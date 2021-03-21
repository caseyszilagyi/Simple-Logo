package slogo.model.commands.basic_commands;

import java.util.ArrayList;
import java.util.List;
import slogo.model.commands.basic_commands.command_types.MultipleTurtleCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This is the ask command. It defines a set of turtles and passes commands to act on them
 *
 * @author Casey Szilagyi
 */
public class Ask extends MultipleTurtleCommand {

  private final List<Integer> IDS = new ArrayList<>();
  private final TreeNode COMMANDS;

  /**
   * Gets the commands and turtles to act on
   * @param informationBundle The bundle that contains all turtles
   * @param children 2 children, one of which has a command block with turtle IDS and
   *                 the other which has the commands to run
   */
  public Ask(CommandInformationBundle informationBundle, List<TreeNode> children){
    super(informationBundle);
    TreeNode IDBlock = children.get(0);
    for(TreeNode child: IDBlock.getChildren()){
      IDS.add((int) loadClass(informationBundle, child).execute());
    }
    COMMANDS = children.get(1);
  }

  /**
   * Sets the active turtles to the ones that were passed
   * @return The ID of the last turtle in the list
   */
  @Override
  public double execute(){
    addActiveTurtleLayer(IDS);

    return IDS.get(IDS.size()-1);
  }
}
