package slogo.model.commands.basic_commands;

import slogo.model.turtle.Turtle;
import java.lang.Math.*;

/**
 * The forward command
 *
 * @author Casey Szilagyi
 */
public class Forward implements BasicCommand {

  private final BasicCommand DISTANCE;

  /**
   * Makes an instance of the forward command
   *
   * @param commands The distance forward that it will move
   */
  public Forward(BasicCommand... commands) {
    DISTANCE = commands[0];
  }

  /**
   * Makes the TurtleMovement object that dictates how the turtle will move
   *
   * @return The TurtleMovement object
   */
  public int execute(Turtle turtle) {
    turtle.setXPosition(turtle.getXPosition() + Math.cos(turtle.getAngle()) * DISTANCE.execute(turtle));
    turtle.setYPosition(turtle.getYPosition() + Math.sin(turtle.getAngle()) * DISTANCE.execute(turtle));
    return DISTANCE.execute(turtle);
  }
}
