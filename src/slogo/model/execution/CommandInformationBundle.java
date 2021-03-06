package slogo.model.execution;

import java.util.HashMap;
import java.util.Map;
import slogo.model.turtle.Turtle;

/**
 * This class stores all of the information that will be needed to execute a command. Will be
 * passed to the execute method of each basic command. Has the turtle, variable names, and
 * user defined commands
 */
public class CommandInformationBundle {

  private final Turtle TURTLE;
  private final Map<String, Integer> VARIABLES = new HashMap<>();
  //Need to figure out what the second data type is, some kind of tree?
  private final Map<String, String> COMMANDS = new HashMap<>();

  /**
   * Constructor can get passed the turtle that is being used
   * @param turtle The turtle
   */
  public CommandInformationBundle(Turtle turtle){
    TURTLE = turtle;
  }

  /**
   * Adds a variable to the map
   * @param name The variable name
   * @param variable The value of the variable
   */
  public void addVariable(String name, int variable){
    VARIABLES.put(name, variable);
  }

  /**
   * Gets a variable from the map
   * @param name The variable name
   * @return The value of the variable
   */
  public int getVariable(String name){
    return VARIABLES.get(name);
  }

}
