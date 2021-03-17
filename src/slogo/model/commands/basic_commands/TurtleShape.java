package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The get turtle shape command
 *
 * @author Casey Szilagyi
 */
public class TurtleShape extends DisplayQueryCommand {


  /**
   * Makes an instance of the get turtle shape command
   *
   * @param bundle Has the display information
   * @param nodes  Command has no children because it is a query command
   */
  public TurtleShape(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Gets the index of the turtle shape
   *
   * @return The index of the turtle shape
   */
  public double execute() {
    return getTurtleShape();
  }
}
