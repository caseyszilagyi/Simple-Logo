package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Moves the turtle backwards by a certain amount
 *
 * @author Casey Szilagyi
 */
public class Backward extends TurtleAlteringCommand {

  private final double DISTANCE;

  /**
   * Makes an instance of the backward command
   *
   * @param bundle The information bundle that holds the turtle that this command operates on
   * @param nodes  Only has 1 child, which is the distance backward to move
   */
  public Backward(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    DISTANCE = super.loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Makes the turtle move the distance backward that is specified by the child node
   *
   * @return The distance backward that the turtle moved
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      changeTurtlePosition(-1 * DISTANCE * Math.cos(getAngle() / 360 * Math.PI * 2),
          -1 * DISTANCE * Math.sin(getAngle() / 360 * Math.PI * 2));
    });
    return DISTANCE;
  }

}
