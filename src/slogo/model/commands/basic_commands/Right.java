package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Rotates the turtle right
 *
 * @author Casey Szilagyi
 */
public class Right extends TurtleAlteringCommand {

  private final TreeNode ANGLE;

  /**
   * Makes an instance of the right command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  The only child is the number of degrees to rotate the turtle
   */
  public Right(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    ANGLE = nodes.get(0);
  }

  /**
   * Makes the turtle rotate right by the specified number of degrees
   *
   * @return The angle that it rotated
   */
  @Override
  public double execute() {
    return updateTurtle(turtle -> {
      return rotateCounterClockwise(-1 * executeNode(ANGLE));
    });
  }
}
