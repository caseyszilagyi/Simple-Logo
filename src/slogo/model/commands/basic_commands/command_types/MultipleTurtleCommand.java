package slogo.model.commands.basic_commands.command_types;


import java.util.ArrayList;
import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.Turtle;
import slogo.model.execution.TurtleInformation;
import slogo.model.tree.TreeNode;

/**
 * Any command of this type has the potential to modify the current set of active turtles that the
 * rest of the commands operate on.
 *
 * @author Casey Szilagyi
 */
public abstract class MultipleTurtleCommand extends Command {

  private final TurtleInformation TURTLE_INFORMATION;
  private final CommandInformationBundle INFORMATION_BUNDLE;

  /**
   * Makes the command and saves the turtle information
   *
   * @param informationBundle Has the turtle information to save
   */
  public MultipleTurtleCommand(CommandInformationBundle informationBundle) {
    TURTLE_INFORMATION = informationBundle.getTurtleInformation();
    INFORMATION_BUNDLE = informationBundle;
  }

  /**
   * Replaces the current active turtles with the new list of active turtles
   *
   * @param IDS The list of IDS that are now considered active
   */
  protected void replaceActiveTurtleLayer(List<Integer> IDS) {
    TURTLE_INFORMATION.setActiveTurtleLayer(IDS);
  }

  /**
   * Determines which turtles are active based on a conditional
   * @param conditional The TreeNode representing the conditional
   */
  protected void determineActiveTurtles(TreeNode conditional){
    List<Turtle> allTurtles = TURTLE_INFORMATION.getAllTurtles();
    List<Integer> nextLayer = new ArrayList<>();
    for(Turtle turtle: allTurtles){
      TURTLE_INFORMATION.setActiveTurtle(turtle.getID());
      if(executeNode(conditional) != 0){
        nextLayer.add(turtle.getID());
      }
    }
    TURTLE_INFORMATION.addActiveTurtleLayer();
    TURTLE_INFORMATION.setActiveTurtleLayer(nextLayer);
  }

  /**
   * Removes the layer of currently active turtles. Used when a loop is exited
   */
  protected void removeActiveTurtleLayer() {
    TURTLE_INFORMATION.removeActiveTurtleLayer();
  }

  /**
   * Adds a layer of active turtles. Used for ask/askWith
   *
   * @param IDS The list of IDS that are now considered active
   */
  protected void addActiveTurtleLayer(List<Integer> IDS) {
    TURTLE_INFORMATION.addActiveTurtleLayer();
    TURTLE_INFORMATION.setActiveTurtleLayer(IDS);
  }

  /**
   * Gets the number of turtles that currently exist
   *
   * @return The number of turtles
   */
  protected int getNumberOfTurtles(){
    return TURTLE_INFORMATION.getNumberOfTurtles();
  }

  /**
   * Executes the command in the node. Needed for condionals with multiple turtles
   *
   * @param node The node that holds all of the commands to execute
   * @return The double value that represents the executed node
   */
  protected double executeNode(TreeNode node) {
    return loadClass(INFORMATION_BUNDLE, node).execute();
  }

}
