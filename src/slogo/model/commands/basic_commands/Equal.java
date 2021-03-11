package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.Command;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The Equal command, which checks if the value of two nodes are equal
 *
 * @author Casey Szilagyi
 */
public class Equal extends MathAndLogicCommand {

  private final double EXPRESSION_1;
  private final double EXPRESSION_2;

  /**
   * Makes an instance of the equal command
   *
   * @param bundle Not used for this command type
   * @param nodes The two children to be compared
   */
  public Equal(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    EXPRESSION_1 = loadClass(bundle, nodes.get(0)).execute();
    EXPRESSION_2 = loadClass(bundle, nodes.get(1)).execute();
  }

  /**
   * Compares the values of the two expressions
   *
   * @return 1 if the first expression is less than the second, 0 otherwise
   */
  public double execute() {
    if (EXPRESSION_1 == EXPRESSION_2) {
      return 1;
    }
    return 0;
  }
}
