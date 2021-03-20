package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Sets the position of the turtle to a specific X and Y position
 *
 * @author jincho
 */
public class SetPosition extends TurtleAlteringCommand {

  private final double NEW_X;
  private final double NEW_Y;
  private final double PREV_X;
  private final double PREV_Y;

  /**
   * Makes instance of SetPosition command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  The two children contain the X/Y values that the turlte will move to
   */
  public SetPosition(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    NEW_X = loadClass(bundle, nodes.get(0)).execute();
    NEW_Y = loadClass(bundle, nodes.get(1)).execute();
    PREV_X = getXCoordinate();
    PREV_Y = getYCoordinate();
  }

  private double distanceMoved() {
    return Math.sqrt(
        (Math.abs(NEW_X - PREV_X) * Math.abs(NEW_X - PREV_X)) + (Math.abs(NEW_Y - PREV_Y) * Math
            .abs(NEW_Y - PREV_Y)));
  }

  /**
   * Make the turtle change its location to the new specified location
   *
   * @return distance it moved from previous to new location
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      setTurtlePosition(NEW_X, NEW_Y);
    });
    return distanceMoved();
  }
}
