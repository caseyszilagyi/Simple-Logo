package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the sine of an angle
 *
 * @author jincho
 */
public class Sine extends MathAndLogicCommand {

  private final double ANGLE;

  /**
   * Makes an instance of the sine command
   *
   * @param bundle   Not used for this command
   * @param children The TreeNode to take the sine of
   */
  public Sine(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    ANGLE = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Calculates the sine of the angle in degrees
   *
   * @return The sine of the angle
   */
  @Override
  public double execute() {
    return Math.sin(Math.toRadians(ANGLE));
  }
}
