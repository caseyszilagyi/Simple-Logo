package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Checks to see if a node is 0
 *
 * @author Casey Szilagyi
 */
public class Not extends MathAndLogicCommand {

  private final double VALUE;

  /**
   * Makes an instance of the not command
   *
   * @param bundle Not used for this command type
   * @param nodes  The child node to be checked
   */
  public Not(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    VALUE = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Checks to see if the value is 0
   *
   * @return 1 if the value is 0, 0 if it is not
   */
  @Override
  public double execute() {
    if (VALUE == 0) {
      return 1;
    }
    return 0;
  }
}
