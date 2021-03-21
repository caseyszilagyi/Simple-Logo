package slogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Main;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.DisplayInformation;
import slogo.model.execution.TurtleInformation;
import slogo.model.execution.UserDefinedInformation;
import slogo.model.turtle.DummyViewController;

/**
 * Tests designed to test more complicated commands with the parser and turtle movements,
 * including those with loops and multiple turtles
 */
public class ParserAndTurtleTests {

  static final double TOLERANCE = 0.05;
  private CommandExecutor EXECUTOR;
  private CommandInformationBundle BUNDLE;
  private TurtleInformation TURTLE_INFO;
  private DisplayInformation DISPLAY_INFO;
  private UserDefinedInformation USER_INFO;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    BackEndExternalAPI modelController = new ModelController();
    modelController.setViewController(new DummyViewController());
    BUNDLE = new CommandInformationBundle(modelController);
    EXECUTOR = new SLogoCommandExecutor(modelController, BUNDLE);
    TURTLE_INFO = BUNDLE.getTurtleInformation();
    DISPLAY_INFO = BUNDLE.getDisplayInformation();
    USER_INFO = BUNDLE.getUserDefinedInformation();
  }

  @Test
  void testSimpleAsk(){

  }

  /**
   * Testing nesting with tell statements
   */
  @Test
  void testTellMovingDifferentDirections(){
    executeCommand("tell [ 2 ] setxy 100 100 rt 90 tell [ 1 2 ] fd 50");
    assertEquals(0, getXCoordinate(1), TOLERANCE);
    assertEquals(50, getYCoordinate(1), TOLERANCE);
    assertEquals(150, getXCoordinate(2), TOLERANCE);
    assertEquals(100, getYCoordinate(2), TOLERANCE);
  }

  /**
   * Testing nesting with tell statements
   */
  @Test
  void testSimpleNestedTell(){
    executeCommand("tell [ 4 ] fd 40 tell [ 3 ] fd 50 dotimes [ :distance 4 ] [ tell [ :distance ]"
        + " fd :distance ] fd 50");
    assertEquals(103, getYCoordinate(3));
    assertEquals(1, getYCoordinate(1));
    assertEquals(2, getYCoordinate(2));
    assertEquals(44, getYCoordinate(4));
  }

  // Executes a command given a string
  private void executeCommand(String command){
   EXECUTOR.executeCommand(command, "English");
  }

  // Gets the x coordinate of the turtle with the given ID
  private double getXCoordinate(int ID){
    return TURTLE_INFO.getTurtle(ID).getXPosition();
  }

  // Gets the y coordinate of the turtle with the given ID
  private double getYCoordinate(int ID){
    return TURTLE_INFO.getTurtle(ID).getYPosition();
  }

  // Gets the angle of the turtle with the given ID
  private double getAngle(int ID){
    return TURTLE_INFO.getTurtle(ID).getAngle();
  }



}
