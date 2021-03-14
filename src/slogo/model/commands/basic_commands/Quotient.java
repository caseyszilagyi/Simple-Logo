package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The Quotient command
 *
 * @author Casey Szilagyi
 */
public class Quotient extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the quotient command
   *
   * @param bundle   Not used for this command
   * @param children The TreeNodes to take the quotient of
   */
  public Quotient(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Gives the quotient of the two numbers
   *
   * @return The quotient of the two numbers
   */
  public double execute() {
    return Math.floor(VALUE_1 / VALUE_2);
  }
}
