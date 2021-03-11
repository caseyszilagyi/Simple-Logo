package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The minus command
 *
 * @author Casey Szilagyi
 */
public class Minus extends MathAndLogicCommand {

  private final double VALUE;

  /**
   * Makes an instance of the minus command
   *
   * @param bundle Not used for this command
   * @param children The TreeNode to return the minus of
   */
  public Minus(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Returns the negative of the value
   *
   * @return The negative of the value
   */
  public double execute() {
    return VALUE * -1;
  }
}
