package slogo.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.controller.ModelController;

public class InputCleaner {

  private static final String LANGUAGES_PACKAGE = InputCleaner.class.getPackageName()+".resources.languages.";
  public static final String WHITESPACE = "\\s+";
  private String language;
  private List<Entry<String, Pattern>> symbols;
  private String userInput;
  private CommandParser commandParser;
  private ModelController modelController;


  public InputCleaner(String userInput, ModelController modelController, CommandParser commandParser){
    symbols = new ArrayList<>();
    language = "English";
    addLangPatterns(language);
    addLangPatterns("Syntax");
    this.userInput = userInput;
    this.modelController = modelController;
    this.commandParser = commandParser;
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addLangPatterns(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      symbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  public List<String> cleanString() {
    String noComments = removeComments();
    List<String> translated = translateCommand(noComments);
    List<String> groupedCommands = findCommandBlocks(translated);
  }

  private String removeComments() {
    StringBuffer removedComments = new StringBuffer(userInput);
    while (userInput.indexOf('#') != -1) {
      int indBeforeComment = removedComments.indexOf("#")-1;
      int indAfterComment = removedComments.indexOf("\n", indBeforeComment);
      removedComments.replace(indBeforeComment, indAfterComment, " ");
    }
    return removedComments.toString();
  }

  private boolean isComment(int currInd, int nextInd, List<String> commands){
    return commands.get(currInd).equals("\n") && commands.get(currInd).equals("#");
  }

  public List<String> translateCommand(String input) {
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
    int blockSize = 0;
    String commandKey = "CommandBlock";
    String commandVal = "";
    for (int ind = 0; ind < commands.size(); ind++) {
      blockSize++;
      if (commands.get(ind).equals("[")) {
        commands.set(ind, commandKey);
        blockSize = 0;
      }
      if (commands.get(ind).equals("]")) {
        commands.remove(ind);
        ind--;
        commandVal = blockSize-1 + "";
        commandParser.addSingleParamCount(commandKey, commandVal);
      }
    }
    return commands;
  }

  public String getCommandKey (String text) {
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

  private String joinStringList(List<String> split){
    String joined = "";
    for(String s : split){
      joined = joined + s + " ";
    }
    return joined;
  }


}
