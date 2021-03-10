package slogo.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.controller.ModelController;

/**
 * Cleans the raw string input from the user into a list of strings that the CommandParser can use will recognize as commands and command parameters
 * removes comments, bundles series of commands in [] into a CommandBundle, adds CommandBundle param counts to parameters cound in CommandParser
 *
 * @author jincho
 */
public class InputCleaner {

  private static final String LANGUAGES_PACKAGE = InputCleaner.class.getPackageName()+".resources.languages.";
  public static final String WHITESPACE = "\\s+";
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
  public InputCleaner(String userInput, ModelController modelController, CommandParser commandParser) {
    symbols = new ArrayList<>();
    syntaxMap = new HashMap<>();
    language = "English";
    addLangPatterns(language);
    addRegExPatterns("Syntax");
    this.userInput = userInput;
    this.commandParser = commandParser;
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
    List<String> beforeTranslation = Arrays.asList(input.split(WHITESPACE));
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
    int blockSize = 0;
    String commandKey = "CommandBlock_";
    String commandVal = "";
    int commandCount = 0;
    for (int ind = 0; ind < toRet.size(); ind++) {
      String commandKeyNum = "";
      if (isCommand(toRet.get(ind))) {
        blockSize++;
      }
      if (toRet.get(ind).equals("[")) {
        commandCount++;
        commandKeyNum = commandKey + Integer.toString(commandCount);
        toRet.set(ind, commandKeyNum);
        blockSize = 0;
      }
      if (toRet.get(ind).equals("]")) {
        toRet.remove(ind);
        ind--;
        commandVal = blockSize + "";
        commandKeyNum = commandKey + Integer.toString(commandCount);
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
        //here, you set the variable = the varible value (constant)
//        toRet.set(ind, toRet.get(ind).substring(1));
      }
    }
    return toRet;
  }

  private boolean isVariable(String s) {
    //also need to check if it exists in map
    return match(s, syntaxMap.get("Variable"));
  }

  private List<String> replaceUserDefCommands(List<String> commands) {
    List<String> toRet = new ArrayList<>(commands);
    for (int ind = 0; ind < toRet.size(); ind++) {
      if(isUserDefCommand(toRet.get(ind))) {
        //replace the name of command with the command block node with the children that are its params
      }
    }
    return toRet;
  }

  private boolean isUserDefCommand(String s) {
    //look in map of pre def commands
    return true;
  }

  private String getCommandKey (String text) {
    final String ERROR = "NO MATCH";
    for (Entry<String, Pattern> e : symbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    // FIXME: perhaps throw an exception instead
    return ERROR;
  }

  private boolean match (String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
