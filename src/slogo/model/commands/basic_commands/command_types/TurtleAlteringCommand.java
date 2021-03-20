package slogo.model.commands.basic_commands.command_types;

import java.util.List;
import java.util.function.Consumer;
import slogo.controller.BackEndExternalAPI;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.Turtle;
import slogo.model.execution.TurtleInformation;

/**
 * This abstract class is designed to be implemented by any BasicCommand that alters the state of
 * the turtle in any way
 *
 * @author Casey Szilagyi
 */
public abstract class TurtleAlteringCommand extends TurtleQueryCommand {

  private Turtle activeTurtle;
  private final TurtleInformation TURTLE_INFORMATION;

  /**
   * Makes the BasicCommand and saves the turtle
   *
   * @param informationBundle The only part of this bundle that is needed is the turtle
   */
  public TurtleAlteringCommand(CommandInformationBundle informationBundle) {
    super(informationBundle);
    activeTurtle = informationBundle.getTurtleInformation().getActiveTurtle();
    TURTLE_INFORMATION = informationBundle.getTurtleInformation();
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
   */
  protected void changeTurtlePosition(double changeX, double changeY) {
    activeTurtle.changePosition(changeX, changeY);
  }

  /**
   * Sets the position of the turtle
   *
   * @param xPosition The final x position of the turtle
   * @param yPosition The final y position of the turtle
   */
  protected void setTurtlePosition(double xPosition, double yPosition) {
    activeTurtle.setPosition(xPosition, yPosition);
  }

  /**
   * Changes the angle of the turtle
   *
   * @param change The change in angle in degrees, in the counter clockwise direction
   */
  protected void changeTurtleAngle(double change) {
    activeTurtle.rotateCounterClockwise(change);
  }

  /**
   * Sets the angle position of the turtle
   *
   * @param angle The angle
   */
  protected void setAngle(double angle) {
    activeTurtle.setAngle(angle);
  }

  /**
   * Sets the pen state
   *
   * @param penState The pen state
   */
  protected void changePenState(double penState) {
    activeTurtle.setPenState(penState);
  }

  /**
   * Sets the turtle visibility
   *
   * @param visibility The visibility
   */
  protected void changeTurtleVisibility(double visibility) {
    activeTurtle.setVisibility(visibility);
  }

  /**
   * Resets the screen
   */
  protected void reset() {
    activeTurtle.clearScreen();
  }

  protected void updateFrontEnd() {
    TURTLE_INFORMATION.updateFrontEnd();
  }

  protected void setActiveTurtle(int ID) {
    TURTLE_INFORMATION.setActiveTurtle(ID);
  }


  /**
   * Updates all of the active turtles
   *
   * @param turtleAction The method calls that are called on the turtles
   */
  protected void updateTurtle(Consumer<Turtle> turtleAction) {
    List<Integer> activeTurtleList = TURTLE_INFORMATION.getCurrentActiveTurtleList();
    for (int turtleID : activeTurtleList) {
      setActiveTurtle(turtleID);
      activeTurtle = TURTLE_INFORMATION.getActiveTurtle();
      turtleAction.accept(activeTurtle);
      updateFrontEnd();
    }

  }


}
