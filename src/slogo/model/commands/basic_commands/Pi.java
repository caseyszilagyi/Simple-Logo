package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Returns the value of Pi
 *
 * @author Casey Szilagyi
 */
public class Pi extends MathAndLogicCommand {

  /**
   * Makes an instance of the pi command
   *
   * @param bundle   Not used for this command
   * @param children Null because the pi command has no children
   */
  public Pi(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
  }

  /**
   * Gives the value of Pi
   *
   * @return Pi
   */
  @Override
  public double execute() {
    return Math.PI;
  }
}
