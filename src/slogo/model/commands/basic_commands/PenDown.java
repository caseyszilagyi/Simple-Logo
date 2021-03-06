package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Puts the pen down
 *
 * @author Casey Szilagyi
 */
public class PenDown extends TurtleAlteringCommand {

  /**
   * Makes an instance of the pen down command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public PenDown(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Puts the pen down
   *
   * @return 1, because the pen is drawing
   */
  @Override
  public double execute() {
    return updateTurtle(turtle -> {
      return changePenState(1);
    });
  }
}
