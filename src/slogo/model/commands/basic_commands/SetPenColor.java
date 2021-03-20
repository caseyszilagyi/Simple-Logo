package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayAlteringCommand;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The set pen color command
 *
 * @author Casey Szilagyi
 */
public class SetPenColor extends DisplayAlteringCommand {

  private final double INDEX;

  /**
   * Makes an instance of the set pen color command
   *
   * @param bundle Contains the model controller that the command is sent through
   * @param nodes  1 child, which is the index corresponding to the color
   */
  public SetPenColor(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    INDEX = loadClass(bundle, nodes.get(0)).execute();
  }

  /**
   * Sets the pen color color
   *
   * @return The index of the chosen pen color
   */
  public double execute() {
    setPenColor((int) INDEX);
    return INDEX;
  }
}
