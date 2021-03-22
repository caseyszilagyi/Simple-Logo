package slogo.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.UserDefinedInformation;
import slogo.model.parse.CommandParser;
import slogo.model.parse.InputCleaner;
import slogo.model.parse.MakeTokens;
import slogo.model.parse.tokens.Token;
import slogo.model.tree.TreeNode;

/**
 * Prepares for and executes all commands. Main communication between the ModelController and the
 * Model. Centralized location for all classes that take part in the cleaning, parsing, and
 * execution of commands.
 *
 * @author jincho
 */
public class SLogoCommandExecutor implements CommandExecutor {

  public static final String LANGUAGES_PACKAGE = "slogo.model.resources.languages.";
  public static final String COMMAND_PACKAGE = "slogo.model.resources.commands.";
  public static final String REGEX_SYNTAX = "Syntax";
  public static final String COMMAND_PARAMS = "CommandsParam";

  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final CommandInformationBundle BUNDLE;
  private final BasicCommandClassLoader COMMAND_LOADER = new BasicCommandClassLoader();
  private final UserDefinedInformation USER_INFORMATION;

  /**
   * This is the command executor that executes commands for SLogo. The basic constructor takes the
   * model controller to link it to the front end, and then makes the command bundle for the user
   *
   * @param modelController The model controller that is linked to the front end
   */
  public SLogoCommandExecutor(BackEndExternalAPI modelController) {
    MODEL_CONTROLLER = modelController;
    BUNDLE = new CommandInformationBundle(modelController);
    USER_INFORMATION = BUNDLE.getUserDefinedInformation();
  }

  /**
   * This constructor is designed if the user already has a bundle with turtle/variable information
   * and wants to link it to this class. Also useful for testing
   *
   * @param modelController The model controller that is linked to the front end
   * @param bundle          The bundle that has turtle information, variable information, and
   *                        display information
   */
  public SLogoCommandExecutor(BackEndExternalAPI modelController, CommandInformationBundle bundle) {
    MODEL_CONTROLLER = modelController;
    BUNDLE = bundle;
    USER_INFORMATION = BUNDLE.getUserDefinedInformation();
  }

  /**
   * Gets an unmodifiable copy of the map of commands
   *
   * @return The copy of the command map
   */
  public Map<String, UserDefinedCommand> getCommandMap() {
    return USER_INFORMATION.getCommandMap();
  }

  /**
   * Gets an unmodifiable copy of the variable map
   *
   * @return The variable map
   */
  public Map<String, Double> getVariableMap() {
    return USER_INFORMATION.getVariableMap();
  }

  /**
   * Executes a command, resulting in calls to the model controller
   *
   * @param input    The command in raw string form
   * @param language The language of the command
   */
  public void executeCommand(String input, String language) {
    CommandParser commandParser = new CommandParser(input, language, MODEL_CONTROLLER);
    TreeNode inputRoot = commandParser.makeTree();
    for (TreeNode child : inputRoot.getChildren()) {
      COMMAND_LOADER.makeCommand(BUNDLE, child).execute();
    }

  }
}
