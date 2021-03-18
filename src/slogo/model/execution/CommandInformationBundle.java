package slogo.model.execution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * This class stores all of the information that will be needed to execute a command. Will be passed
 * to the execute method of each basic command. Has the turtle, variable names, and user defined
 * commands
 */
public class CommandInformationBundle {

  private final Map<String, Double> VARIABLES = new HashMap<>();
  private final Map<String, UserDefinedCommand> COMMANDS = new HashMap<>();
  private final List<Map<String, Double>> PARAMETERS = new ArrayList<>();
  private final Set<String> PARAMETERS_LIST = new HashSet<>();

  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final DisplayInformation DISPLAY_INFORMATION = new DisplayInformation();

  private final List<Turtle> ALL_TURTLES = new ArrayList<>();
  // used to keep track of nested tell/ask statements
  private final List<List<Integer>> CURRENT_ACTIVE_TURTLES = new ArrayList<>();
  // used to keep track of which turtle is currently being used in nested tell/ask statements
  private List<Integer> ACTIVE_TURTLE_INDEXES = new ArrayList<>();


  /**
   * Makes our information bundle, and initializes the first turtle
   *
   * @param modelController The model controller that the bundle communicates with to pass
   *                        information to the front end
   */
  public CommandInformationBundle(BackEndExternalAPI modelController) {
    MODEL_CONTROLLER = modelController;
    addFirstTurtleLayer();
  }

