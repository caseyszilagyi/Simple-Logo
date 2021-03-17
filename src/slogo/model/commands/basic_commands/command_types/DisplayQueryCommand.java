package slogo.model.commands.basic_commands.command_types;

import slogo.controller.BackEndExternalAPI;
import slogo.model.execution.CommandInformationBundle;

/**
 * This abstract class is designed to be implemented by any BasicCommand that needs any data
 * about the state of the front end
 */
public abstract class DisplayQueryCommand extends Command {

  private final BackEndExternalAPI MODEL_CONTROLLER;

  /**
   * Makes the BasicCommand and saves the front end display object
   *
   * @param informationBundle The bundle of information that contains the front end display
   *                          object
   */
  public DisplayQueryCommand(CommandInformationBundle informationBundle) {
    MODEL_CONTROLLER = informationBundle.getModelController();
  }


}
