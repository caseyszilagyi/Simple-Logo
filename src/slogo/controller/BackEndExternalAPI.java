package slogo.controller;

import java.util.List;
import java.util.Map;
import javax.swing.text.html.ImageView;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.Turtle;

/**
 * @author Ji Yun Hyo
 */
public interface BackEndExternalAPI {

  /**
   * Return all command history to be displayed
   *
   * @return List of Strings that represent the command histories
   */
  public List<String> getCommandHistory();

  /**
   * Allows user to remove a specific user-defined command
   */
  public void removeUserDefinedCommand();

  /**
   * Adds a new variable to
   */
  public void addVariable();

  /**
   * Removes a variable
   */
  public void removeVariable();

  /**
   * Returns a variable
   */
  public Double getSingleVariable(String var);

  /**
   * Returns a map of all the variable name to values mappings
   */
  public Map<String, Double> getVariables();

  /**
   * Returns a data structure containing user defined commands
   *
   * @return
   */
  public Map<String, UserDefinedCommand> getUserDefinedCommands();

  /**
   * Adds a new user defined command to the data structure containing all user defined commands
   */
  public void addUserDefinedCommands();

  /**
   * Passes in the String of commands to be parsed
   *
   * @param input
   */
  public void parseInput(String input);

  /**
   * Returns the image file of the turtle
   *
   * @return ImageView of the image file
   */
  public ImageView getTurtleImage();

  /**
   * Returns some kind of data structure that the frontend can use to display result of
   * executeCommand
   *
   * @return results of commands to be displayed
   */
  public List<String> getCommandResult();

  /**
   * Returns a data structure containing all Turtle objects Frontend can use this method along with
   * getCommandResult to update all turtles
   *
   * @return list of all Turtle objects
   */
  public List<Turtle> getAllTurtles();

  /**
   * @return the language of the command input
   */
  public String getLanguage();

  void setViewController(FrontEndExternalAPI viewController);

  void passInputToFrontEnd(List<Double> frontEndParameters);


  /**
   * Passes the position of the turtle to the front end. Assumes that 0, 0 is the middle of the
   * screen, with +x to the right and +y up
   *
   * @param xPosition The x position of the turtle
   * @param yPosition The y position of the turtle
   */
  public void setTurtlePosition(double xPosition, double yPosition);

  /**
   * Passes the angle of the turtle to the front end, in degrees. 0 is to the right, and increasing
   * angles rotate counterclockwise
   *
   * @param angle
   */
  public void setTurtleAngle(double angle);

  /**
   * Passes the current pen state to the front end
   *
   * @param penState The pen state. 0 if the pen is not drawing, 1 otherwise
   */
  public void setPenState(double penState);

  /**
   * Passes the turtles visibility to the front end
   *
   * @param visibility The visibility of the turtle. 0 if it is invisible, 1 if it is showing
   */
  public void setTurtleVisibility(double visibility);

  /**
   * Tells the front end when to clear the screen of all lines that have been drawn
   */
  public void clearScreen();

  /**
   * Sets the turtle that is currently active in the front end
   *
   * @param turtleID The ID of the active turtle
   */
  public void setActiveTurtle(int turtleID);

  /**
   * Sets the background color of the display. The index corresponds to a specific color as
   * specified in a properties file
   *
   * @param index The index corresponding to the color
   */
  public void setBackgroundColor(int index);

  /**
   * Sets the pen color that the turtles draw. The index corresponds to a specific color as
   * specified in a properties file
   *
   * @param index The index corresponding to the color
   */
  public void setPenColor(int index);

  /**
   * Sets the shape of the turtle. The index corresponds to a specific shape
   *
   * @param index The index corresponding to the shape
   */
  public void setTurtleShape(int index);

  /**
   * Alters an index of the color palette to hold a new color value
   *
   * @param index The index of the palette to alter
   * @param red   Red value of an rgb color, ranges from 0-255
   * @param green Green value of an rgb color, ranges from 0-255
   * @param blue  Blue value of an rgb color, ranges from 0-255
   */
  public void setPalette(int index, int red, int green, int blue);

  /**
   * Sets the pen size to be a specific pixel width
   *
   * @param penSize The size of the pen, in pixels
   */
  public void setPenSize(double penSize);
}