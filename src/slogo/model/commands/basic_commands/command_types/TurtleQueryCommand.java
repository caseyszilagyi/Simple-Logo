package slogo.model.commands.basic_commands.command_types;

import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.Turtle;
import slogo.model.execution.TurtleInformation;

/**
 * This abstract class is designed to be implemented by any BasicCommand that needs any data from
 * the turtle in any way. It does not have the ability to alter the state of the turtle
 */
public abstract class TurtleQueryCommand extends Command {

  private Turtle activeTurtle;
  private final TurtleInformation TURTLE_INFORMATION;

  /**
   * Makes the BasicCommand and saves the Turtle
   *
   * @param informationBundle The bundle of information that contains the turtle
   */
  public TurtleQueryCommand(CommandInformationBundle informationBundle) {
    activeTurtle = informationBundle.getTurtleInformation().getActiveTurtle();
    TURTLE_INFORMATION = informationBundle.getTurtleInformation();
  }

  /**
   * Gets the X Coordinate of the turtle
   *
   * @return The X coordinate
   */
  protected double getXCoordinate() {
    return activeTurtle.getXPosition();
  }

  /**
   * Gets the Y Coordinate of the turtle
   *
   * @return The Y coordinate
   */
  protected double getYCoordinate() {
    return activeTurtle.getYPosition();
  }

  /**
   * Gets the angle of the turtle
   *
   * @return The angle
   */
  protected double getAngle() {
    return activeTurtle.getAngle();
  }

  /**
   * Gets the pen state
   *
   * @return The pen state
   */
  protected double getPenState() {
    return activeTurtle.getPenState();
  }

  /**
   * Gets whether the turtle is showing
   *
   * @return The pen state
   */
  protected double getVisibility() {
    return activeTurtle.getVisibility();
  }

  /**
   * Gets the ID of the current turtle
   *
   * @return The ID
   */
  protected int getID() {
    return activeTurtle.getID();
  }

  /**
   * Updates the active turtle that the queries will act on
   */
  protected void updateActiveTurtle(){
    activeTurtle = TURTLE_INFORMATION.getActiveTurtle();
  }
}
