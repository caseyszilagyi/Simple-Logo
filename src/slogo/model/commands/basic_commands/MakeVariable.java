package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Makes and stores a variable for later use
 *
 * @author Casey Szilagyi
 */
public class MakeVariable extends ControlStructureCommand {

  private final String NAME;
  private final double VALUE;

  /**
   * Makes an instance of the MakeVariable command
   *
   * @param bundle Contains the map of variable names to values
   * @param nodes  The nodes for making the variable. Includes the string name, and the other node
   *               that will eventually be a double value
   */
  public MakeVariable(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    NAME = nodes.get(0).getValue();
    VALUE = loadClass(bundle, nodes.get(1)).execute();
  }

  /**
   * Adds the variable to the bundle that holds the variables, and returns the value of the
   * variable
   *
   * @return The variable value
   */
  @Override
  public double execute() {
    setVariable(NAME, VALUE);
    return VALUE;
  }
}
