package slogo.model.commands.basic_commands.command_types;


import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.TurtleInformation;

/**
 * Any command of this type has the potential to modify the current set of active turtles
 * that the rest of the commands operate on.
 *
 * @author Casey Szilagyi
 */
public abstract class MultipleTurtleCommand extends Command{

  TurtleInformation TURTLE_INFORMATION;

  public MultipleTurtleCommand(CommandInformationBundle informationBundle){
    TURTLE_INFORMATION = informationBundle.getTurtleInformation();
  }

  protected int numberOfActiveTurtles(){
    return TURTLE_INFORMATION.getAllTurtles().size();
  }

  protected void replaceActiveTurtleLayer(List<Integer> IDS){
    TURTLE_INFORMATION.setActiveTurtleLayer(IDS);
  }

  protected void duplicateActiveTurtleLayer(){
    TURTLE_INFORMATION.addActiveTurtleLayer();
  }

  protected void removeActiveTurtleLayer(){
    TURTLE_INFORMATION.removeActiveTurtleLayer();
  }

  protected void addActiveTurtleLayer(List<Integer> IDS){
    TURTLE_INFORMATION.addActiveTurtleLayer();
  }






}
