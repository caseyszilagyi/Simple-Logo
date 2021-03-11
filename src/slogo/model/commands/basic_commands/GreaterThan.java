package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.Command;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command compares the values of the two children nodes.
 *
 * @author Casey Szilagyi
 */
public class GreaterThan extends MathAndLogicCommand {

  private final double EXPRESSION_1;
  private final double EXPRESSION_2;

  /**
   * Makes an instance of the greater than command, which will compare two node values
   *
   * @param bundle Not used for this command type
   * @param nodes The two children to be compared
   */
  public GreaterThan(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    EXPRESSION_1 = loadClass(bundle, nodes.get(0)).execute();
    EXPRESSION_2 = loadClass(bundle, nodes.get(1)).execute();
  }

  /**
   * Compares the values of the two expressions
   *
   * @return 1 if the first expression is greater than the second, 0 otherwise
   */
  public double execute() {
    if (EXPRESSION_1 > EXPRESSION_2) {
      return 1;
    }
    return 0;
  }
}
