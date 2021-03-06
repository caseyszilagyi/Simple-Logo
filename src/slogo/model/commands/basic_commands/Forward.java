package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;
import slogo.model.turtle.Turtle;

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
   * Makes the turtle move the distance forward that was specified in the constructor
   *
   * @param informationBundle The bundle of all information that is needed
   * @return The distance forward that it moved
   */
  public int execute(CommandInformationBundle informationBundle) {
    Turtle turtle = informationBundle.getTurtle();
    turtle.changeXPosition(Math.cos(turtle.getAngle()) * DISTANCE.execute(informationBundle));
    turtle.changeYPosition(Math.sin(turtle.getAngle()) * DISTANCE.execute(informationBundle));
    return DISTANCE.execute(informationBundle);
  }
}
