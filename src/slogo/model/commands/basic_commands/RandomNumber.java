package slogo.model.commands.basic_commands;

import java.util.List;
import java.util.Random;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Returns a non-negative number strictly less than max
 *
 * @author Casey Szilagyi
 */
public class RandomNumber extends MathAndLogicCommand {

  private final double MAX;
  private final Random RANDOM = new Random();

  /**
   * Makes an instance of the random command
   *
   * @param bundle   Not used for this command
   * @param children The one child is the max value that the random number can go up to
   */
  public RandomNumber(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    MAX = loadClass(bundle, children.get(0)).execute();
  }

  /**
   * Generates a (pseudo) random number up to the max
   *
   * @return The random number
   */
  @Override
  public double execute() {
    return RANDOM.nextDouble() * MAX;
  }
}
