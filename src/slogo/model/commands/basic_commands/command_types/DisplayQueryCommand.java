package slogo.model.commands.basic_commands.command_types;

import slogo.controller.BackEndExternalAPI;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.DisplayInformation;

/**
 * This abstract class is designed to be implemented by any BasicCommand that needs any data about
 * the state of the front end
 */
public abstract class DisplayQueryCommand extends Command {

  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final DisplayInformation DISPLAY_INFORMATION;

  /**
   * Makes the BasicCommand and saves the front end display object
   *
   * @param informationBundle The bundle of information that contains the front end display object
   */
  public DisplayQueryCommand(CommandInformationBundle informationBundle) {
    MODEL_CONTROLLER = informationBundle.getModelController();
    DISPLAY_INFORMATION = informationBundle.getDisplayInformation();
  }

  /**
   * Gets the index value of the pen color
   * @return The index
   */
  protected double getPenColor(){
    return DISPLAY_INFORMATION.getPenColor();
  }

  /**
   * Gets the index value of the turtle shape
   * @return The index
   */
  protected double getTurtleShape(){
    return DISPLAY_INFORMATION.getTurtleShape();
  }



}
