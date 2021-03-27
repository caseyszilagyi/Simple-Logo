package slogo.controller;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.html.ImageView;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.Turtle;
import slogo.view.ScreenCreator;

/**
 * Receives information from backend and passes the information to frontend
 * @author Ji Yun Hyo
 */
public class ViewController implements FrontEndExternalAPI {

  BackEndExternalAPI modelController;
  ScreenCreator screenCreator;
  private String userCommandInputs;
  private final Deque<String> commandHistory;

  private final Map<String, String> userDefinedHistory;
  private int NAME_OF_USER_DEFINED_COMMANDS = 1;
  private String EXAMPLE_PARAMETER = " 50";

  /**
   * Default constructor
   */
  public ViewController() {
    screenCreator = new ScreenCreator(this);
    commandHistory = new ArrayDeque<>();
    userDefinedHistory = new HashMap<>();
  }

  /**
   * Sets the background color of the display. The index corresponds to a specific color as
   * specified in a properties file
   * @param index The index corresponding to the color
   */
  @Override
  public void setBackGroundColor(int index) {
    // TODO implement here
  }

  /**
   * Sets the image of the specific turtle with the image
   * @param turtle turtle ID
   * @param image image file
   */
  @Override
  public void setTurtleImage(Turtle turtle, ImageView image) {
    // TODO implement here
  }

  /**
   * Gets the language of the command and UI.
   * There is a dependency with ScreenCreator
   */
  public String getLanguage() {
    // TODO implement here
    return screenCreator.getLanguage();
  }

  /**
   * Returns the command history
   * Dependency: ScreenCreator
   */
  public Deque<String> getCommandHistory() {
    // TODO implement here
    return commandHistory;
  }

  /**
   * returns the error message to be displayed
   * @param errorMessage error message to be displayed
   */
  public void displayError(String errorMessage) {
    // TODO implement here
  }

  /**
   * sets the model controller to set up the line of communication from/to the backend
   * @param modelController BackEndExternalAPI
   */
  @Override
  public void setModelController(BackEndExternalAPI modelController) {
    this.modelController = modelController;
  }

  /**
   * Returns the user command input to be parsed
   * @return user command input
   */
  @Override
  public String getUserCommandInput() {
    return userCommandInputs;
  }

  /**
   * Processes the command input from the text area. Primary method that passes the user input
   * to the backend to be parsed
   * Dependency exists with model controller and the backend classes.
   * @param userCommandInputs command string of the user input
   */
  @Override
  public void processUserCommandInput(String userCommandInputs) {
    commandHistory.offerFirst(userCommandInputs);
    screenCreator.updateCommandHistory(commandHistory);

    this.userCommandInputs = userCommandInputs;
    modelController.executeCommand(userCommandInputs);
  }

  /**
   * Displays the actual command when the user clicks on the command history/user-defined commands button
   * Dependencies exist with ScreenCreator
   * @param command String to be displayed in the text area
   */
  @Override
  public void displayCommandStringOnTextArea(String command) {
    screenCreator.displayCommandStringOnTextArea(command);
  }

  /**
   * Sends the information to the frontend so that the command history/variables tabs can be updated
   * Dependency exists with model controller method that gets the information from the backend.
   * Dependency exists with ScreenCreator class from the frontend that calls this method.
   * @param variables information about all the variables user has defined
   * @param userDefinedCommands information about all the user-defined commands
   */
  @Override
  public void updateFrontEnd(Map<String, Double> variables,
                             Map<String, UserDefinedCommand> userDefinedCommands) {
    screenCreator.updateVariablesAndUserDefinedCommands(variables, userDefinedCommands);
  }

  /**
   * Sets the ID of the turtle to be updated/displayed
   * @param turtleID ID of the turtle to be updated/displayed for animation
   */
  @Override
  public void setActiveTurtle(int turtleID) {
//    System.out.println();
//    System.out.println("Active turtle ID: " + turtleID);
    screenCreator.setActiveTurtle(turtleID);
  }

