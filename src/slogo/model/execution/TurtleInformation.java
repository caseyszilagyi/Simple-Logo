package slogo.model.execution;

import java.util.ArrayList;
import java.util.List;
import slogo.controller.BackEndExternalAPI;

/**
 * This class holds and keeps track of the current set of active turtles. It has a variety
 * of methods in order to deal with nested loops and more complex scenarios
 *
 * @author Casey Szilagyi
 */
public class TurtleInformation {

  private final BackEndExternalAPI MODEL_CONTROLLER;

  private final List<Turtle> ALL_TURTLES = new ArrayList<>();
  // used to keep track of nested tell/ask statements
  private final List<List<Integer>> CURRENT_ACTIVE_TURTLES = new ArrayList<>();
  private Integer activeTurtleID;

  public TurtleInformation(BackEndExternalAPI modelController){
    MODEL_CONTROLLER = modelController;
    addFirstTurtleLayer();
  }


  private void addFirstTurtleLayer() {
    List<Integer> firstLayer = new ArrayList<Integer>();
    firstLayer.add(1);
    CURRENT_ACTIVE_TURTLES.add(firstLayer);
    ALL_TURTLES.add(new Turtle(1, MODEL_CONTROLLER));
    activeTurtleID = 1;
  }


  // Gets the list of IDs corresponding to the most deeply nested list of active turtles
  public List<Integer> getCurrentActiveTurtleList() {
    return CURRENT_ACTIVE_TURTLES.get(CURRENT_ACTIVE_TURTLES.size() - 1);
  }

  /**
   * Gets the active turtle in this bundle
   *
   * @return the turtle
   */
  public Turtle getActiveTurtle() {
    return ALL_TURTLES.get(activeTurtleID-1);
  }

  public void setActiveTurtle(int ID){
    if(ID>ALL_TURTLES.size()){
      makeNewTurtles(ID);
    }
    activeTurtleID = ID;
    MODEL_CONTROLLER.setActiveTurtle(ID);
  }

  /**
   * Updates the front end with the current active turtle's information
   */
  public void updateFrontEnd() {
    MODEL_CONTROLLER.passInputToFrontEnd(getActiveTurtle().getFrontEndParameters());
  }


  // Makes new turtles up to the given ID. Automatically called when the user
  // tries to add turtles that don't exist yet
  private void makeNewTurtles(int ID) {
    for (int i = ALL_TURTLES.size() + 1; i <= ID; i++) {
      ALL_TURTLES.add(new Turtle(i, MODEL_CONTROLLER));
      activeTurtleID = i;
    }
  }


  /**
   * Gets a list of all the turtles that exist
   *
   * @return The list of all the turtles
   * <p>
   * <p>
   * MAYBE DO THIS WITH AN ITERATOR? Might not need this command, I think that this class should be
   * doint management with the turtles? commands don't need this
   */
  public List<Turtle> getAllTurtles() {
    return ALL_TURTLES;
  }

  /**
   * Adds a new list and copies the previous list for a nested loop. Also makes the current active
   * turtle index for this new list equal to 0, so that the commands in the loop execute for each
   * turtle.
   */
  public void addActiveTurtleLayer() {
    List<Integer> nextLayer = new ArrayList<>();
    nextLayer.addAll(CURRENT_ACTIVE_TURTLES.get(CURRENT_ACTIVE_TURTLES.size() - 1));
    CURRENT_ACTIVE_TURTLES.add(nextLayer);
  }

  public void setActiveTurtleLayer(List<Integer> nextLayer) {
    removeActiveTurtleLayer();
    CURRENT_ACTIVE_TURTLES.add(nextLayer);
  }

  /**
   * Removes an inner nested active turtle layer.
   */
  public void removeActiveTurtleLayer() {
    CURRENT_ACTIVE_TURTLES.remove(CURRENT_ACTIVE_TURTLES.size() - 1);
  }
}
