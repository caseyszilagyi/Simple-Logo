package slogo.model.commands.basic_commands;

import slogo.model.turtle.Turtle;

/**
 * The backward command
 *
 * @author Casey Szilagyi
 */
public class Back implements BasicCommand {

  private final BasicCommand DISTANCE;

  /**
   * Makes an instance of the back command
   *
   * @param commands The distance back that it will move
   */
  public Back(BasicCommand... commands) {
    DISTANCE = commands[0];
  }

  /**
   * Makes the turtle move the distance back that was specified in the constructor
   *
   * @param turtle The turtle object
   * @return The distance backward that it moved
   */
  public int execute(Turtle turtle) {
    turtle.setXPosition(
        turtle.getXPosition() - Math.cos(turtle.getAngle()) * DISTANCE.execute(turtle));
    turtle.setYPosition(
        turtle.getYPosition() - Math.sin(turtle.getAngle()) * DISTANCE.execute(turtle));
    return DISTANCE.execute(turtle);
  }
}
