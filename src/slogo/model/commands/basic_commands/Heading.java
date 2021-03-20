package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleQueryCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Command to get angle the turtle is facing/heading
 *
 * @author jincho
 */
public class Heading extends TurtleQueryCommand {

  /**
   * Makes the BasicCommand and saves the Turtle
   *
   * @param informationBundle The bundle of information that contains the turtle
   * @param children This command has no children, so it will be null
   */
  public Heading(CommandInformationBundle informationBundle, List<TreeNode> children) {
    super(informationBundle);
  }

  /**
   * Sets the heading that the turtle is facing
   * @return The number of degrees moved
   */
  @Override
  public double execute() {
    return getAngle();
  }
}
