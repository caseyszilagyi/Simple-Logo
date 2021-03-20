package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the difference between two numbers
 *
 * @author jincho
 */
public class Difference extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the difference command
   *
   * @param bundle   Not used for this command
   * @param children Has 2 children, which contain the values to take the difference of
   */
  public Difference(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Calculate the difference
   *
   * @return The difference between the numbers
   */
  @Override
  public double execute() {
    return VALUE_1 - VALUE_2;
  }

}
