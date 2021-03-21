package slogo.model.execution;

import java.util.ArrayList;
import java.util.List;
import slogo.controller.BackEndExternalAPI;

/**
 * This class holds and keeps track of the current set of active turtles. It has a variety of
 * methods in order to deal with nested loops and more complex scenarios
 *
 * @author Casey Szilagyi
 */
public class TurtleInformation {

  private final BackEndExternalAPI MODEL_CONTROLLER;

  private final List<Turtle> ALL_TURTLES = new ArrayList<>();
  // used to keep track of nested tell/ask statements
  private final List<List<Integer>> CURRENT_ACTIVE_TURTLES = new ArrayList<>();
  private Integer activeTurtleID;

  public TurtleInformation(BackEndExternalAPI modelController) {
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

  /**
   * Gets the active turtle in this bundle
   *
   * @return the turtle
   */
  public Turtle getActiveTurtle() {
    return ALL_TURTLES.get(activeTurtleID - 1);
  }

  /**
   * Gets the turtle with a given ID
   * @param ID The ID
   * @return The turtle
   */
  public Turtle getTurtle(int ID){
    return ALL_TURTLES.get(ID-1);
  }

  /**
   * Sets the turtle that the commands will act on
   *
   * @param ID The ID of the turtle
   */
  public void setActiveTurtle(int ID) {
    activeTurtleID = ID;
    MODEL_CONTROLLER.setActiveTurtle(ID);
  }

  /**
   * Gets the number of turtles that currently exist
   *
   * @return The number of turtles
   */
  public int getNumberOfTurtles() {
    return ALL_TURTLES.size();
  }

  /**
   * Gets the list of IDs that correspond to the current active turtles
   *
   * @return The list of turtle IDs
   */
  public List<Integer> getCurrentActiveTurtleList() {
    return CURRENT_ACTIVE_TURTLES.get(CURRENT_ACTIVE_TURTLES.size() - 1);
  }

  // Makes new turtles up to the given ID. Automatically called when the user
  // tries to add turtles that don't exist yet
  private void makeNewTurtles(int ID) {
    for (int i = ALL_TURTLES.size() + 1; i <= ID; i++) {
      ALL_TURTLES.add(new Turtle(i, MODEL_CONTROLLER));
      ALL_TURTLES.get(i - 1).tellFrontEnd();
      activeTurtleID = i;
    }
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

  /**
   * Replaces the active turtles with the set of active turtle IDs passed in
   *
   * @param nextLayer The set of active turtle IDS
   */
  public void setActiveTurtleLayer(List<Integer> nextLayer) {
    checkForNewHighestID(nextLayer);
    removeActiveTurtleLayer();
    CURRENT_ACTIVE_TURTLES.add(nextLayer);
    setActiveTurtle(nextLayer.get(0));
    MODEL_CONTROLLER.setActiveTurtles(nextLayer);
  }

  // Checks for a higher ID, because new turtles will need to be made
  private void checkForNewHighestID(List<Integer> nextLayer) {
    for (int ID : nextLayer) {
      if (ID > ALL_TURTLES.size()) {
        makeNewTurtles(ID);
      }
    }
  }

  /**
   * Removes the current set of active turtles
   */
  public void removeActiveTurtleLayer() {
    CURRENT_ACTIVE_TURTLES.remove(CURRENT_ACTIVE_TURTLES.size() - 1);
    MODEL_CONTROLLER.setActiveTurtles(getCurrentActiveTurtleList());
  }
}
