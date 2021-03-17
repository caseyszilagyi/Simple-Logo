package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The get pen color command
 *
 * @author Casey Szilagyi
 */
public class PenColor extends DisplayQueryCommand {


  /**
   * Makes an instance of the get pen color command
   *
   * @param bundle Has the display information
   * @param nodes  Command has no children because it is a query command
   */
  public PenColor(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Gets the index of the pen color
   *
   * @return The index of the pen color
   */
  public double execute() {
    return getPenColor();
  }
}
