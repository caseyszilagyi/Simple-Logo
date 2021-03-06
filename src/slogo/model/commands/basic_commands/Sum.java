package slogo.model.commands.basic_commands;

import slogo.model.turtle.Turtle;

/**
 * The sum command
 *
 * @author Casey Szilagyi
 */
public class Sum implements BasicCommand {

  private final BasicCommand[] VALUES_TO_SUM;

  /**
   * Makes an instance of the sum command
   *
   * @param commands The BasicCommands to sum
   */
  public Sum(BasicCommand... commands) {
    VALUES_TO_SUM = commands;
  }

  /**
   * Makes the turtle move the distance back that was specified in the constructor
   *
   * @param turtle The turtle object
   * @return The distance backward that it moved
   */
  public int execute(Turtle turtle) {
    int result = 0;
    for (int i = 0; i < VALUES_TO_SUM.length; i++) {
      result += VALUES_TO_SUM[i].execute(turtle);
    }
    return result;
  }
}
