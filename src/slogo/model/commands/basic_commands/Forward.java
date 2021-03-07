package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * The forward command
 *
 * @author Casey Szilagyi
 */
public class Forward implements BasicCommand {

  private final List<TreeNode> CHILDREN;

  /**
   * Makes an instance of the forward command
   *
   * @param nodes All of the children nodes
   */
  public Forward(List<TreeNode> nodes) {
    CHILDREN = nodes;
  }

  /**
   * Makes the turtle move the distance forward that was specified in the constructor
   *
   * @param informationBundle The bundle of all information that is needed
   * @return The distance forward that it moved
   */
  public double execute(CommandInformationBundle informationBundle) {
    double val = informationBundle.loadClass(CHILDREN.get(0)).execute(informationBundle);
    informationBundle.getTurtle().changeXPosition(Math.cos(informationBundle.getTurtle().getAngle()) * val);
    informationBundle.getTurtle().changeYPosition(Math.sin(informationBundle.getTurtle().getAngle()) * val);
    return val;
  }
}
