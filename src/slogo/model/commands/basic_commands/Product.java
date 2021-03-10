package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Product command (calculates product of 2 children)
 *
 * @author jincho
 */
public class Product extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the sum command
   *
   * @param bundle Not used for this command
   * @param children The TreeNodes to sum
   */
  public Product(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * calc product
   *
   * @return The product of the two numbers
   */
  public double execute() {
    return VALUE_1 * VALUE_2;
  }

}
