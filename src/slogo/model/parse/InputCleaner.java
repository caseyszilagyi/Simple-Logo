package slogo.model.parse;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.SLogoCommandExecutor;

/**
 * Cleans the raw string input from the user into a list of strings that the CommandParser can use
 * will recognize as commands and command parameters removes comments
 *
 * @author jincho
 */
public class InputCleaner extends Parser{

  private static final String WHITESPACE = "\\s+";
  private List<Entry<String, Pattern>> languagePatterns;
  private List<String> userDefinedCommands;
  private Pattern makeUserDef;

  private String userInput;

  /**
   * create instance of InputCleaner and initializes lists for "translating" the string into strings
   * recognizable by backend classes and parser
   *
   * @param userInput       raw string of commands
   */
  public InputCleaner(String userInput, String language, BackEndExternalAPI modelController) {
    languagePatterns = new ArrayList<>();
    addLangPatterns(language);
    this.userInput = userInput;
    userDefinedCommands = new ArrayList<>(modelController.getUserDefinedCommands().keySet());
  }

  private void addLangPatterns(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(SLogoCommandExecutor.LANGUAGES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      Pattern regexPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
      if(key.equals("MakeUserInstruction")) { makeUserDef = regexPattern; }
      languagePatterns.add(new SimpleEntry<>(key, regexPattern));
    }
  }

  /**
   * method that actually cleans the string input
   *
   * @return list of strings without comments, translated to backend recognizable
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
    for (int i = 0; i<beforeTranslation.length; i++) {
      String s = beforeTranslation[i];
      if (match(s, syntaxMap.get("Command")) && !userDefCommandName(i, beforeTranslation) ) {
        translated.add(getCommandKey(s));
      } else {
        translated.add(s);
      }
    }
    return translated;
  }

  private boolean userDefCommandName(int id, String[] beforeTranslation) {
    String curr = beforeTranslation[id];
    boolean newlyDefined = false;
    if(id != 0) {
      newlyDefined = match(beforeTranslation[id-1], makeUserDef);
      if(newlyDefined) { userDefinedCommands.add(curr); }
    }
    boolean alreadyDefined = userDefinedCommands.contains(curr);
    return alreadyDefined || newlyDefined;
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
    throw new ErrorHandler("InvalidCommandName");
  }
}
