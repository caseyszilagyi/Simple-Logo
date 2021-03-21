package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Gets the index corresponding with the turtle's shape
 *
 * @author Casey Szilagyi
 */
public class GetShape extends DisplayQueryCommand {


  /**
   * Makes an instance of the get turtle shape command
   *
   * @param bundle Has the display information
   * @param nodes  Command has no children because it is a query command
   */
  public GetShape(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Gets the index of the turtle shape
   *
   * @return The index of the turtle shape
   */
  @Override
  public double execute() {
    return getTurtleShape();
  }
}
