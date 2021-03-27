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
   * Backend can call this method to relay information to be displayed to the frontend
   */
  public Queue<String> getCommandHistory();

  /**
   * Displays error message
   */
  public void displayError(String errorMessage);

  public void setModelController(BackEndExternalAPI modelController);

  public String getUserCommandInput();

  public void processUserCommandInput(String userCommandInput);

  void displayCommandStringOnTextArea(String command);

  void updateFrontEnd(Map<String, Double> variables,
                      Map<String, UserDefinedCommand> userDefinedCommands);

  void setActiveTurtle(int turtleID);

  void setTurtlePosition(double xPosition, double yPosition);

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
