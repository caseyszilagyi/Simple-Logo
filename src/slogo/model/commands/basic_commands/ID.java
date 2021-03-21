package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Gets the ID of the turtle
 *
 * @author Casey Szilagyi
 */
public class ID extends TurtleQueryCommand {

  /**
   * Makes an instance of the ID Command
   *
   * @param bundle   Has the turtle that will need to be accessed
   * @param children This will be null since it is a query
   */
  public ID(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
  }

  /**
   * Gets the turtle's ID
   *
   * @return The ID
   */
  @Override
  public double execute() {
    return getID();
  }
}
