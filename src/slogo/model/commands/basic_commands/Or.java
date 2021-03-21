package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Checks to see if either node is nonzero
 *
 * @author Casey Szilagyi
 */
public class Or extends MathAndLogicCommand {

  private final double EXPRESSION_1;
  private final double EXPRESSION_2;

  /**
   * Makes an instance of the or command
   *
   * @param bundle Not used for this command type
   * @param nodes  The two children to be checked
   */
  public Or(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    EXPRESSION_1 = loadClass(bundle, nodes.get(0)).execute();
    EXPRESSION_2 = loadClass(bundle, nodes.get(1)).execute();
  }

  /**
   * Checks to see if either of the values are nonzero
   *
   * @return 1 if either expression is nonzero
   */
  @Override
  public double execute() {
    if (EXPRESSION_1 != 0 || EXPRESSION_2 != 0) {
      return 1;
    }
    return 0;
  }
}
