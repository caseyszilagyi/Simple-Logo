package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the inverse tangent of an angle
 *
 * @author Casey Szilagyi
 */
public class ArcTangent extends MathAndLogicCommand {

  private final double RATIO;

  /**
   * Makes an instance of the ArcTangent command
   *
   * @param bundle   Not used for this command
   * @param children The TreeNode to take the arctangent of
   */
  public ArcTangent(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    RATIO = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Calculates arctangent of angle in degrees
   *
   * @return The arctangent of the ratio
   */
  @Override
  public double execute() {
    return Math.toDegrees(Math.atan(RATIO));
  }
}
