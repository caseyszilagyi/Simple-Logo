package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command decides whether to execute the given block of commands
 *
 * @author Casey Szilagyi
 */
public class If extends ControlStructureCommand {

  private final double CONDITIONAL;
  private final TreeNode COMMAND_BLOCK;

  /**
   * Makes an instance of the if command
   *
   * @param bundle   Will not be needed for this command
   * @param children The two children are the conditional, and block of commands to execute
   */
  public If(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    CONDITIONAL = loadClass(bundle, children.get(0)).execute();
    COMMAND_BLOCK = children.get(1);
  }

  /**
   * Executes the command if the conditional is nonzero
   *
   * @return The value of the final command executed, or 0 if the conditional is not executed
   */
  @Override
  public double execute() {
    double val = 0;
    if (CONDITIONAL != 0) {
      addTurtleLayer();
      val = executeNode(COMMAND_BLOCK);
      removeTurtleLayer();
    }
    return val;
  }
}
