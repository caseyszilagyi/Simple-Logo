package slogo.controller;

import java.util.List;
import java.util.Map;
import slogo.ErrorHandler;
import slogo.model.CommandExecutor;
import slogo.model.SLogoCommandExecutor;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * @author Ji Yun Hyo
 */
public class ModelController implements BackEndExternalAPI {

  FrontEndExternalAPI viewController;
  CommandExecutor COMMAND_EXECUTOR;

  /**
   * Default constructor
   */
  public ModelController() {
    COMMAND_EXECUTOR = new SLogoCommandExecutor(this);

  }


  /**
   * gives access to the value a variable represents
   */
  public Map<String, Double> getVariables() {
    // TODO implement here
    return COMMAND_EXECUTOR.getVariableMap();
  }

  /**
   * gives "global" access to all user defined commands so that user can access every time they user
   * input
   *
   * @return map of command names to their command tree root nodes
   */
  public Map<String, UserDefinedCommand> getUserDefinedCommands() {
    // TODO implement here
    return COMMAND_EXECUTOR.getCommandMap();
  }


  /**
   * parses through input and creates a tree. it then executes all the commands in that tree
   *
   * @param input String input
   */
  public void executeCommand(String input) {
    COMMAND_EXECUTOR.executeCommand(input, viewController.getLanguage());
    viewController.updateFrontEnd(getVariables(),getUserDefinedCommands());
  }


  /**
   * @return the language of the command input
   */
  @Override
  public String getLanguage() {
    try {
      return viewController.getLanguage();
    } catch (Exception e) {
      throw new ErrorHandler("View Controller doesn't exist!!");
    }
  }

  @Override
  public void setViewController(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
  }

  // Below are all the methods used to pass information to the front end

  /**
   * Passes the position of the turtle to the front end. Assumes that 0, 0 is the middle of the
   * screen, with +x to the right and +y up
   *
   * @param xPosition The x position of the turtle
   * @param yPosition The y position of the turtle
   */
  public void setTurtlePosition(double xPosition, double yPosition) {
    viewController.setTurtlePosition(xPosition, yPosition);
  }

  /**
   * Passes the angle of the turtle to the front end, in degrees. 0 is to the right, and increasing
   * angles rotate counterclockwise
   *
   * @param angle
   */
  public void setTurtleAngle(double angle) {
    viewController.setTurtleAngle(angle);
  }

  /**
   * Passes the current pen state to the front end
   *
   * @param penState The pen state. 0 if the pen is not drawing, 1 otherwise
   */
  public void setPenState(double penState) {
    viewController.setPenState(penState);
  }

  /**
   * Passes the turtles visibility to the front end
   *
   * @param visibility The visibility of the turtle. 0 if it is invisible, 1 if it is showing
   */
  public void setTurtleVisibility(double visibility) {
    viewController.setTurtleVisibility(visibility);
  }

  /**
   * Tells the front end when to clear the screen of all lines that have been drawn
   */
  public void clearScreen() {
    viewController.clearScreen();
  }

  /**
   * Sets the turtle that is currently being altered by commands sent to the front end
   *
   * @param turtleID The ID of the active turtle
   */
  public void setActiveTurtle(int turtleID) {
    viewController.setActiveTurtle(turtleID);
  }

  /**
   * Sets all of the turtles that are currently active in the front end
   *
   * @param IDs The IDs of the active turtles
   */
  public void setActiveTurtles(List<Integer> IDs){
    //:TODO Link with front end
    viewController.setActiveTurtles(IDs);
  }

  /**
   * Sets the background color of the display. The index corresponds to a specific color as
   * specified in a properties file
   *
   * @param index The index corresponding to the color
   */
  public void setBackgroundColor(int index) {
    viewController.setBackGroundColor(index);
  }

  /**
   * Sets the pen color that the turtles draw. The index corresponds to a specific color as
   * specified in a properties file
   *
   * @param index The index corresponding to the color
   */
  public void setPenColor(int index) {
    viewController.setPenColor(index);
  }

  /**
   * Sets the shape of the turtle. The index corresponds to a specific shape
   *
   * @param index The index corresponding to the shape
   */
  public void setTurtleShape(int index) {
    viewController.setTurtleShape(index);
  }

  /**
   * Alters an index of the color palette to hold a new color value
   *
   * @param index The index of the palette to alter
   * @param red   Red value of an rgb color, ranges from 0-255
   * @param green Green value of an rgb color, ranges from 0-255
   * @param blue  Blue value of an rgb color, ranges from 0-255
   */
  public void setPalette(int index, int red, int green, int blue) {
    viewController.setPalette(index, red, green, blue);
  }

  /**
   * Sets the pen size to be a specific pixel width
   *
   * @param penSize The size of the pen, in pixels
   */
  public void setPenSize(double penSize) {
    viewController.setPenSize(penSize);
  }


}
