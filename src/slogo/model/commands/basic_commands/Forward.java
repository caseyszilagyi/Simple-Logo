package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The forward command
 *
 * @author Casey Szilagyi
 */
public class Forward extends TurtleAlteringCommand {

  private final double DISTANCE;

  /**
   * Makes an instance of the forward command
   *
   * @param bundle Contains the turtle that will neeed to be altered for this command
   * @param nodes  All of the children nodes needed for this command
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
  public double execute() {
    updateTurtle(turtle -> {
      changeTurtleX(DISTANCE * Math.cos(getAngle() / 360 * Math.PI * 2));
      changeTurtleY(DISTANCE * Math.sin(getAngle() / 360 * Math.PI * 2));
    });
    return DISTANCE;
  }
}
