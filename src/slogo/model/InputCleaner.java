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

public class InputCleaner {

  private static final String LANGUAGES_PACKAGE = InputCleaner.class.getPackageName()+".resources.languages.";
  public static final String WHITESPACE = "\\s+";
  private String language;
  private List<Entry<String, Pattern>> symbols;
  private Map<String, Pattern> syntaxMap;
  private String userInput;
  private CommandParser commandParser;
  private ModelController modelController;


  public InputCleaner(String userInput, ModelController modelController, CommandParser commandParser){
    symbols = new ArrayList<>();
    syntaxMap = new HashMap<>();
    language = "English";
    addLangPatterns(language);
    addRegExPatterns("Syntax");
    this.userInput = userInput;
    this.modelController = modelController;
    this.commandParser = commandParser;
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  private void addLangPatterns(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      symbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  private void addRegExPatterns(String syntax){

    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      syntaxMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  public List<String> cleanString() {
    String noComments = removeComments();
    List<String> translated = translateCommand(noComments);
    List<String> groupedCommands = findCommandBlocks(translated);
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
      if(isCommand(toRet.get(ind))){
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
        System.out.println("COMMAND_1: "+ commandVal);
        commandKeyNum = commandKey + Integer.toString(commandCount);

        commandParser.addSingleParamCount(commandKeyNum, commandVal);
      }
    }
    return toRet;
  }

  private boolean isCommand(String s) {
    return match(s, syntaxMap.get("Command"));
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
