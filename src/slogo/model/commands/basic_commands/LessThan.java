package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * The LessThan Command
 *
 * @author Casey Szilagyi
 */
public class LessThan implements BasicCommand {

  private final BasicCommand EXPRESSION_1;
  private final BasicCommand EXPRESSION_2;

  /**
   * Makes an instance of the less than command
   *
   * @param commands The two basicCommand objects that will give double values to compare
   */
  public LessThan(BasicCommand... commands) {
    EXPRESSION_1 = commands[0];
    EXPRESSION_2 = commands[1];
  }

  /**
   * Compares the values of the two expressions
   *
   * @param informationBundle The bundle of the turtle and variables
   * @return 1 if the first expression is less than the second, 0 otherwise
   */
  public double execute(CommandInformationBundle informationBundle) {
    if (EXPRESSION_1.execute(informationBundle) < EXPRESSION_2.execute(informationBundle)) {
      return 1;
    }
    return 0;
  }
}
