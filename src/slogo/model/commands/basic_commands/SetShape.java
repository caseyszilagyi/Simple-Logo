package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Sets the shape of the turtle
 *
 * @author Casey Szilagyi
 */
public class SetShape extends DisplayAlteringCommand {

  private final double INDEX;

  /**
   * Makes an instance of the set shape command
   *
   * @param bundle Contains the model controller that the command is sent through
   * @param nodes  1 child, which is the index corresponding to the shape
   */
  public SetShape(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    INDEX = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Sets the turtle shape
   *
   * @return The index of the chosen shape
   */
  @Override
  public double execute() {
    setTurtleShape((int) INDEX);
    return INDEX;
  }
}
