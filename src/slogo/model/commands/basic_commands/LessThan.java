package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The LessThan Command
 *
 * @author Casey Szilagyi
 */
public class LessThan implements BasicCommand {

  private final TreeNode EXPRESSION_1;
  private final TreeNode EXPRESSION_2;

  /**
   * Makes an instance of the less than command
   *
   * @param nodes The TreeNode objects that will condense to integers to compare
   */
  public LessThan(List<TreeNode> nodes) {
    EXPRESSION_1 = nodes.get(0);
    EXPRESSION_2 = nodes.get(1);
  }

  /**
   * Compares the values of the two expressions
   *
   * @param informationBundle The bundle of the turtle and variables
   * @return 1 if the first expression is less than the second, 0 otherwise
   */
  public double execute(CommandInformationBundle informationBundle) {
    if (informationBundle.loadClass(EXPRESSION_1).execute(informationBundle) < informationBundle
        .loadClass(EXPRESSION_2).execute(informationBundle)) {
      return 1;
    }
    return 0;
  }
}
