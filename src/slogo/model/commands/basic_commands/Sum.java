package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.Command;
import slogo.model.commands.basic_commands.command_types.MathAndLogicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * The sum command
 *
 * @author Casey Szilagyi
 */
public class Sum extends MathAndLogicCommand {

  private final double VALUE_1;
  private final double VALUE_2;

  /**
   * Makes an instance of the sum command
   *
   * @param bundle Not used for this command
   * @param children The TreeNodes to sum
   */
  public Sum(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VALUE_1 = loadClass(bundle, children.get(0)).execute();
    VALUE_2 = loadClass(bundle, children.get(1)).execute();
  }

  /**
   * Makes the turtle move the distance back that was specified in the constructor
   *
   * @return The sum of the two numbers
   */
  public double execute() {
    return VALUE_1 + VALUE_2;
  }
}
