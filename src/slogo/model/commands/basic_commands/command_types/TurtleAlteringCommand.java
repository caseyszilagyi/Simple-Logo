package slogo.model.commands.basic_commands.command_types;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.Turtle;
import slogo.model.execution.TurtleInformation;
import slogo.model.tree.TreeNode;

/**
 * This abstract class is designed to be implemented by any BasicCommand that alters the state of
 * the turtle in any way
 *
 * @author Casey Szilagyi
 */
public abstract class TurtleAlteringCommand extends TurtleQueryCommand {

  private Turtle activeTurtle;
  private final TurtleInformation TURTLE_INFORMATION;
  private final CommandInformationBundle INFORMATION_BUNDLE;

  /**
   * Makes the BasicCommand and saves the turtle
   *
   * @param informationBundle The only part of this bundle that is needed is the turtle
   */
  public TurtleAlteringCommand(CommandInformationBundle informationBundle) {
    super(informationBundle);
    activeTurtle = informationBundle.getTurtleInformation().getActiveTurtle();
    TURTLE_INFORMATION = informationBundle.getTurtleInformation();
    INFORMATION_BUNDLE = informationBundle;
  }

  /**
   * Executes the command. This is implemented in the subclass because it is different for each
   * command
   *
   * @return The double value representing the result of the executed method
   */
  public abstract double execute();

  /**
   * Changes the position of the turtle by a certain amount
   *
   * @param changeX The change in X position
   * @param changeY The change in Y position
   * @return The total change in position
   */
  protected double changeTurtlePosition(double changeX, double changeY) {
    activeTurtle.changePosition(changeX, changeY);
    return(Math.sqrt(Math.pow(changeX, 2) + Math.pow(changeY, 2)));
  }

  /**
   * Sets the position of the turtle
   *
   * @param xPosition The final x position of the turtle
   * @param yPosition The final y position of the turtle
   * @return The total change in position
   */
  protected double setTurtlePosition(double xPosition, double yPosition) {
    double oldX = activeTurtle.getXPosition();
    double oldY = activeTurtle.getYPosition();
    activeTurtle.setPosition(xPosition, yPosition);
    return Math.sqrt(Math.pow(oldX-xPosition, 2) + Math.pow(oldY-yPosition, 2));
  }

  /**
   * Changes the angle of the turtle
   *
   * @param change The change in angle in degrees, in the counter clockwise direction
   * @return the total change in the angle
   */
  protected double rotateCounterClockwise(double change) {
    activeTurtle.rotateCounterClockwise(change);
    return Math.abs(change);
  }

  /**
   * Sets the angle position of the turtle
   *
   * @param angle The angle
   * @return the total change in the angle
   */
  protected double setAngle(double angle) {
    double oldAngle = getAngle();
    activeTurtle.setAngle(angle);
    return(Math.abs(oldAngle-angle));
  }

  /**
   * Sets the pen state
   *
   * @param penState The pen state
   * @return The pen state
   */
  protected double changePenState(double penState) {
    activeTurtle.setPenState(penState);
    return penState;
  }

  /**
   * Sets the turtle visibility
   *
   * @param visibility The visibility
   * @return The visibility
   */
  protected double changeTurtleVisibility(double visibility) {
    activeTurtle.setVisibility(visibility);
    return visibility;
  }

  /**
   * Resets the screen
   */
  protected void reset() {
    activeTurtle.clearScreen();
  }


  /**
   * Updates all of the active turtles
   *
   * @param turtleAction The method calls that are called on the turtles
   * @return The value of the executed command. Always returns the value after execution
   * on the last turtle
   */
  protected double updateTurtle(ToDoubleFunction<Turtle> turtleAction) {
    List<Integer> activeTurtleList = TURTLE_INFORMATION.getCurrentActiveTurtleList();
    double val = 0;
    for (int turtleID : activeTurtleList) {
      setActiveTurtle(turtleID);
      activeTurtle = TURTLE_INFORMATION.getActiveTurtle();
      val = turtleAction.applyAsDouble(activeTurtle);
    }
    return val;
  }

  /**
   * Executes the node corresponding to a command. This is necessary so that the children
   * of each command execute differently if there are queries involved that involve the specific
   * turtles
   *
   * @param node The node that holds all of the commands to execute
   * @return The double value that represents the executed node
   */
  protected double executeNode(TreeNode node) {
    return loadClass(INFORMATION_BUNDLE, node).execute();
  }

  // Changes the turtle that is active
  private void setActiveTurtle(int ID) {
    TURTLE_INFORMATION.setActiveTurtle(ID);
  }


}
