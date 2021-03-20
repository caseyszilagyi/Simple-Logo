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
   * @param children The integer values representing the turtles that will follow future commands
   */
  public Tell(CommandInformationBundle informationBundle, List<TreeNode> children){
    super(informationBundle);
    for(TreeNode child: children){
      IDS.add((int) loadClass(informationBundle, child).execute());
    }
  }

  /**
   *
   * @return
   */
  @Override
  public double execute(){
    replaceActiveTurtleLayer(IDS);
    return IDS.get(IDS.size()-1);
  }
}
