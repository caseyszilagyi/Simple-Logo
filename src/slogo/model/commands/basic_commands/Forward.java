package slogo.model.commands.basic_commands;

import slogo.model.turtle.Turtle;
import java.lang.Math.*;

/**
 * The forward command
 *
 * @author Casey Szilagyi
 */
public class Forward implements BasicCommand {

  private final int DISTANCE;

  /**
   * Makes an instance of the forward command
   *
   * @param args The distance forward that it will move
   */
  public Forward(int... args) {
    DISTANCE = args[0];
  }

  /**
   * Makes the TurtleMovement object that dictates how the turtle will move
   *
   * @return The TurtleMovement object
   */
  public void execute(Turtle turtle) {
    turtle.setXPosition(turtle.getXPosition() + Math.cos(turtle.getAngle()) * DISTANCE);
    turtle.setYPosition(turtle.getYPosition() + Math.sin(turtle.getAngle()) * DISTANCE);
  }
}
