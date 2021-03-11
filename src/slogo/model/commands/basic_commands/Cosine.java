package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the Cosine of an angle
 *
 * @author Casey Szilagyi
 */
public class Cosine extends MathAndLogicCommand {

  private final double ANGLE;

  /**
   * Makes an instance of the cosine
   *
   * @param bundle   Not used for this command
   * @param children The TreeNode to take the cosine of
   * @param children The TreeNode to take the cosine of
   */
  public Cosine(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    ANGLE = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * calculate cosine of angle in degrees
   *
   * @return The cosine of angle
   */
  public double execute() {
    return Math.cos(Math.toRadians(ANGLE));
  }
}
