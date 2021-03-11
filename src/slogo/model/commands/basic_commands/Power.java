package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculate the power of a number
 *
 * @author Casey Szilagyi
 */
public class Power extends MathAndLogicCommand {

  private final double BASE;
  private final double EXPONENT;

  /**
   * Makes an instance of the power command
   *
   * @param bundle   Not used for this command
   * @param children The TreeNodes that have the value of the base/exponent
   */
  public Power(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    BASE = loadClass(bundle, children.get(0)).execute();
    EXPONENT = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Calculate the base raised to the exponent
   *
   * @return The result of the base raised to the exponent
   */
  public double execute() {
    return Math.pow(BASE, EXPONENT);
  }
}
