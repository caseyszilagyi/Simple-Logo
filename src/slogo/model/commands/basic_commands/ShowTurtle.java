package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Makes the turtle visible
 *
 * @author Casey Szilagyi
 */
public class ShowTurtle extends TurtleAlteringCommand {

  /**
   * Makes an instance of the ShowTurtle command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public ShowTurtle(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Makes the turtle visible
   *
   * @return 1, because the turtle is visible
   */
  @Override
  public double execute() {
    return updateTurtle(turtle -> {
      return changeTurtleVisibility(1);
    });
  }
}
