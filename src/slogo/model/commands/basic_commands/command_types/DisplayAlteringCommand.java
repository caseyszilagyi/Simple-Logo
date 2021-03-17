package slogo.model.commands.basic_commands.command_types;

import slogo.controller.BackEndExternalAPI;
import slogo.model.execution.CommandInformationBundle;

/**
 * This abstract class is designed to be implemented by any BasicCommand that needs to alter
 * data pertaining to the display of the front end
 */
public abstract class DisplayAlteringCommand extends DisplayQueryCommand {

  private final BackEndExternalAPI MODEL_CONTROLLER;

  /**
   * Makes the BasicCommand and saves the model controller to communicate with the front
   * end
   *
   * @param informationBundle The bundle of information that contains the model controller
   */
  public DisplayAlteringCommand(CommandInformationBundle informationBundle) {
    super(informationBundle);
    MODEL_CONTROLLER = informationBundle.getModelController();
  }

  protected void setBackgroundColor(double index){

  }

  protected void setPenColor(double index){

  }

  protected void setShape(double index){

  }

  protected void setPalette(double index, double red, double green, double blue){

  }

  protected void setPenSize(double pixels){

  }


}
