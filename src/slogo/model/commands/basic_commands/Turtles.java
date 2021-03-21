package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MultipleTurtleCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Gets the number of currently active turtles
 *
 * @author Casey Szilagyi
 */
public class Turtles extends MultipleTurtleCommand {

  /**
   * Makes an instance of the Turtles Command
   *
   * @param bundle   Has the turtles that will need to be counted
   * @param children This will be null since it is a query
   */
  public Turtles(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
  }

  /**
   * Gets the number of active turtles
   *
   * @return The number of turtles
   */
  @Override
  public double execute() {
    return getNumberOfTurtles();
  }
}

