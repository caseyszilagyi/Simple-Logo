package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;
import slogo.model.turtle.Turtle;

/**
 * The backward command
 *
 * @author Casey Szilagyi
 */
public class Backward implements BasicCommand {

  private final BasicCommand DISTANCE;

  /**
   * Makes an instance of the back command
   *
   * @param commands The distance back that it will move
   */
  public Backward(BasicCommand... commands) {
    DISTANCE = commands[0];
  }

  /**
   * Makes the turtle move the distance back that was specified in the constructor
   *
   * @param informationBundle The turtle object
   * @return The distance backward that it moved
   */
  public int execute(CommandInformationBundle informationBundle) {
    Turtle turtle = informationBundle.getTurtle();
    turtle.changeXPosition(-1 * Math.cos(turtle.getAngle()) * DISTANCE.execute(informationBundle));
    turtle.changeYPosition(-1 * Math.sin(turtle.getAngle()) * DISTANCE.execute(informationBundle));
    return DISTANCE.execute(informationBundle);
  }
}
