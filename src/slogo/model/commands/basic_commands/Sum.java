package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the sum of two numbers
 *
 * @author Casey Szilagyi
 */
public class Sum extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the sum command
   *
   * @param bundle   Not used for this command
   * @param children The children are the two nodes to sum
   */
  public Sum(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Sums the two numbers
   *
   * @return The sum of the two numbers
   */
  @Override
  public double execute() {
    return VALUE_1 + VALUE_2;
  }
}
