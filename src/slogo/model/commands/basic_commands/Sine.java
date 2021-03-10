package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * calculating sine of angle
 *
 * @author jincho
 */
public class Sine extends MathAndLogicCommand {

  private final double VALUE;

  /**
   * Makes an instance of the sum command
   *
   * @param bundle Not used for this command
   * @param children The TreeNodes to sine
   */
  public Sine(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * calculate sine of angle in degrees
   *
   * @return The sine of angle
   */
  public double execute() {
    return Math.sin(Math.toRadians(VALUE));
  }
}
