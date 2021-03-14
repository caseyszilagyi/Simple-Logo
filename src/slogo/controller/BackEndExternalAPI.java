package slogo.controller;

import java.util.List;
import java.util.Map;
import javax.swing.text.html.ImageView;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.turtle.Turtle;

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
   * Passes in the String of commmands to be parsed
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
   * @return results of commands to be displlayed
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
}