package slogo.model.execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.ModelController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.commands.basic_commands.MakeUserInstruction;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * This class stores all of the information that will be needed to execute a command. Will be passed
 * to the execute method of each basic command. Has the turtle, variable names, and user defined
 * commands
 */
public class CommandInformationBundle {

  private final Turtle TURTLE = new Turtle();

  private final Map<String, Double> VARIABLES = new HashMap<>();
  private final Map<String, UserDefinedCommand> COMMANDS = new HashMap<>();
  private final List<Map<String, Double>> PARAMETERS = new ArrayList<>();


  private final BasicCommandClassLoader CLASS_LOADER = new BasicCommandClassLoader();
  private final BackEndExternalAPI MODEL_CONTROLLER;

  /**
   * Makes our information bundle
   * @param modelController
   */
  public CommandInformationBundle(BackEndExternalAPI modelController) {
    MODEL_CONTROLLER = modelController;
  }

  /**
   * Adds a variable to the map
   *
   * @param name  The variable name
   * @param value The value of the variable
   */
  public void addVariable(String name, Double value) {
    VARIABLES.put(name, value);
  }

  /**
   * Gets the map of commands
   * @return The command map
   */
  public Map<String, UserDefinedCommand> getCommandMap(){
    return COMMANDS;
  }

  /**
   * Gets the map of variables
   * @return The variable map
   */
  public Map<String, Double> getVariableMap(){
    return VARIABLES;
  }

  /**
   * Gets the map of parameters. Each successive map is one deeper in the nested structure
   * @return The list of maps
   */
  public List<Map<String, Double>> getParameterMap(){
    return PARAMETERS;
  }

  /**
   * Gets the turtle in this bundle
   *
   * @return the turtle
   */
  public Turtle getTurtle() {
    return TURTLE;
  }

  public void updateTurtle() { MODEL_CONTROLLER.passInputToFrontEnd(TURTLE.getFrontEndParameters());}

  /**
   * Makes a Basic command using the given node
   *
   * @param node The node
   * @return The BasicCommand
   */
  public BasicCommand loadClass(TreeNode node) {
    return CLASS_LOADER.makeCommand(this, node);
  }

}
