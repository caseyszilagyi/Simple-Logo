package slogo.model.execution;

import java.util.HashMap;
import java.util.Map;
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
  private final Map<String, String> COMMANDS = new HashMap<>();

  /**
   * Makes our information bundle
   */
  public CommandInformationBundle() {
  }

  /**
   * Adds a variable to the map
   *
   * @param name     The variable name
   * @param variable The value of the variable
   */
  public void addVariable(String name, double variable) {
    VARIABLES.put(name, variable);
  }

  /**
   * Gets a variable from the map
   *
   * @param name The variable name
   * @return The value of the variable
   */
  public double getVariable(String name) {
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

}
