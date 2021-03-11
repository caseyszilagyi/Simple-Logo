package slogo.model.execution;

import java.util.HashMap;
import java.util.Map;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.ModelController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.BasicCommand;
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
  //Need to figure out what the second data type is, some kind of tree?
  private final Map<String, TreeNode> COMMANDS = new HashMap<>();

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
  public Map<String, TreeNode> getCommandMap(){
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
