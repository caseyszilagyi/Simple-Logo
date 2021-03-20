package slogo.model.commands.basic_commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Repeats the given commands a certain number of times
 *
 * @author Casey Szilagyi
 */
public class Repeat extends ControlStructureCommand {

  private final double LOOP_COUNT;
  private final TreeNode COMMAND_BLOCK;

  /**
   * Makes an instance of the Repeat loop
   *
   * @param bundle   Not used for this command
   * @param children Has 2 nodes, which are the loop count and the block of commands to execute
   */
  public Repeat(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    LOOP_COUNT = loadClass(bundle, children.get(0)).execute();
    COMMAND_BLOCK = children.get(1);
  }

  /**
   * Repeats the loop the specified number of times. The variable :repcount is stored during the
   * loop as the # loop that it is currently on
   *
   * @return The value of the final command executed
   */
  @Override
  public double execute() {
    double val = 0;
    addParameterMap();
    addTurtleLayer();
    for (double i = 1; i <= LOOP_COUNT; i += 1) {
      setParameter(":repcount", i);
      val = executeBlock(COMMAND_BLOCK);
    }
    removeParameterMap();
    removeTurtleLayer();
    return val;
  }
}
