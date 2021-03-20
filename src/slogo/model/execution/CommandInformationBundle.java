package slogo.model.execution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * This class stores all of the information that will be needed to execute a command. Will be passed
 * to the execute method of each basic command. Has the turtle, variable names, and user defined
 * commands
 */
public class CommandInformationBundle {

  private final UserDefinedInformation USER_INFORMATION;

  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final DisplayInformation DISPLAY_INFORMATION = new DisplayInformation();
  private final TurtleInformation TURTLE_INFORMATION;


  /**
   * Makes our information bundle, and initializes the first turtle
   *
   * @param modelController The model controller that the bundle communicates with to pass
   *                        information to the front end
   */
  public CommandInformationBundle(BackEndExternalAPI modelController) {
    MODEL_CONTROLLER = modelController;
    USER_INFORMATION = new UserDefinedInformation(modelController);
    TURTLE_INFORMATION = new TurtleInformation(MODEL_CONTROLLER);
  }

  public TurtleInformation getTurtleInformation(){
    return TURTLE_INFORMATION;
  }

  /**
   * Gets the model controller. Used for the commands to pass information to the front end
   *
   * @return The model controller
   */
  public BackEndExternalAPI getModelController() {
    return MODEL_CONTROLLER;
  }

  public UserDefinedInformation getUserDefinedInformation(){
    return USER_INFORMATION;
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
   * Gets the display information
   *
   * @return The display information
   */
  public DisplayInformation getDisplayInformation() {
    return DISPLAY_INFORMATION;
  }


}
