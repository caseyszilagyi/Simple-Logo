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
   * Makes an instance of the Repeat loop
   *
   * @param bundle The pieces of information, such as variables and user defined commands, that may
   *               be needed to execute the the command
   * @param children The children are the conditional, and block of commands to execute
   */
  public If(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    CONDITIONAL = loadClass(bundle, children.get(0)).execute();
    COMMAND_BLOCK = children.get(1);
  }

  /**
   * Repeats the loop the specified number of times
   *
   * @return The value of the final command executed
   */
  public double execute() {
    double val = 0;
    if(CONDITIONAL != 0){
      val = executeBlock(COMMAND_BLOCK);
    }
    return val;
  }
}
