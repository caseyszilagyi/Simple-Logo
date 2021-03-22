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
 * @author jincho
 */
public abstract class Parser {

  public static Map<String, Pattern> syntaxMap;
  public static Map<String, List<String>> commandParam;
  public static final String NUM_TOKEN = "NUM";
  public static final String LIST_TOKEN = "LIST";
  public static final String COMMAND_BLOCK_CLASS = "CommandBlock";



  /**
   * all Parsers have access to the map of regex patterns and the expected parameters for the commands provided so they are declared here.
   * declares these maps as a basis for all parsers that extend this abstract class
   */
  public Parser(){
    syntaxMap = addRegExPatterns();
    commandParam = addExpectedParams();
  }

  /**
   * creates map of syntax regex patterns particular to the SLogo language
   * @return Map of Regex types to their patterns
   */
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
   * creates map of all basic commands and their expected token inputs as parameters
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

  /**
   * sees if the text given is of the regex pattern given
   * @param text string you want to see if it matches regex pattern
   * @param regex pattern you compare string to
   * @return boolean stating if text is of regex pattern given
   */
  protected boolean match(String text, Pattern... regex) {
    for (Pattern p : regex) {
      if(p.matcher(text).matches()) { return true;}
    }
    return false;
  }

  /**
   * gives resulting list of commands after going through the parsing and refactoring of the command list
   * @return list of string representations after parsing of particular type
   */
  public abstract List<String> parseResults();


}