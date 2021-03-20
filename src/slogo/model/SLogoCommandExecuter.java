package slogo.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.parse.CommandParser;
import slogo.model.parse.InputCleaner;
import slogo.model.parse.MakeTokens;
import slogo.model.tree.TreeNode;

/**
 * Prepares for and executes all commands. Main communication between the ModelController and the Model.
 * Centralized location for all classes that take part in the cleaning, parsing, and execution of commands.
 *
 * @author jincho
 */
public class SLogoCommandExecuter implements CommandExecuter {

  public static final String LANGUAGES_PACKAGE = "slogo.model.resources.languages.";
  public static final String COMMAND_PACKAGE = "slogo.model.resources.commands.";
  public static final String COMMAND_WITH_LISTS = "CommandBlocks";
  public static final String TOKENS_MAP = "TokenSyntax";
  public static final String COMMAND_KEY = "CommandBlock_";

  private final Map<String, Pattern> regexMap;

  private BackEndExternalAPI modelController;
  private CommandParser commandParser;
  private InputCleaner inputCleaner;
  private MakeTokens tokenMaker;

  private final CommandInformationBundle BUNDLE;
  private final BasicCommandClassLoader COMMAND_LOADER = new BasicCommandClassLoader();


  public SLogoCommandExecuter(BackEndExternalAPI modelController) {
    this.modelController = modelController;
    BUNDLE = new CommandInformationBundle(modelController);


    regexMap = new HashMap<>();
    addRegExPatterns("Syntax");

  }

  public CommandInformationBundle getBundle(){
    return BUNDLE;
  }



  public void executeCommand(String input, String language) {
    CommandParser commandParser = new CommandParser(input, language, modelController);
    TreeNode inputRoot = commandParser.makeTree();
    for (TreeNode child : inputRoot.getChildren()) {
      COMMAND_LOADER.makeCommand(BUNDLE, child).execute();
    }
  }

  private void addRegExPatterns(String regEx) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + regEx);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      regexMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  private boolean match(String text, Pattern... regex) {
    for (Pattern p : regex) {
      if(p.matcher(text).matches()) { return true;}
    }
    return false;
  }




}