  /**
   * Gets the model controller. Used for the commands to pass information to the front end
   *
   * @return The model controller
   */
  public BackEndExternalAPI getModelController() {
    return MODEL_CONTROLLER;
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
   *
   * @param commandName The name of the command
   * @return The command
   * @throws ErrorHandler If the command doesn't exist, this error is thrown
   */
  public UserDefinedCommand getCommand(String commandName) throws ErrorHandler {
    if (COMMANDS.get(commandName) == null) {
      throw new ErrorHandler("InvalidCommandName");
    }
    return COMMANDS.get(commandName);
  }

  /**
   * Adds a command to the map
   *
   * @param name    The command name
   * @param command The command
   */
  public void addCommand(String name, UserDefinedCommand command) {
    COMMANDS.put(name, command);
  }

  /**
   * Checks if a command is in the map
   *
   * @param name The command name
   */
  public boolean hasCommand(String name) {
    return COMMANDS.containsKey(name);
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
   *
   * @param variableName The name of the variable
   * @return The variable value
   * @throws ErrorHandler If the variable doesn't exist, this error is thrown
   */
  public double getVariable(String variableName) throws ErrorHandler {
    if (VARIABLES.get(variableName) == null) {
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
   * Checks if a variable is in the map
   *
   * @param name The variable name
   */
  public boolean hasVariable(String name) {
    return VARIABLES.containsKey(name);
  }


  /**
   * Gets the map of parameters. Each successive map is one deeper in the nested structure
   *
   * @return The list of maps
   */
  public List<Map<String, Double>> getParameterMap() {
    List<Map<String, Double>> unmodifiableParameterCopy = PARAMETERS;
    for (Map paramMap : PARAMETERS) {
      Map<String, Double> unmodifiableMap = Collections.unmodifiableMap(paramMap);
      unmodifiableParameterCopy.add(unmodifiableMap);
    }
    return Collections.unmodifiableList(unmodifiableParameterCopy);
  }

  /**
   * Adds a parameter map to the list of parameter maps. This will be removed when a loop/command is
   * done and the parameters are no longer needed
   */
  public void addParameterMap() {
    PARAMETERS.add(new HashMap<String, Double>());
  }

  /**
   * Removes a parameter map from the list of parameter maps. This is done when a loop/command is
   * done and the parameters are no longer needed
   */
  public void removeParameterMap() {
    PARAMETERS.remove(PARAMETERS.size() - 1);
  }

  /**
   * Gets a parameter with a specific name from the list holding the maps
   *
   * @param parameterName The name of the parameter
   * @return The parameter value
   * @throws ErrorHandler If the parameter doesn't exist, this error is thrown
   */
  public Double getParameter(String parameterName) {
    for (int i = PARAMETERS.size() - 1; i >= 0; i--) {
      if (PARAMETERS.get(i).get(parameterName) != null) {
        return PARAMETERS.get(i).get(parameterName);
      }
    }
    return null;
  }

  /**
   * Adds a parameter to the last map in the parameter list, because the last map is the most inner
   * map
   *
   * @param name  The parameter name
   * @param value The value of the parameter
   */
  public void addParameter(String name, Double value) {
    PARAMETERS_LIST.add(name);
    PARAMETERS.get(PARAMETERS.size() - 1).put(name, value);
  }

  /**
   * Checks if a parameter is in the map
   *
   * @param name The parameter name
   */
  public boolean hasParameter(String name) {
    return PARAMETERS_LIST.contains(name);
  }


  private void addFirstTurtleLayer(){
    List<Integer> firstLayer = new ArrayList<Integer>();
    CURRENT_ACTIVE_TURTLES.add(firstLayer);
    ACTIVE_TURTLE_INDEXES.add(0);
    addActiveTurtle(1);
  }

  /**
   * Gets the active turtle in this bundle
   *
   * @return the turtle
   */
  public Turtle getActiveTurtle() {
    int activeIndex = getActiveIndex();
    List<Integer> activeList= getCurrentActiveTurtleList();
    return ALL_TURTLES.get(activeList.get(activeIndex)-1);
  }

  /**
   * Increments the counter that references the active turtle
   *
   * @return true if done successfully, false if we were on the last turtle
   */
  public boolean incrementActiveTurtle() {
    int activeIndex = getActiveIndex();
    List<Integer> activeList= getCurrentActiveTurtleList();
    if(activeList.size()>activeIndex){
      activeIndex++;
      return true;
    }
    return false;
  }

  // Gets the index of the active turtle of the most deeply nested list of active turtle IDs
  private int getActiveIndex(){
    return ACTIVE_TURTLE_INDEXES.get(ACTIVE_TURTLE_INDEXES.size()-1);
  }

  // Gets the list of IDs corresponding to the most deeply nested list of active turtles
  private List<Integer> getCurrentActiveTurtleList(){
    return CURRENT_ACTIVE_TURTLES.get(CURRENT_ACTIVE_TURTLES.size()-1);
  }

  /**
   * Updates the front end with the current active turtle's information
   */
  public void updateTurtle() {
    MODEL_CONTROLLER.passInputToFrontEnd(getActiveTurtle().getFrontEndParameters());
  }


  public void addActiveTurtle(int ID) {
    if (ID > ALL_TURTLES.size()) {
      makeNewTurtles(ID);
    }
    getCurrentActiveTurtleList().add(ID);
  }


  // Makes new turtles up to the given ID. Automatically called when the user
  // tries to add turtles that don't exist yet
  private void makeNewTurtles(int ID) {
    for (int i = ALL_TURTLES.size()+1; i <= ID; i++) {
      ALL_TURTLES.add(new Turtle(i));
    }
  }

  /**
   * Gets a list of all the turtles that exist
   *
   * @return The list of all the turtles
   * <p>
   * <p>
   * MAYBE DO THIS WITH AN ITERATOR? Might not need this command, I think that this
   * class should be doint management with the turtles? commands don't need this
   */
  public List<Turtle> getAllTurtles() {
    return ALL_TURTLES;
  }

  /**
   * Adds a new list and copies the previous list for a nested loop. Also makes the current
   * active turtle index for this new list equal to 0, so that the commands in the loop
   * execute for each turtle.
   */
  public void addActiveTurtleLayer() {
    List<Integer> nextLayer = new ArrayList<>();
    nextLayer.addAll(CURRENT_ACTIVE_TURTLES.get(CURRENT_ACTIVE_TURTLES.size()-1));
    CURRENT_ACTIVE_TURTLES.add(nextLayer);
    ACTIVE_TURTLE_INDEXES.add(0);
  }

  /**
   * Removes an inner nested active turtle layer.
   */
  public void removeActiveTurtleLayer() {
    CURRENT_ACTIVE_TURTLES.remove(CURRENT_ACTIVE_TURTLES.size() - 1);
  }

  /**
   * Gets the display information
   *
   * @return The display information
   */
  public DisplayInformation getDisplayInformation() {
    return DISPLAY_INFORMATION;
  }


}
