package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.Command;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command repeats the given BasicCommands a certain number of times
 *
 * @author Casey Szilagyi
 */
public class Repeat extends ControlStructureCommand {

  private final double LOOP_COUNT;
  private final TreeNode COMMAND_BLOCK;

  /**
   * Makes an instance of the Repeat loop
   *
   * @param bundle The pieces of information, such as variables and user defined commands, that may
   *               be needed to execute the the command
   * @param children Has 2 nodes, which is the loop count and the block of commands to execute
   */
  public Repeat(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    LOOP_COUNT = loadClass(bundle, children.get(0)).execute();
    COMMAND_BLOCK = children.get(1);
  }

  /**
   * Repeats the loop the specified number of times
   *
   * @return The value of the final command executed
   */
  public double execute() {
    double val = 0;
    for (int i = 0; i < LOOP_COUNT; i++) {
      val = executeBlock(COMMAND_BLOCK);
    }
    return val;
  }
}
