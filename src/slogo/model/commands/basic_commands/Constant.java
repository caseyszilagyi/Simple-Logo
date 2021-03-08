package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * This is the Constant BasicCommand. Note that the constructor is different because it is the
 * lowest level command and therefore can't be constructed out of other commands. Therefore, it must
 * be passed a double in order to be constructed rather than an unspecified number of basic
 * commands
 */
public class Constant implements BasicCommand {

  private final double CONSTANT;

  /**
   * This constructor essentially just stores the int in a BasicCommand object
   *
   * @param value The integer to store
   */
  public Constant(double value) {
    CONSTANT = value;
  }

  /**
   * Returns the constant
   *
   * @return The constant
   */
  @Override
  public double execute() {
    return CONSTANT;
  }

}
