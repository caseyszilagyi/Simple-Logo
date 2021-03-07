package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * The sum command
 *
 * @author Casey Szilagyi
 */
public class Sum implements BasicCommand {

  private final List<TreeNode> VALUES_TO_SUM;

  /**
   * Makes an instance of the sum command
   *
   * @param nodes The TreeNodes to sum
   */
  public Sum(List<TreeNode> nodes) {
    VALUES_TO_SUM = nodes;
  }

  /**
   * Makes the turtle move the distance back that was specified in the constructor
   *
   * @param informationBundle The bundle that has the turtle and variables
   * @return The sum of the two numbers
   */
  public double execute(CommandInformationBundle informationBundle) {
    double result = 0;
    for (int i = 0; i < VALUES_TO_SUM.size(); i++) {
      result += informationBundle.loadClass(VALUES_TO_SUM.get(i)).execute(informationBundle);
    }
    return result;
  }
}
