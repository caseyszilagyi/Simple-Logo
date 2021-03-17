package slogo.model.commands.basic_commands.command_types;

import slogo.controller.BackEndExternalAPI;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.DisplayInformation;

/**
 * This abstract class is designed to be implemented by any BasicCommand that needs to alter
 * data pertaining to the display of the front end
 */
public abstract class DisplayAlteringCommand extends DisplayQueryCommand {

  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final DisplayInformation DISPLAY_INFORMATION;

  /**
   * Makes the BasicCommand and saves the model controller to communicate with the front
   * end
   *
   * @param informationBundle The bundle of information that contains the model controller
   */
  public DisplayAlteringCommand(CommandInformationBundle informationBundle) {
    super(informationBundle);
    MODEL_CONTROLLER = informationBundle.getModelController();
    DISPLAY_INFORMATION = informationBundle.getDisplayInformation();
  }

  /**
   * Sets the index representing the background color
   * @param index The index
   */
  protected void setBackgroundColor(int index){
    DISPLAY_INFORMATION.setBackgroundColor(index);
    MODEL_CONTROLLER.setBackgroundColor(index);
  }

  /**
   * Sets the index representing the pen color
   * @param index The index
   */
  protected void setPenColor(int index){
    DISPLAY_INFORMATION.setPenColor(index);
    MODEL_CONTROLLER.setPenColor(index);
  }

  /**
   * Sets the index representing the turtle shape
   * @param index The index
   */
  protected void setTurtleShape(int index){
    DISPLAY_INFORMATION.setTurtleShape(index);
    MODEL_CONTROLLER.setTurtleShape(index);
  }

  /**
   * Changes an index in the palette to a specific color
   * @param index The index in the palette to change
   * @param red RGB value for red
   * @param green RGB value for green
   * @param blue RGB value for blue
   */
  protected void setPalette(int index, int red, int green, int blue){
    MODEL_CONTROLLER.setPalette(index, red, green, blue);
  }

  /**
   * Sets the index representing the line drawing size
   * @param pixels the size of the line, in pixels
   */
  protected void setPenSize(double pixels){
    DISPLAY_INFORMATION.setPenSize(pixels);
    MODEL_CONTROLLER.setPenSize(pixels);
  }


}
