package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculate the natural log of a number
 *
 * @author Casey Szilagyi
 */
public class NaturalLog extends MathAndLogicCommand {

  private final double VALUE;

  /**
   * Makes an instance of the NaturalLog command
   *
   * @param bundle   Not used for this command
   * @param children The TreeNode to take the natural log of
   */
  public NaturalLog(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Calculate the natural log of the number
   *
   * @return The natural log of the number
   */
  public double execute() {
    return Math.log(VALUE);
  }
}
