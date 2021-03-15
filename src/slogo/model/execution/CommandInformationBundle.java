package slogo.model.execution;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.BasicCommand;
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
  private final Set<String> PARAMETERS_LIST = new HashSet<>();

  private final BackEndExternalAPI MODEL_CONTROLLER;

  /**
   * Makes our information bundle
   *
   * @param modelController
   */
  public CommandInformationBundle(BackEndExternalAPI modelController) {
    MODEL_CONTROLLER = modelController;
  }

  /**
   * Gets an unmodifiable copy of the map of commands
   *
   * @return The copy of the command map
   */
  public Map<String, UserDefinedCommand> getCommandMap() {
    return Collections.unmodifiableMap(COMMANDS);
  }

  /**
   * Gets a command with a specific name from the map
   * @param commandName The name of the command
   * @return The command
   * @throws ErrorHandler If the command doesn't exist, this error is thrown
   */
  public UserDefinedCommand getCommand(String commandName) throws ErrorHandler {
    if(COMMANDS.get(commandName) == null){
      throw new ErrorHandler("InvalidCommandName");
    }
    return COMMANDS.get(commandName);
  }

  /**
   * Adds a command to the map
   *
   * @param name  The command name
   * @param command The command
   */
  public void addCommand(String name, UserDefinedCommand command) {
    COMMANDS.put(name, command);
  }


  /**
   * Gets an unmodifiable copy of the variable map
   *
   * @return The variable map
   */
  public Map<String, Double> getVariableMap() {
    return Collections.unmodifiableMap(VARIABLES);
  }

  /**
   * Gets a variable with a specific name from the map
   * @param variableName The name of the variable
   * @return The variable value
   * @throws ErrorHandler If the variable doesn't exist, this error is thrown
   */
  public double getVariable(String variableName) throws ErrorHandler {
    if(VARIABLES.get(variableName) == null){
      throw new ErrorHandler("InvalidVariableName");
    }
    return VARIABLES.get(variableName);
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
   * Gets the map of parameters. Each successive map is one deeper in the nested structure
   *
   * @return The list of maps
   */
  public List<Map<String, Double>> getParameterMap() {
    List<Map<String, Double>> unmodifiableParameterCopy = PARAMETERS;
    for(Map paramMap: PARAMETERS){
      Map<String, Double> unmodifiableMap = Collections.unmodifiableMap(paramMap);
      unmodifiableParameterCopy.add(unmodifiableMap);
    }
    return Collections.unmodifiableList(unmodifiableParameterCopy);
  }

  /**
   * Gets a parameter with a specific name from the list holding the maps
   * @param parameterName The name of the parameter
   * @return The parameter value
   * @throws ErrorHandler If the parameter doesn't exist, this error is thrown
   */
  public Double getParameter(String parameterName) throws ErrorHandler {
    for(int i = PARAMETERS.size() - 1; i>=0; i--){
      if(PARAMETERS.get(i).get(parameterName) != null){
        return PARAMETERS.get(i).get(parameterName);
      }
    }
    return null;
  }

  /**
   * Adds a parameter to the last map in the parameter list, because the last map is the
   * most inner map
   *
   * @param name  The parameter name
   * @param value The value of the parameter
   */
  public void addParameter(String name, Double value) {
    PARAMETERS_LIST.add(name);
    PARAMETERS.get(PARAMETERS.size()-1).put(name, value);
  }


  /**
   * Gets the turtle in this bundle
   *
   * @return the turtle
   */
  public Turtle getTurtle() {
    return TURTLE;
  }

  public void updateTurtle() {
    MODEL_CONTROLLER.passInputToFrontEnd(TURTLE.getFrontEndParameters());
  }

}
