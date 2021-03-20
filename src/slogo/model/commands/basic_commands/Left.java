package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The rotate left command
 *
 * @author Casey Szilagyi
 */
public class Left extends TurtleAlteringCommand {

  private final double ANGLE;

  /**
   * Makes an instance of the left command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  Has 1 child, which is the amount the turtle will be rotated
   */
  public Left(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    ANGLE = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Makes the turtle rotate left by the specified number of degrees
   *
   * @return The angle that it rotated
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      changeTurtleAngle(ANGLE);
    });
    return ANGLE;
  }
}
