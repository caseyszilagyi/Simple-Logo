package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Command to get YCoordinate of turtle
 *
 * @author jincho
 */
public class YCoordinate extends TurtleQueryCommand {

  /**
   * Makes the BasicCommand and saves the Turtle
   *
   * @param informationBundle The bundle of information that contains the turtle
   * @param children
   */
  public YCoordinate(CommandInformationBundle informationBundle, List<TreeNode> children) {
    super(informationBundle);
  }

  @Override
  public double execute() {
    return getYCoordinate();
  }
}
