package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Sets the pen size
 *
 * @author Casey Szilagyi
 */
public class SetPenSize extends DisplayAlteringCommand {

  private final double SIZE;

  /**
   * Makes an instance of the set pen size command
   *
   * @param bundle Contains the model controller that the command is sent through
   * @param nodes  1 child, which is the value of the pen size
   */
  public SetPenSize(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    SIZE = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Sets the pen size
   *
   * @return The pen size
   */
  @Override
  public double execute() {
    setPenSize(SIZE);
    return SIZE;
  }
}