  /**
   * Sets the turtle position to be updated to the x and y coordinates
   * Dependency exists with the ScreenCreator class that calls this method
   * @param xPosition xCoord of the turtle
   * @param yPosition yCoord of the turtle
   */
  @Override
  public void setTurtlePosition(double xPosition, double yPosition) {
//    System.out.println("xPosition of Turtle (ViewController): " + xPosition);
//    System.out.println("yPosition of Turtle (ViewController): " + yPosition);
    screenCreator.setTurtlePosition(xPosition,yPosition);
  }

  /**
   * Sets the turtle angle
   * Dependency exists with ScreenCreator class that calls this method
   * @param angle angle to be set
   */
  @Override
  public void setTurtleAngle(double angle) {
//    System.out.println("Angle of Turtle: " + angle);
    screenCreator.updateCommandQueue("Angles", Collections.singletonList(angle));
  }

  /**
   * Sets the turtle's pen state
   * Dependency: ScreenCreator class
   * @param penState sets pen down or pen up
   */
  @Override
  public void setPenState(double penState) {
    screenCreator.updateCommandQueue("Pen", Collections.singletonList(penState));
  }

  /**
   * Sets the visibility of the turtle
   * Dependency: ScreenCreator class that calls this method
   * @param visibility visibility of the turtle
   */
  @Override
  public void setTurtleVisibility(double visibility) {
    screenCreator.updateCommandQueue("Visibility", Collections.singletonList(visibility));
  }

  /**
   * Clears the screen and puts all turtles at the origin
   * Dependency: ScreenCreator class that calls this method
   */
  @Override
  public void clearScreen() {
    screenCreator.clearScreen();
  }

  /**
   * Sets pen color
   * Dependency: ScreenCreator class that calls this method
   * @param index index of the pen color
   */
  @Override
  public void setPenColor(int index) {
    double in = index;
    screenCreator.updateCommandQueue("PenColor", Collections.singletonList(in));
  }

  /**
   * Sets the turtle shape
   * Dependency: ScreenCreator class that calls this method
   * @param index index representing the turtle shape
   */
  @Override
  public void setTurtleShape(int index) {
    double in = index;
    screenCreator.updateCommandQueue("PenColor", Collections.singletonList(in));
  }

  /**
   * Sets the color palette
   * Dependency: ScreenCreator class that calls this method
   * @param index index of the color palette
   * @param red red value out of 255
   * @param green green value out of 255
   * @param blue blue value out of 255
   */
  @Override
  public void setPalette(int index, int red, int green, int blue) {
    double in = index;
    screenCreator.updateCommandQueue("PenColor", Collections.singletonList(in));
  }

  /**
   * Sets the pensize
   * Dependency: ScreenCreator class that calls this method
   * @param penSize double representing the pen size
   */
  @Override
  public void setPenSize(double penSize) {
    screenCreator.updateCommandQueue("Thickness", Collections.singletonList(penSize));
  }

  /**
   * Passes the information to the frontend (ScreenCreator) so that the type and the commands
   * can be added to the queue for animation.
   * Dependency: ScreenCreator class that calls this method
   * @param commandType command type to be executed
   * @param commandValues values of the results of the commands
   */
  @Override
  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    //  screenCreator.updateCommandQueue(commandType, commandValues);
  }

  /**
   * Sets the language of the command and the UI
   * Dependency: ScreenCreator class that calls this method
   * @param language string representing the language
   */
  @Override
  public void setLanguage(String language) {
    screenCreator.updateLanguage(language);
  }

  /**
   * This allows the frontend to keep a list of all frontend turtles that are set to active by the
   * backend. This allows the frontend to display different turtle images for active/inactive turtles
   * @param iDs all IDs of the active turtles
   */
  @Override
  public void setActiveTurtles(List<Integer> iDs) {
    screenCreator.setActiveTurtles(iDs);
//    System.out.println("All active turtles: " + iDs);
  }

}
