package slogo.model.commands.basic_commands;

import java.util.ArrayList;
import java.util.List;
import slogo.model.commands.basic_commands.command_types.MultipleTurtleCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This is the tell command. It defines the set of turtles that the commands act on
 */
public class Tell extends MultipleTurtleCommand {

  private final List<Integer> IDS = new ArrayList<>();

  /**
   * Gets all of the turtles that will follow future actions
   * @param informationBundle The bundle that contains all turtles
   * @param children Holds the command block that has all of the turtle IDs
   */
  public Tell(CommandInformationBundle informationBundle, List<TreeNode> children){
    super(informationBundle);
    TreeNode IDBlock = children.get(0);
    for(TreeNode child: IDBlock.getChildren()){
      IDS.add((int) loadClass(informationBundle, child).execute());
    }
  }

  /**
   * Sets the active turtles to the ones that were passed
   * @return The ID of the last turtle in the list
   */
  @Override
  public double execute(){
    replaceActiveTurtleLayer(IDS);
    return IDS.get(IDS.size()-1);
  }
}
