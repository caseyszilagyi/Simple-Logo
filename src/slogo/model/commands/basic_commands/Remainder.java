package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Gets the remainder of a division operation
 *
 * @author Casey Szilagyi
 */
public class Remainder extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the remainder command
   *
   * @param bundle   Not used for this command
   * @param children The two TreeNodes to divide and get the remainder of
   */
  public Remainder(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Gives the remainder of the division operation
   *
   * @return The remainder
   */
  @Override
  public double execute() {
    return VALUE_1 % VALUE_2;
  }
}
