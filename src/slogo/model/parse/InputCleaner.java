package slogo.model.parse;

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
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * Cleans the raw string input from the user into a list of strings that the CommandParser can use
 * will recognize as commands and command parameters removes comments
 * @author jincho
 */
public class InputCleaner {

  private static final String LANGUAGES_PACKAGE = "slogo.model.resources.languages.";
  private static final String COMMANDS_PACKAGE = "slogo.model.resources.commands.";
  private static final String WHITESPACE = "\\s+";
  public CommandParser commandParser;
  private List<Entry<String, Pattern>> symbols;
  private Map<String, Pattern> syntaxMap;
  private Map<String, List<String>> commandParam;
  private String userInput;

  /**
   * create instance of InputCleaner and initializes lists for "translating" the string into strings
   * recognizable by backend classes and parser
   *
   * @param userInput       raw string of commands
   * @param modelController ModelController associated with the current string input
   * @param commandParser   CommandParser that will parse through this particular string
   */
  public InputCleaner(String userInput, String language, BackEndExternalAPI modelController,
      CommandParser commandParser) {
    symbols = new ArrayList<>();
    syntaxMap = new HashMap<>();
    commandParam = new HashMap<>();
    addLangPatterns(language);
    addRegExPatterns("Syntax");
    addParamCounts("CommandsParam");
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
   * Adds the given resource file to this language's recognized types
   */
  public void addParamCounts(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(COMMANDS_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      commandParam.put(key, Arrays.asList(resources.getString(key).split(" ")));
    }
  }


  /**
   * method that actually cleans the string input
   *
   * @return list of strings without comments, translated to backend recognizable, and commandblocks
   * grouped
   */
  public List<String> cleanString() {
    String noComments = removeComments();
    List<String> translated = translateCommand(noComments);
    translated.removeIf(command -> command.equals(""));
    return translated;
  }

  private String removeComments() {
    StringBuffer removedComments = new StringBuffer(userInput);
    while (removedComments.indexOf("#") != -1) {
      int indBeforeComment = removedComments.indexOf("#");
      int indAfterComment = removedComments.indexOf("\n", indBeforeComment) + 1;
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

  public String getCommandKey(String text) {
    for (Entry<String, Pattern> e : symbols) {
      try {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      } catch (Exception ex) {
        throw new ErrorHandler("InvalidCommand");
      }
    }
    return "NO MATCH";
  }

  private boolean match(String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
