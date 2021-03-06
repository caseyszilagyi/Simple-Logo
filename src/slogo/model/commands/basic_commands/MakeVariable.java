package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;
import slogo.model.turtle.Turtle;

/**
 * This command makes and stores a variable for later use
 *
 * @author Casey Szilagyi
 */
public class MakeVariable implements BasicCommand {

  private final BasicCommand VARIABLE;

  /**
   * Makes an instance of the MakeVariable command
   *
   * @param commands The
   */
  public MakeVariable(BasicCommand... commands) {
    VARIABLE = commands[0];
  }

  /**
   * Makes the turtle move the distance forward that was specified in the constructor
   *
   * @param informationBundle The turtle object
   * @return The distance forward that it moved
   */
  public int execute(CommandInformationBundle informationBundle) {
    return  0;
  }
}
