package slogo.model.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.model.SLogoCommandExecutor;

/**
 *
 */
public abstract class Parser {

  public static Map<String, Pattern> syntaxMap;
  public static Map<String, List<String>> commandParam;

  public Parser(){
    syntaxMap = addRegExPatterns();
    commandParam = addExpectedParams();
  }

  protected Map<String, Pattern> addRegExPatterns() {
    Map<String, Pattern> regexMap = new HashMap<>();
    ResourceBundle resources = ResourceBundle.getBundle(SLogoCommandExecutor.LANGUAGES_PACKAGE + SLogoCommandExecutor.REGEX_SYNTAX);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      regexMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
    return regexMap;
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  protected Map<String, List<String>> addExpectedParams() {
    Map<String, List<String>> paramMap = new HashMap<>();
    ResourceBundle resources = ResourceBundle.getBundle(SLogoCommandExecutor.COMMAND_PACKAGE + SLogoCommandExecutor.COMMAND_PARAMS);
    for (String key : Collections.list(resources.getKeys())) {
      List<String> params = new ArrayList<>(Arrays.asList(resources.getString(key).split(" ")));
      params.removeIf(command -> command.equals(""));
      paramMap.put(key, params);
    }
    return paramMap;
  }

  protected boolean match(String text, Pattern... regex) {
    for (Pattern p : regex) {
      if(p.matcher(text).matches()) { return true;}
    }
    return false;
  }

  public abstract List<String> parseResults();


}