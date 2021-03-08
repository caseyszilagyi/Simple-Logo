package slogo.model.commands.basic_commands.command_types;

import java.util.List;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * This abstract class is designed to be implemented by any BasicCommand that needs any
 * data from the turtle in any way. It does not have the ability to alter the state
 * of the turtle
 */
public abstract class TurtleQueryCommand extends Command {

  private final Turtle TURTLE;

  /**
   * Makes the BasicCommand and saves the Turtle
   *
   * @param informationBundle The bundle of information that contains the turtle
   */
  public TurtleQueryCommand(CommandInformationBundle informationBundle){
    TURTLE = informationBundle.getTurtle();
  }

  /**
   * Gets the X Coordinate of the turtle
   * @return The X coordinate
   */
  protected double getXCoordinate(){
    return TURTLE.getXPosition();
  }

  /**
   * Gets the Y Coordinate of the turtle
   * @return The Y coordinate
   */
  protected double getYCoordinate(){
    return TURTLE.getYPosition();
  }

  /**
   * Gets the angle of the turtle
   * @return The angle
   */
  protected double getAngle(){
    return TURTLE.getAngle();
  }

  /**
   * Gets the pen state
   * @return The pen state
   */
  protected double getPenState(){
    return TURTLE.getPenState();
  }

  /**
   * Gets whether the turtle is showing
   * @return The pen state
   */
  protected double getVisibility(){
    return TURTLE.getVisibility();
  }

}
