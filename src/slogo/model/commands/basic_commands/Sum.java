package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;
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
   * @param informationBundle The bundle that has the turtle and variables
   * @return The sum of the two numbers
   */
  public double execute(CommandInformationBundle informationBundle) {
    double result = 0;
    for (int i = 0; i < VALUES_TO_SUM.length; i++) {
      result += VALUES_TO_SUM[i].execute(informationBundle);
    }
    return result;
  }
}
