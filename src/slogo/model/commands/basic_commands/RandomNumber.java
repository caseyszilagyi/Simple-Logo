package slogo.model.commands.basic_commands;

import java.util.List;
import java.util.Random;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The random command. This command returns a non-negative number strictly less than max
 *
 * @author Casey Szilagyi
 */
public class RandomNumber extends MathAndLogicCommand {

  private final double MAX;
  private final Random RANDOM = new Random();

  /**
   * Makes an instance of the sum command
   *
   * @param bundle Not used for this command
   * @param children The TreeNodes to sum
   */
  public RandomNumber(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    MAX = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Sums the two numbers
   *
   * @return The sum of the two numbers
   */
  public double execute() {
    return RANDOM.nextDouble()*MAX;
  }
}
