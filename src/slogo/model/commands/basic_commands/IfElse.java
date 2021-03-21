package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command decides which block of commands to execute
 *
 * @author Casey Szilagyi
 */
public class IfElse extends ControlStructureCommand {

  private final double CONDITIONAL;
  private final TreeNode IF_BLOCK;
  private final TreeNode ELSE_BLOCK;

  /**
   * Makes an instance of the IfElse command
   *
   * @param bundle   Not used for this command
   * @param children The 3 children are the conditional, if block, and else block to execute
   */
  public IfElse(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    CONDITIONAL = loadClass(bundle, children.get(0)).execute();
    IF_BLOCK = children.get(1);
    ELSE_BLOCK = children.get(2);
  }

  /**
   * Executes the if block if the conditional is nonzero, otherwise runs the else block
   *
   * @return The value of the final command executed, or 0 if no commands are executed
   */
  @Override
  public double execute() {
    double val = 0;
    addTurtleLayer();
    if (CONDITIONAL != 0) {
      val = executeNode(IF_BLOCK);
    } else {
      val = executeNode(ELSE_BLOCK);
    }
    removeTurtleLayer();
    return val;
  }
}
