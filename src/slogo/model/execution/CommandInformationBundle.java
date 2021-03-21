package slogo.model.execution;

import slogo.controller.BackEndExternalAPI;

/**
 * This class stores all of the information that will be needed to execute a command. Will be passed
 * to the execute method of each basic command. Has the turtle, variable names, and user defined
 * commands
 */
public class CommandInformationBundle {

  private final UserDefinedInformation USER_INFORMATION;

  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final DisplayInformation DISPLAY_INFORMATION;
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
    TURTLE_INFORMATION = new TurtleInformation(modelController);
    DISPLAY_INFORMATION = new DisplayInformation(modelController);

  }

  /**
   * Gets the model controller. Used for the commands to pass information to the front end
   *
   * @return The model controller
   */
  public BackEndExternalAPI getModelController() {
    return MODEL_CONTROLLER;
  }

  /**
   * Gets the turtle information. Contains all of the turtles and lists of active turtles, as well
   * as structure to deal with local ask/tell calls
   *
   * @return The user defined information
   */
  public TurtleInformation getTurtleInformation() {
    return TURTLE_INFORMATION;
  }

  /**
   * Gets the user defined information. Contains the commands, variables, and parameters, as well as
   * some helper methods to organize that information
   *
   * @return The user defined information
   */
  public UserDefinedInformation getUserDefinedInformation() {
    return USER_INFORMATION;
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
