package slogo.model.commands.basic_commands.command_types;

import slogo.model.execution.CommandInformationBundle;

/**
 * This abstract class is meant to be implemented by any math/logic command that doesn't need any
 * other information to function
 *
 * @author Casey Szilagyi
 */
public abstract class MathAndLogicCommand extends Command {

  /**
   * Constructs an instance of this class type
   *
   * @param informationBundle None of this bundle is used for any command that deals with
   *                          math/logic
   */
  public MathAndLogicCommand(CommandInformationBundle informationBundle) {
  }


}
