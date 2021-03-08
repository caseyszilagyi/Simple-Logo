package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Command to get the XCoordinate of the turtle
 *
 * @author Casey Szilagyi
 */
public class XCoordinate extends TurtleQueryCommand {

  /**
   * Makes an instance of the XCoordinate Command
   *
   * @param bundle Has the turtle that will need to be accessed
   * @param children The basic commands. This will be null since it is a query
   */
  public XCoordinate(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
  }

  /**
   * Gets the turtle's XCoordinate
   *
   * @return The XCoordinate
   */
  public double execute() {
    return getXCoordinate();
  }
}
