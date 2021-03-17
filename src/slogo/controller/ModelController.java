package slogo.controller;

import java.util.List;
import java.util.Map;
import javax.swing.text.html.ImageView;
import slogo.model.parse.CommandParser;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * @author Ji Yun Hyo
 */
public class ModelController implements BackEndExternalAPI {

  FrontEndExternalAPI viewController;
  CommandInformationBundle commandInformationBundle;
  BasicCommandClassLoader basicCommandClassLoader;

  /**
   * Default constructor
   */
  public ModelController() {

    commandInformationBundle = new CommandInformationBundle(this);
    basicCommandClassLoader = new BasicCommandClassLoader();

  }

  /**
   * @return
   */
  public List<String> getCommandHistory() {
    // TODO implement here
    // get the
    return null;
  }

  /**
   *
   */
  public void removeUserDefinedCommand() {
    // TODO implement here
  }

  /**
   *
   */
  public void addVariable() {
    // TODO implement here
  }

  /**
   *
   */
  public void removeVariable() {
    // TODO implement here
  }

  /**
   * gives access to the value a variable represents
   *
   * @param var variable name to get
   */
  public Double getSingleVariable(String var) {
    // TODO implement here
    return commandInformationBundle.getVariableMap().get(var);
  }

  /**
   * gives access to the value a variable represents
   */
  public Map<String, Double> getVariables() {
    // TODO implement here
    return commandInformationBundle.getVariableMap();
  }

  /**
   * gives "global" access to all user defined commands so that user can access every time they user
   * input
   *
   * @return map of command names to their command tree root nodes
   */
  public Map<String, UserDefinedCommand> getUserDefinedCommands() {
    // TODO implement here
    return commandInformationBundle.getCommandMap();
  }

  /**
   *
   */
  public void addUserDefinedCommands() {
    // TODO implement here
  }

  /**
   * parses through input and creates a tree. it then executes all the commands in that tree
   *
   * @param input String input
   */
  public void parseInput(String input) {
    // TODO implement here
    System.out.println("ModelController received the following string as input: \n" + input);
    String language = viewController.getLanguage();
    CommandParser commandParser = new CommandParser(input, language, this);
    TreeNode inputRoot = commandParser.makeTree();

    //NEEDS TO BE REFACTORED TO MAKE SURE WE ADHERE TO DEPENDENCY INVERSION PRINCIPLE
    // inputRoot is null and the command starts from its child
//        CommandInformationBundle commandInformationBundle = new CommandInformationBundle(this);
//        BasicCommandClassLoader basicCommandClassLoader = new BasicCommandClassLoader();
    for (TreeNode child : inputRoot.getChildren()) {
      System.out.println("Value of child of root: " + child.getValue());
      basicCommandClassLoader.makeCommand(commandInformationBundle, child).execute();
    }
    viewController.updateFrontEnd(getVariables(), getUserDefinedCommands());
  }

  /**
   * @param error
   */
  public void handleInputError(String error) {

  }

  /**
   * @return
   */
  public ImageView getTurtleImage() {
    // TODO implement here
    return null;
  }

  /**
   * @return
   */
  public List<String> getCommandResult() {
    // TODO implement here
    return null;
  }

  /**
   * @return
   */
  public List<Turtle> getAllTurtles() {
    // TODO implement here
    return null;
  }

  /**
   * @return the language of the command input
   */
  @Override
  public String getLanguage() {
    try {
      return viewController.getLanguage();
    } catch (Exception e) {
      System.out.println("View Controller doesn't exist!!");
      throw e;
    }
  }

  @Override
  public void setViewController(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
  }

  // Below are all the methods used to pass information to the front end

  /**
   * Passes the arraylist of values necessary to modify the turtle in the front end. This method is
   * intended to be called in the model when the turtle is updated
   *
   * @param parameters The List of parameters to pass to the view
   */
  public void passInputToFrontEnd(List<Double> parameters) {
    //: TODO Call a method on the viewController and pass it this arraylist of parameters
    if (viewController != null) {
      viewController.passInputFromBackendToFrontEnd(parameters);
    }
  }

  /**
   * Passes the position of the turtle to the front end. Assumes that 0, 0 is the middle of the
   * screen, with +x to the right and +y up
   *
   * @param xPosition The x position of the turtle
   * @param yPosition The y position of the turtle
   */
  public void setTurtlePosition(double xPosition, double yPosition) {

  }

  /**
   * Passes the angle of the turtle to the front end, in degrees. 0 is to the right, and increasing
   * angles rotate counterclockwise
   *
   * @param angle
   */
  public void setTurtleAngle(double angle) {

  }

  /**
   * Passes the current pen state to the front end
   *
   * @param penState The pen state. 0 if the pen is not drawing, 1 otherwise
   */
  public void setPenState(double penState) {

  }

  /**
   * Passes the turtles visibility to the front end
   *
   * @param visibility The visibility of the turtle. 0 if it is invisible, 1 if it is showing
   */
  public void setTurtleVisibility(double visibility) {

  }

  /**
   * Tells the front end when to clear the screen of all lines that have been drawn
   */
  public void clearScreen() {

  }

  /**
   * Sets the turtle that is currently active in the front end
   *
   * @param turtleID The ID of the active turtle
   */
  public void setActiveTurtle(int turtleID) {

  }

  /**
   * Sets the background color of the display. The index corresponds to a specific color as
   * specified in a properties file
   *
   * @param index The index corresponding to the color
   */
  public void setBackgroundColor(int index) {

  }

  /**
   * Sets the pen color that the turtles draw. The index corresponds to a specific color as
   * specified in a properties file
   *
   * @param index The index corresponding to the color
   */
  public void setPenColor(int index) {

  }

  /**
   * Sets the shape of the turtle. The index corresponds to a specific shape
   *
   * @param index The index corresponding to the shape
   */
  public void setShape(int index) {

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

  }

  /**
   * Sets the pen size to be a specific pixel width
   *
   * @param penSize The size of the pen, in pixels
   */
  public void setPenSize(double penSize) {

  }


}