package slogo.model.parse;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.SLogoCommandExecutor;

/**
 * Cleans the raw string input from the user into a list of strings that the CommandParser can use
 * will recognize as commands and command parameters removes comments
 * @author jincho
 */
public class InputCleaner extends Parser{

  private static final String WHITESPACE = "\\s+";
  private List<Entry<String, Pattern>> languagePatterns;

  private String userInput;

  /**
   * create instance of InputCleaner and initializes lists for "translating" the string into strings
   * recognizable by backend classes and parser
   *
   * @param userInput       raw string of commands
   */
  public InputCleaner(String userInput, String language) {
    languagePatterns = new ArrayList<>();
    addLangPatterns(language);
    this.userInput = userInput;
  }

  private void addLangPatterns(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(SLogoCommandExecutor.LANGUAGES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      languagePatterns.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * method that actually cleans the string input
   *
   * @return list of strings without comments, translated to backend recognizable, and commandblocks
   * grouped
   */
  @Override
  public List<String> parseResults() {
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

  private String getCommandKey(String text) {
    for (Entry<String, Pattern> e : languagePatterns) {
      try {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      } catch (Exception ex) {
        throw new ErrorHandler("InvalidCommandName");
      }
    }
    return "NO MATCH";
  }
}
