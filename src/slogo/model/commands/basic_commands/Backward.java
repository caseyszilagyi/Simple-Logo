package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * The backward command
 *
 * @author Casey Szilagyi
 */
public class Backward implements BasicCommand {

  private final List<TreeNode> CHILDREN;

  /**
   * Makes an instance of the backward command
   *
   * @param nodes All of the children nodes needed for this command
   */
  public Backward(List<TreeNode> nodes) {
    CHILDREN = nodes;
  }

  /**
   * Makes the turtle move the distance backward that is specified by the child node
   *
   * @param informationBundle The bundle of all information that is needed
   * @return The distance backward that it moved
   */
  public double execute(CommandInformationBundle informationBundle) {
    double val = informationBundle.loadClass(CHILDREN.get(0)).execute(informationBundle) * -1;
    informationBundle.getTurtle()
        .changeXPosition(Math.cos(informationBundle.getTurtle().getAngle()) * val);
    informationBundle.getTurtle()
        .changeYPosition(Math.sin(informationBundle.getTurtle().getAngle()) * val);
    return val;
  }
}
