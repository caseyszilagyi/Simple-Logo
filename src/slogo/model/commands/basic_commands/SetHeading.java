package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Rotates turtle so that it's heading the specified angle
 *
 * @author jincho
 */
public class SetHeading extends TurtleAlteringCommand {

  private final double ANGLE;
  private final double PREV_ANGLE;

  /**
   * Makes an instance of the set heading command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  The only child is the angle that the turtle will be facing
   */
  public SetHeading(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    PREV_ANGLE = getAngle();
    ANGLE = loadClass(bundle, nodes.get(0)).execute();
  }

  // calculates the number of degrees the turtle rotated
  private double degreesMoved() {
    return Math.abs(PREV_ANGLE - ANGLE);
  }

  /**
   * Sets the turtle to face a new angle
   *
   * @return The absolute value of the degrees rotated
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      setAngle(ANGLE);
    });
    return degreesMoved();
  }
}
