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
  // used to keep track of which turtle is currently being used in nested tell/ask statements
  private List<Integer> ACTIVE_TURTLE_INDEXES = new ArrayList<>();
  private Integer activeTurtleID;

  public TurtleInformation(BackEndExternalAPI modelController){
    MODEL_CONTROLLER = modelController;
    addFirstTurtleLayer();
  }


  private void addFirstTurtleLayer() {
    List<Integer> firstLayer = new ArrayList<Integer>();
    CURRENT_ACTIVE_TURTLES.add(firstLayer);
    ACTIVE_TURTLE_INDEXES.add(0);
    addActiveTurtle(1);
    activeTurtleID = 1;
  }

  // Gets the index of the active turtle of the most deeply nested list of active turtle IDs
  private int getActiveIndex() {
    return ACTIVE_TURTLE_INDEXES.get(ACTIVE_TURTLE_INDEXES.size() - 1);
  }

  // Increments the active turtle index for the last layer of turtles
  private void incrementActiveIndex() {
    ACTIVE_TURTLE_INDEXES.set(ACTIVE_TURTLE_INDEXES.size() - 1,
        ACTIVE_TURTLE_INDEXES.get(ACTIVE_TURTLE_INDEXES.size() - 1) + 1);
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
    for (int i = ALL_TURTLES.size() + 1; i <= ID; i++) {
      ALL_TURTLES.add(new Turtle(i));
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
    ACTIVE_TURTLE_INDEXES.add(0);
  }

  public void setActiveTurtleLayer(List<Integer> nextLayer) {
    removeActiveTurtleLayer();
    CURRENT_ACTIVE_TURTLES.add(nextLayer);
    ACTIVE_TURTLE_INDEXES.add(0);
  }

  /**
   * Removes an inner nested active turtle layer.
   */
  public void removeActiveTurtleLayer() {
    CURRENT_ACTIVE_TURTLES.remove(CURRENT_ACTIVE_TURTLES.size() - 1);
    ACTIVE_TURTLE_INDEXES.remove(ACTIVE_TURTLE_INDEXES.size() - 1);
  }
}
