package slogo.model.execution;

import java.util.HashMap;
import java.util.Map;
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
  private final Map<String, BasicCommand> VARIABLES = new HashMap<>();
  //Need to figure out what the second data type is, some kind of tree?
  private final Map<String, BasicCommand> COMMANDS = new HashMap<>();

  private final BasicCommandClassLoader CLASS_LOADER = new BasicCommandClassLoader();

  /**
   * Makes our information bundle
   */
  public CommandInformationBundle() {
  }

  /**
   * Adds a variable to the map
   *
   * @param name     The variable name
   * @param value The value of the variable in BasicCommand form
   */
  public void addVariable(String name, BasicCommand value) {
    VARIABLES.put(name, value);
  }

  /**
   * Gets a variable from the map
   *
   * @param name The variable name
   * @return The value of the variable in BasicCommand form
   */
  public BasicCommand getVariable(String name) {
    return VARIABLES.get(name);
  }

  /**
   * Gets a command from the map
   *
   * @param name The command name
   * @return The root node of the command in BasicCommand form
   */
  public BasicCommand getCommand(String name) {
    return VARIABLES.get(name);
  }

  /**
   * Gets the turtle in this bundle
   *
   * @return the turtle
   */
  public Turtle getTurtle() {
    return TURTLE;
  }

  /**
   * Makes a Basic command using the given node
   * @param node The node
   * @return The BasicCommand
   */
  public BasicCommand loadClass(TreeNode node){
    return CLASS_LOADER.makeCommand(this, node, node.getChildren());
  }

}
