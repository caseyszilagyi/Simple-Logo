package slogo.model;

import java.security.spec.ECField;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
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
  private final Map<String, Double> VARIABLES;
  private final Map<String, UserDefinedCommand> COMMANDS;

  private String language;
  private List<Entry<String, Pattern>> symbols;
  private Map<String, Pattern> syntaxMap;
  private String userInput;
  public CommandParser commandParser;

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
    List<String> groupedCommands = findCommandBlocks(translated);
    groupedCommands.removeIf(command -> command.equals(""));
    List<String> variablesToValues = replaceVariables(groupedCommands);
    return variablesToValues;
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

  private List<String> findCommandBlocks(List<String> commands) {
    List<String> toRet = new ArrayList<>(commands);
    Deque<String> commandBlocks = new ArrayDeque<>();
    Deque<Integer> parameters = new ArrayDeque<>();
    String commandKey = "CommandBlock_";
    int commandCount = 0;
    int blockSize = 0;
    String commandVal = "";
    for (int ind = 0; ind < toRet.size(); ind++) {
      String commandKeyNum = "";
      String curr = toRet.get(ind);
      if (isCommand(curr)) {
        blockSize++;
      }
      if (curr.equals("[")) {
        commandCount++;
        commandKeyNum = commandKey + Integer.toString(commandCount);
        commandBlocks.push(commandKeyNum);
        parameters.push(blockSize);
        toRet.set(ind, commandKeyNum);
        blockSize = 0;
      }
      if (toRet.get(ind).equals("]")) {
        toRet.remove(ind);
        ind--;
        commandVal = blockSize + "";
        commandKeyNum = commandBlocks.pop();
        blockSize = parameters.pop();
        commandParser.addSingleParamCount(commandKeyNum, commandVal);
      }
    }
    return toRet;
  }

  private boolean isCommand(String s) {
    return match(s, syntaxMap.get("Command"));
  }

  private List<String> replaceVariables(List<String> commands) {
    List<String> toRet = new ArrayList<>(commands);
    for (int ind = 0; ind < toRet.size(); ind++) {
      if(isVariable(toRet.get(ind))) {
        try {
          Double varVal = VARIABLES.get(toRet.get(ind).substring(1));
          toRet.set(ind, toRet.get(ind));
        } catch (Exception e){
          System.out.println("Variable doesn't exist!!");
        }
      }
    }
    return toRet;
  }

  private boolean isVariable(String s) {
    return match(s, syntaxMap.get("Variable"));
  }

  private List<String> replaceUserDefCommands(List<String> commands) {
    List<String> toRet = new ArrayList<>(commands);
    for (int ind = 0; ind < toRet.size(); ind++) {
      if(isUserDefCommand(toRet.get(ind))) {
        //replace the name of command with the command block node with the children that are its params
        try {
          UserDefinedCommand userDefCommand = COMMANDS.get(toRet.get(ind));
        } catch (Exception e) {
          System.out.println("User Defined Command doesn't exist!!!");
        }
      }
    }
    return toRet;
  }

  private boolean isUserDefCommand(String s) {
    return COMMANDS.containsKey(s);
  }

  private String getCommandKey (String text) {

    for (Entry<String, Pattern> e : symbols) {
      try {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      } catch (Exception ex){
        System.out.println("invalid command");
      }
    }
    // FIXME: perhaps throw an exception instead
    return null;
  }

  private boolean match (String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
