package slogo.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * Cleans the raw string input from the user into a list of strings that the CommandParser can use will recognize as commands and command parameters
 * removes comments, bundles series of commands in [] into a CommandBundle, adds CommandBundle param counts to parameters cound in CommandParser
 *
 * @author jincho
 */
public class InputCleaner {

  private static final String LANGUAGES_PACKAGE = InputCleaner.class.getPackageName()+".resources.languages.";
  private static final String WHITESPACE = "\\s+";
  private static final ArrayList<String> VARIABLE_BLOCK_COMMANDS = new ArrayList<>(Arrays.asList("DoTimes", "MakeUserInstruction"));
  private final Map<String, Double> VARIABLES;
  private final Map<String, UserDefinedCommand> COMMANDS;

  private String language;
  private List<Entry<String, Pattern>> symbols;
  private Map<String, Pattern> syntaxMap;
  private String userInput;
  private int commandCount;
  public CommandParser commandParser;
  private int paramCountsActual = 0;
  private int paramCountsExpected = 0;

  /**
   * create instance of InputCleaner and initializes lists for "translating" the string into strings recognizable by backend classes and parser
   *
   * @param userInput raw string of commands
   * @param modelController ModelController associated with the current string input
   * @param commandParser CommandParser that will parse through this particular string
   */
  public InputCleaner(String userInput, String language, BackEndExternalAPI modelController, CommandParser commandParser) {
    symbols = new ArrayList<>();
    syntaxMap = new HashMap<>();
    this.language = language;
    commandCount = 0;
    addLangPatterns(language);
    addRegExPatterns("Syntax");
    this.userInput = userInput;
    this.commandParser = commandParser;
    VARIABLES = modelController.getVariables();
    COMMANDS = modelController.getUserDefinedCommands();
  }

  private void addLangPatterns(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      symbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  private void addRegExPatterns(String regEx) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + regEx);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      syntaxMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  /**
   * method that actually cleans the string input
   * @return list of strings without comments, translated to backend recognizable, and commandblocks grouped
   */
  public List<String> cleanString() {
    String noComments = removeComments();
    List<String> translated = translateCommand(noComments);
    List<String> varBlocks = findVariableBlocks(translated);
    List<String> groupedCommands = findCommandBlocks(varBlocks);
    groupedCommands.removeIf(command -> command.equals(""));
    if(paramCountsActual != paramCountsExpected){
      throw new ErrorHandler("WrongParamNum");
    }
    return groupedCommands;
  }

  private String removeComments() {
    StringBuffer removedComments = new StringBuffer(userInput);
    while (removedComments.indexOf("#") != -1) {
      int indBeforeComment = removedComments.indexOf("#");
      int indAfterComment = removedComments.indexOf("\n", indBeforeComment)+1;
      removedComments.replace(indBeforeComment, indAfterComment, " ");
    }
    return removedComments.toString();
  }

  private List<String> translateCommand(String input) {
    List<String> translated = new ArrayList<>();
    String[] beforeTranslation = input.split(WHITESPACE);
    for (String s : beforeTranslation) {
      if (getCommandKey(s).equals("NO MATCH")) {
        translated.add(s);
      } else {
        translated.add(getCommandKey(s));
      }
    }
    return translated;
  }

  private List<String> findVariableBlocks(List<String> commands) {
    System.out.println(commands);
    List<String> toRet = new ArrayList<>(commands);
    String commandKey = "CommandBlock_";
    int blockSize = 0;
    String commandVal = "";
    boolean canCount = false;
    for (int ind = 0; ind < toRet.size(); ind++) {
      String commandKeyNum = "";
      String curr = toRet.get(ind);
      blockSize++;
      if (match(curr, syntaxMap.get("ListStart")) && (hasVarBlocks(toRet.get(ind-1)) || hasVarBlocks(toRet.get(ind-2)))) {
        commandCount++;
        commandKeyNum = commandKey + Integer.toString(commandCount);
        toRet.set(ind, commandKeyNum);
        blockSize = 0;
        canCount = true;
      }
      if (match(curr, syntaxMap.get("ListEnd")) && canCount) {
        toRet.remove(ind);
        ind--;
        commandVal = blockSize-1 + "";
        commandKeyNum = commandKey + Integer.toString(commandCount);
        commandParser.addSingleParamCount(commandKeyNum, commandVal);
        canCount = false;
      }
    }
    return toRet;
  }

  private boolean hasVarBlocks(String s) {
    System.out.println(s);
    return VARIABLE_BLOCK_COMMANDS.contains(s);
  }

  private List<String> findCommandBlocks(List<String> commands) {
    List<String> toRet = new ArrayList<>(commands);
    Deque<String> commandBlocks = new ArrayDeque<>();
    Deque<Integer> parameters = new ArrayDeque<>();
    String commandKey = "CommandBlock_";
    int blockSize = 0;
    String commandVal = "";
    for (int ind = 0; ind < toRet.size(); ind++) {
      String commandKeyNum = "";
      String curr = toRet.get(ind);
      if (isCommand(curr)) {
        blockSize++;
      }
      if (match(curr, syntaxMap.get("ListStart"))) {
        commandCount++;
        commandKeyNum = commandKey + Integer.toString(commandCount);
        commandBlocks.push(commandKeyNum);
        parameters.push(blockSize);
        toRet.set(ind, commandKeyNum);
        blockSize = 0;
      }
      if (match(curr, syntaxMap.get("ListEnd"))) {
        toRet.remove(ind);
        ind--;
        commandVal = blockSize + "";
        commandKeyNum = commandBlocks.pop();
        blockSize = parameters.pop();
        commandParser.addSingleParamCount(commandKeyNum, commandVal);
      }
    }
    if(!commandBlocks.isEmpty()){
      throw new ErrorHandler("WrongParamNum");
    }
    return toRet;
  }

  private boolean isCommand(String s) {
    return match(s, syntaxMap.get("Command"));
  }

  private String getCommandKey (String text) {
    for (Entry<String, Pattern> e : symbols) {
      try {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      } catch (Exception ex){
        System.out.println("invalid command");
        throw new ErrorHandler("InvalidCommand");
      }
    }
    return "NO MATCH";
  }

  private boolean match (String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
