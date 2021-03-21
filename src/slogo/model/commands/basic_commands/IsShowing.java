package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Checks whether the turtle is currently showing
 *
 * @author Casey Szilagyi
 */
public class IsShowing extends TurtleQueryCommand {

  /**
   * Makes an instance of the IsShowing command
   *
   * @param bundle   Has the turtle that will need to be accessed
   * @param children This will be null because it is a query, and therefore has no arguments
   */
  public IsShowing(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
  }

  /**
   * Gets the turtle's pen state
   *
   * @return 1 if down, 0 if up
   */
  @Override
  public double execute() {
    return getVisibility();
  }
}
