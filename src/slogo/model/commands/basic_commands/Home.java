package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The home command
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
  public double execute() {
    double distance = Math.sqrt(Math.pow(getXCoordinate(), 2) + Math.pow(getYCoordinate(), 2));
    setTurtleX(0);
    setTurtleY(0);
    setAngle(90);
    updateFrontEnd();
    return distance;
  }
}
