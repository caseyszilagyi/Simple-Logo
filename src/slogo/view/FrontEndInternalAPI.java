package slogo.view;

import java.util.List;

/**
 * @author Ji Yun Hyo
 */
public interface FrontEndInternalAPI {


  /**
   * This allows the frontend to keep a list of all frontend turtles that are set to active by the
   * backend. This allows the frontend to display different turtle images for active/inactive turtles
   * @param iDs all IDs of the active turtles
   */
  void setActiveTurtles(List<Integer> iDs);

  /**
   * Sets the ID of the turtle to be updated/displayed
   * @param turtleID ID of the turtle to be updated/displayed for animation
   */
  void setActiveTurtle(int turtleID);

  /**
   * Receives all information from the backend and updates the Deque in order so that
   * TurtleDisplayPane knows exactly what type of command (e.g. what method to call) in order to
   * update turtle states
   * @param commandType type of command to be executed
   * @param commandValues the specific commands to be executed
   */
  void updateCommandQueue(String commandType, List<Double> commandValues);

  /**
   * clears the screen
   */
  void clearScreen();

  /**
   * updates the states of the turtle
   */
  void updateTurtleStates();

}