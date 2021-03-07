package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command makes and stores a variable for later use
 *
 * @author Casey Szilagyi
 */
public class MakeVariable implements BasicCommand {

  private final String NAME;
  private final TreeNode VALUE;

  /**
   * Makes an instance of the MakeVariable command
   *
   * @param nodes The nodes for making the variable. Includes the string name, and the other
   *              node that will eventually be a double value
   */
  public MakeVariable(List<TreeNode> nodes) {
    NAME = nodes.get(0).getValue();
    VALUE = nodes.get(1);
  }

  /**
   * Adds the variable to the bundle that holds the variables, and returns the value of the
   * variable
   *
   * @param informationBundle The information bundle needed to store the variable in
   * @return The variable value
   */
  public double execute(CommandInformationBundle informationBundle) {
    BasicCommand variableValue = informationBundle.loadClass(VALUE);
    informationBundle.addVariable(NAME, variableValue);
    return variableValue.execute(informationBundle);
  }
}
