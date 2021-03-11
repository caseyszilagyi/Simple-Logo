package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Set Positions command
 *
 * @author jincho
 */
public class SetPosition extends TurtleAlteringCommand {

  private final double NEW_X;
  private final double NEW_Y;
  private final double PREV_X;
  private final double PREV_Y;

  /**
   * makes instance of SetPosition command
   *
   * @param bundle Contains the turtle that will neeed to be altered for this command
   * @param nodes All of the children nodes needed for this command
   */
  public SetPosition(CommandInformationBundle bundle ,List<TreeNode> nodes){
    super(bundle);
    NEW_X = loadClass(bundle, nodes.get(0)).execute();
    NEW_Y = loadClass(bundle, nodes.get(1)).execute();
    PREV_X= bundle.getTurtle().getXPosition();
    PREV_Y = bundle.getTurtle().getYPosition();
  }

  private double distanceMoved(){
    return Math.sqrt((Math.abs(NEW_X - PREV_X) * Math.abs(NEW_X - PREV_X)) + (Math.abs(NEW_Y - PREV_Y) * Math.abs(NEW_Y - PREV_Y)));
  }

  /**
   * Make the turtle change its location to the new specified location
   *
   * @return distance it moved from prev to new location
   */
  @Override
  public double execute() {
    setTurtleX(NEW_X);
    setTurtleY(NEW_Y);
    updateFrontEnd();
    return distanceMoved();
  }
}
