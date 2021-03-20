package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Moves the turtle forward by a certain distance
 *
 * @author Casey Szilagyi
 */
public class Forward extends TurtleAlteringCommand {

  private final double DISTANCE;

  /**
   * Makes an instance of the forward command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  Has 1 child, which is the distance forward to be moved
   */
  public Forward(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    DISTANCE = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Makes the turtle move the distance forward that is specified by the child node
   *
   * @return The distance forward that it moved
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      changeTurtlePosition(DISTANCE * Math.cos(getAngle() / 360 * Math.PI * 2),
          DISTANCE * Math.sin(getAngle() / 360 * Math.PI * 2));
    });
    return DISTANCE;
  }
}
