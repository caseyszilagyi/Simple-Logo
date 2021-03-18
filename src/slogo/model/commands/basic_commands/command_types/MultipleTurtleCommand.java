package slogo.model.commands.basic_commands.command_types;


import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Any command of this type has the potential to modify the current set of active turtles
 * that the rest of the commands operate on.
 *
 * @author Casey Szilagyi
 */
public abstract class MultipleTurtleCommand extends Command{

  CommandInformationBundle INFORMATION_BUNDLE;

  public MultipleTurtleCommand(CommandInformationBundle informationBundle){
    INFORMATION_BUNDLE = informationBundle;
  }

  public double addActiveTurtleLayer(List<Double> IDS){
    INFORMATION_BUNDLE.addActiveTurtleLayer();
    for(Double ID: IDS){
    }
    return  0;
  }




}
