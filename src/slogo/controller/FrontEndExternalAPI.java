package slogo.controller;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.swing.text.html.ImageView;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.Turtle;

/**
 * @author Ji Yun Hyo
 */
public interface FrontEndExternalAPI {

  /**
   * Sets the background color of the display. The index corresponds to a specific color as
   * specified in a properties file
   * @param color The index corresponding to the color
   */
  public void setBackGroundColor(int color);

  /**
   * Sets the image of the specific turtle with the image
   * @param turtle turtle ID
   * @param image image file
   */
  public void setTurtleImage(Turtle turtle, ImageView image);

  /**
   * Gets the language of the command and UI.
   * There is a dependency with ScreenCreator
   */
  public String getLanguage();

  /**
   * Returns the command history
   * Dependency: ScreenCreator
   */
  public Queue<String> getCommandHistory();

  /**
   * returns the error message to be displayed
   * @param errorMessage
   */
  public void displayError(String errorMessage);

  /**
   * sets the model controller to set up the line of communication from/to the backend
   * @param modelController BackEndExternalAPI
   */
  public void setModelController(BackEndExternalAPI modelController);

  /**
   * Returns the user command input to be parsed
   * @return user command input
   */
  public String getUserCommandInput();

  /**
   * Processes the command input from the text area. Primary method that passes the user input
   * to the backend to be parsed
   * Dependency exists with model controller, ScreenCreator, and the backend classes.
   * @param userCommandInput command string of the user input
   */
  public void processUserCommandInput(String userCommandInput);

  /**
   * Displays the actual command when the user clicks on the command history/user-defined commands button
   * Dependencies exist with ScreenCreator
   * @param command String to be displayed in the text area
   */
  void displayCommandStringOnTextArea(String command);

  /**
   * Sends the information to the frontend so that the command history/variables tabs can be updated
   * Dependency exists with model controller method that gets the information from the backend.
   * Dependency exists with ScreenCreator class from the frontend that calls this method.
   * @param variables information about all the variables user has defined
   * @param userDefinedCommands information about all the user-defined commands
   */
  void updateFrontEnd(Map<String, Double> variables,
                      Map<String, UserDefinedCommand> userDefinedCommands);

  /**
   * Sets the ID of the turtle to be updated/displayed
   * @param turtleID ID of the turtle to be updated/displayed for animation
   */
  void setActiveTurtle(int turtleID);

  /**
   * Sets the turtle position to be updated to the x and y coordinates
   * Dependency exists with the ScreenCreator class that calls this method
   * @param xPosition xCoord of the turtle
   * @param yPosition yCoord of the turtle
   */
  void setTurtlePosition(double xPosition, double yPosition);

  /**
   * Sets the turtle angle
   * Dependency exists with ScreenCreator class that calls this method
   * @param angle angle to be set
   */
  void setTurtleAngle(double angle);

  void setPenState(double penState);

  void setTurtleVisibility(double visibility);

  void clearScreen();

  void setPenColor(int index);

  void setTurtleShape(int index);

  void setPalette(int index, int red, int green, int blue);

  void setPenSize(double penSize);

  void updateCommandQueue(String commandType, List<Double> commandValues);

  void setLanguage(String language);

  void setActiveTurtles(List<Integer> iDs);

}
