package slogo.view;

import java.util.List;

/**
 *
 */
public interface FrontEndInternalAPI {


  void setActiveTurtles(List<Integer> iDs);

  void setActiveTurtle(int turtleID);

  void updateCommandQueue(String commandType, List<Double> commandValues);


  void clearScreen();

  void updateTurtlePositions();

}