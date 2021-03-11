package slogo.model.turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.controller.ViewController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.*;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class is designed to test the implementations of each basic command separately in order to
 * guarantee that it is working before trying to concatenate them together or have them rely on each
 * other
 */
public class BasicCommandTester {

  private CommandInformationBundle commandBundle;
  private BasicCommandClassLoader loader;
  private BackEndExternalAPI modelController;
  private FrontEndExternalAPI viewController;
  static final double TOLERANCE = 0.05;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    modelController = new ModelController();
    commandBundle = new CommandInformationBundle(modelController);
    loader = new BasicCommandClassLoader();
  }

  // Turtle commands

  /**
   * Tests the forward command
   */
  @Test
  void testForward() {
    TreeNode child = makeNode("50");
    TreeNode root = makeTree("Forward", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(50, commandBundle.getTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the backward command
   */
  @Test
  void testBackward() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Backward", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(-60, commandBundle.getTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the right rotation command
   */
  @Test
  void testRight() {
    TreeNode child = makeNode("100");
    TreeNode root = makeTree("Right", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(350, commandBundle.getTurtle().getAngle(), TOLERANCE);
  }

  /**
   * Tests the left rotation command
   */
  @Test
  void testLeft() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Left", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(150, commandBundle.getTurtle().getAngle(), TOLERANCE);
  }

  /**
   * Tests the rotation and movement of the turtle to make sure the radians/degrees conversion is
   * correct
   */
  @Test
  void testRotateAndMove() {
    TreeNode child = makeNode("45");
    TreeNode root = makeTree("Right", child);
    executeCommand(makeBasicCommand(root));
    moveTurtle("10");
    assertEquals(7.07, commandBundle.getTurtle().getXPosition(), TOLERANCE);
    assertEquals(7.07, commandBundle.getTurtle().getXPosition(), TOLERANCE);
  }

  /**
   * Tests the forward movement with the sum command
   */
  @Test
  void testForwardSum() {
    TreeNode child = makeNode("5");
    TreeNode child2 = makeNode("10");
    TreeNode sum = makeTree("Sum", child, child2);
    TreeNode root = makeTree("Forward", sum);
    executeCommand(makeBasicCommand(root));
    assertEquals(15, commandBundle.getTurtle().getYPosition());
  }

  /**
   * Tests the SetPosition turtle command
   */
  @Test
  void testSetPosition() {
    TreeNode child = makeNode("5");
    TreeNode child2 = makeNode("5");
    TreeNode root = makeTree("SetPosition", child, child2);
    double distance = executeCommand(makeBasicCommand(root));
    assertEquals(7.07, distance, TOLERANCE);
    assertEquals(5, commandBundle.getTurtle().getXPosition());
    assertEquals(5, commandBundle.getTurtle().getYPosition());
  }

  /**
   * Tests the SetHeading turtle command
   */
  @Test
  void testSetHeading() {
    TreeNode child = makeNode("10");
    TreeNode root = makeTree("SetHeading", child);
    double degreeChange = executeCommand(makeBasicCommand(root));
    assertEquals(80, degreeChange);
    assertEquals(10, commandBundle.getTurtle().getAngle());
  }

  /**
   * Tests the SetTowards turtle command
   */
  @Test
  void testTowards() {
    commandBundle.getTurtle().setXPosition(-10);
    commandBundle.getTurtle().setYPosition(-10);
    TreeNode child1 = makeNode("0");
    TreeNode child2 = makeNode("0");
    TreeNode root = makeTree("SetTowards", child1, child2);
    double degreeChange = executeCommand(makeBasicCommand(root));
    assertEquals(45, degreeChange);
    assertEquals(45, commandBundle.getTurtle().getAngle());
  }

  /**
   * Tests PenUp and PenDown
   */
  @Test
  void testPenUpAndDown() {
    TreeNode up = makeNode("PenUp");
    assertEquals(0, executeCommand(makeBasicCommand(up)));
    assertEquals(0, commandBundle.getTurtle().getPenState());
    TreeNode down = makeNode("PenDown");
    assertEquals(1, executeCommand(makeBasicCommand(down)));
    assertEquals(1, commandBundle.getTurtle().getPenState());
  }

  /**
   * Tests ShowTurtle and HideTurtle
   */
  @Test
  void testShowAndHideTurtle() {
    TreeNode up = makeNode("HideTurtle");
    assertEquals(0, executeCommand(makeBasicCommand(up)));
    assertEquals(0, commandBundle.getTurtle().getVisibility());
    TreeNode down = makeNode("ShowTurtle");
    assertEquals(1, executeCommand(makeBasicCommand(down)));
    assertEquals(1, commandBundle.getTurtle().getVisibility());

  }

  /**
   * Tests Home command
   */
  @Test
  void testHome() {
    rotateTurtleClockwise("45");
    moveTurtle("10");
    TreeNode home = makeNode("Home");
    assertEquals(10, executeCommand(makeBasicCommand(home)));
    assertEquals(0, commandBundle.getTurtle().getXPosition());
    assertEquals(0, commandBundle.getTurtle().getYPosition());
  }

  /**
   * Tests ClearScreen command
   */
  @Test
  void testClearScreen() {
    rotateTurtleClockwise("45");
    moveTurtle("10");
    TreeNode home = makeNode("ClearScreen");
    assertEquals(10, executeCommand(makeBasicCommand(home)));
    assertEquals(0, commandBundle.getTurtle().getXPosition());
    assertEquals(0, commandBundle.getTurtle().getYPosition());
  }



  // Turtle Queries

  @Test
  void testXCor() {
    TreeNode root = makeTree("XCoordinate");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 0);
  }

  @Test
  void testYCor() {
    TreeNode root = makeTree("YCoordinate");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 0);
  }

  @Test
  void testHeading() {
    TreeNode root = makeTree("Heading");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 90);
  }

  // Math Operations

  /**
   * Tests the sum command
   */
  @Test
  void testSum() {
    TreeNode child = makeNode("60");
    TreeNode child2 = makeNode("10");
    TreeNode root = makeTree("Sum", child, child2);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 70, TOLERANCE);
  }

  /**
   * Tests the diff command
   */
  @Test
  void testDiff() {
    TreeNode child = makeNode("60");
    TreeNode child2 = makeNode("10");
    TreeNode root = makeTree("Difference", child, child2);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 50, TOLERANCE);
  }

  /**
   * Tests the product command
   */
  @Test
  void testProd() {
    TreeNode child = makeNode("60");
    TreeNode child2 = makeNode("10");
    TreeNode root = makeTree("Product", child, child2);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 600, TOLERANCE);
  }

  /**
   * Tests the sine command
   */
  @Test
  void testSin() {
    TreeNode child = makeNode("90");
    TreeNode root = makeTree("Sine", child);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 1, TOLERANCE);
  }

  // Boolean Operations

  /**
   * Tests the LessThan command
   */
  @Test
  void testLessThan() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("40");
    TreeNode root = makeTree("LessThan", child1, child2);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("LessThan", child2, child1);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  // Variables, Control Structures, and User Defined Commands

  /**
   * Tests the MakeVariable command
   */
  @Test
  void testMakeVariable() {
    TreeNode name = makeNode("Awesome");
    TreeNode value = makeNode("60");
    TreeNode root = makeTree("MakeVariable", name, value);
    assertEquals(60, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(60, commandBundle.getVariableMap().get("Awesome"), TOLERANCE);
  }

  // Helper methods below


  // Makes a Tree with the top node being the string, and all children being the list of nodes
  private TreeNode makeTree(String root, TreeNode... children) {
    return new TreeNode(root, root, Arrays.asList(children.clone()));
  }

  // Makes a single TreeNode
  private TreeNode makeNode(String val) {
    return new TreeNode(val);
  }


  // Makes a basic command out of the command name and
  private BasicCommand makeBasicCommand(TreeNode node) {
    return loader.makeCommand(commandBundle, node);
  }

  // Moves the turtle a specified distance, useful for testing queries
  private void moveTurtle(String distance) {
    TreeNode node = makeTree("Forward", makeNode(distance));
    BasicCommand forward = makeBasicCommand(node);
    forward.execute();
  }

  // Rotates the turtle a specified angle in the clockwise direction, useful for testing queries
  private void rotateTurtleClockwise(String angle) {
    TreeNode node = makeTree("Right", makeNode(angle));
    BasicCommand rotate = makeBasicCommand(node);
    rotate.execute();
  }

  private double executeCommand(BasicCommand command) {
    return command.execute();
  }


}
