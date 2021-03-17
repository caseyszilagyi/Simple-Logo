package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The set turtle shape
 *
 * @author Casey Szilagyi
 */
public class SetTurtleShape extends DisplayAlteringCommand {

  private final double INDEX;

  /**
   * Makes an instance of the set shape command
   *
   * @param bundle Contains the model controller that the command is sent through
   * @param nodes  1 child, which is the index corresponding to the color
   */
  public SetTurtleShape(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    INDEX = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Sets the turtle shape
   *
   * @return The index of the chosen shape
   */
  public double execute() {
    setTurtleShape((int) INDEX);
    return INDEX;
  }
}
