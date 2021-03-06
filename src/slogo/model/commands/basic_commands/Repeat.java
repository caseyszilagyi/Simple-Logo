package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * This command repeats the given BasicCommands a certain number of times
 *
 * @author Casey Szilagyi
 */
public class Repeat implements BasicCommand {

  private final BasicCommand LOOP_COUNT;
  private final BasicCommand[] COMMANDS;

  /**
   * Makes an instance of the Repeat loop
   *
   * @param commands The chain of basic commands that defines the repeat loop
   */
  public Repeat(BasicCommand... commands) {
    LOOP_COUNT = commands[0];
    COMMANDS = commands;
  }

  /**
   * Repeats the loop the specified number of times
   *
   * @param informationBundle The information bundle needed to modify the turtle/store information
   * @return The value of the final command executed
   */
  public double execute(CommandInformationBundle informationBundle) {
    double val = 0;
    for (int i = 0; i < LOOP_COUNT.execute(informationBundle); i++) {
      for (int commandNumber = 1; commandNumber < COMMANDS.length; commandNumber++) {
        val = COMMANDS[commandNumber].execute(informationBundle);
      }
    }
    return val;
  }
}
