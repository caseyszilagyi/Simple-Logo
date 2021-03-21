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
   * Sets the background color of the GUI
   */
  public void setBackGroundColor(String color);

  /**
   * Sets the image of the turtle
   */
  public void setTurtleImage(Turtle turtle, ImageView image);

  /**
   * Sets the language of the commands
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

  public void passInputFromBackendToFrontEnd(List<Double> parameters);

  public void displayCommandStringOnTextArea(String command);

  public Map<String, Double> getVariables();

  public void updateFrontEnd(Map<String, Double> variables,
      Map<String, UserDefinedCommand> userDefinedCommands);

  void setLanguage(String language);
}