package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Moves the turtle to 0,0
 *
 * @author Casey Szilagyi
 */
public class Home extends TurtleAlteringCommand {

  /**
   * Makes an instance of the Home command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public Home(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Moves the turtle to 0, 0
   *
   * @return The distance that the turtle had to move to get there
   */
  @Override
  public double execute() {
    return updateTurtle(turtle -> {
      setAngle(90);
      return setTurtlePosition(0,0);
    });
  }
}
