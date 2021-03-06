package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Calculates the product of two numbers
 *
 * @author jincho
 */
public class Product extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the product command
   *
   * @param bundle   Not used for this command
   * @param children The two TreeNodes to take the product of
   */
  public Product(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Calculates the product of two numbers
   *
   * @return The product of the two numbers
   */
  @Override
  public double execute() {
    return VALUE_1 * VALUE_2;
  }

}
