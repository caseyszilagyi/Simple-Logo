package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the Tangent of an angle
 *
 * @author Casey Szilagyi
 */
public class Tangent extends MathAndLogicCommand {

  private final double ANGLE;

  /**
   * Makes an instance of the tangent command
   *
   * @param bundle   Not used for this command
   * @param children The TreeNode to take the tangent of
   */
  public Tangent(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    ANGLE = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Calculates the tangent of the angle in degrees
   *
   * @return The tangent of the angle
   */
  @Override
  public double execute() {
    return Math.tan(Math.toRadians(ANGLE));
  }
}
