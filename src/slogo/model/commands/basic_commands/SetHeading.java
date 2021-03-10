package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * rotates turtle so that it's heading the specified angle
 *
 * @author jincho
 */
public class SetHeading extends TurtleAlteringCommand {

  private final double ANGLE;
  private final double PREV_ANGLE;

  /**
   * Makes an instance of the right command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes All of the children nodes needed for this command
   */
  public SetHeading(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    PREV_ANGLE = bundle.getTurtle().getAngle();
    ANGLE = loadClass(bundle, nodes.get(0)).execute();
  }

  private double degreesMoved() {
    return Math.abs(PREV_ANGLE - ANGLE);
  }

  /**
   * sets turtle to fave new angle
   *
   * @return abs val of degrees rotated
   */
  @Override
  public double execute() {
    setAngle(ANGLE);
    return degreesMoved();
  }
}
