package slogo.model.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Main;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.controller.ViewController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.DisplayInformation;
import slogo.model.execution.TurtleInformation;
import slogo.model.execution.UserDefinedInformation;
import slogo.model.tree.TreeNode;


/**
 * This class is designed to test the implementations of each basic command separately in order to
 * guarantee that it is working before trying to concatenate them together or have them rely on each
 * other
 */
public class BasicCommandTest {

  static final double TOLERANCE = 0.05;
  private CommandInformationBundle commandBundle;
  private TurtleInformation turtleInformation;
  private UserDefinedInformation userInformation;
  private DisplayInformation displayInformation;

  private BasicCommandClassLoader loader;
  private BackEndExternalAPI modelController;
  private FrontEndExternalAPI viewController = new DummyViewController();
  private Main main = new Main();

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    modelController = new ModelController();
    modelController.setViewController(viewController);
    viewController.setModelController(modelController);
    commandBundle = new CommandInformationBundle(modelController);
    turtleInformation = commandBundle.getTurtleInformation();
    userInformation = commandBundle.getUserDefinedInformation();
    displayInformation = commandBundle.getDisplayInformation();
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
    assertEquals(50, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the backward command
   */
  @Test
  void testBackward() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Backward", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(-60, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the right rotation command
   */
  @Test
  void testRight() {
    TreeNode child = makeNode("100");
    TreeNode root = makeTree("Right", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(350, turtleInformation.getActiveTurtle().getAngle(), TOLERANCE);
  }

  /**
   * Tests the left rotation command
   */
  @Test
  void testLeft() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Left", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(150, turtleInformation.getActiveTurtle().getAngle(), TOLERANCE);
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
    assertEquals(7.07, turtleInformation.getActiveTurtle().getXPosition(), TOLERANCE);
    assertEquals(7.07, turtleInformation.getActiveTurtle().getXPosition(), TOLERANCE);
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
    assertEquals(15, turtleInformation.getActiveTurtle().getYPosition());
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
    assertEquals(5, turtleInformation.getActiveTurtle().getXPosition());
    assertEquals(5, turtleInformation.getActiveTurtle().getYPosition());
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
    assertEquals(10, turtleInformation.getActiveTurtle().getAngle());
  }

  /**
   * Tests the SetTowards turtle command
   */
  @Test
  void testTowards() {
    turtleInformation.getActiveTurtle().setPosition(-10, -10);
    TreeNode child1 = makeNode("0");
    TreeNode child2 = makeNode("0");
    TreeNode root = makeTree("SetTowards", child1, child2);
    double degreeChange = executeCommand(makeBasicCommand(root));
    assertEquals(45, degreeChange);
    assertEquals(45, turtleInformation.getActiveTurtle().getAngle());
    turtleInformation.getActiveTurtle().setPosition(6, 7);
    executeCommand(makeBasicCommand(root));
    assertEquals(229.398, turtleInformation.getActiveTurtle().getAngle(), TOLERANCE);
  }

  /**
   * Tests PenUp and PenDown
   */
  @Test
  void testPenUpAndDown() {
    TreeNode up = makeNode("PenUp");
    assertEquals(0, executeCommand(makeBasicCommand(up)));
    assertEquals(0, turtleInformation.getActiveTurtle().getPenState());
    TreeNode down = makeNode("PenDown");
    assertEquals(1, executeCommand(makeBasicCommand(down)));
    assertEquals(1, turtleInformation.getActiveTurtle().getPenState());
  }

  /**
   * Tests ShowTurtle and HideTurtle
   */
  @Test
  void testShowAndHideTurtle() {
    TreeNode up = makeNode("HideTurtle");
    assertEquals(0, executeCommand(makeBasicCommand(up)));
    assertEquals(0, turtleInformation.getActiveTurtle().getVisibility());
    TreeNode down = makeNode("ShowTurtle");
    assertEquals(1, executeCommand(makeBasicCommand(down)));
    assertEquals(1, turtleInformation.getActiveTurtle().getVisibility());

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
    assertEquals(0, turtleInformation.getActiveTurtle().getXPosition());
    assertEquals(0, turtleInformation.getActiveTurtle().getYPosition());
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
    assertEquals(0, turtleInformation.getActiveTurtle().getXPosition());
    assertEquals(0, turtleInformation.getActiveTurtle().getYPosition());
  }

  // Turtle Queries

  /**
   * Tests the XCoordinate Command
   */
  @Test
  void testXCor() {
    TreeNode root = makeTree("XCoordinate");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 0);
  }

  /**
   * Tests the YCoordinate Command
   */
  @Test
  void testYCor() {
    TreeNode root = makeTree("YCoordinate");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 0);
  }

  /**
   * Tests the Heading Command
   */
  @Test
  void testHeading() {
    TreeNode root = makeTree("Heading");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 90);
  }

  /**
   * Tests the IsPenDown Command
   */
  @Test
  void testIsPenDown() {
    TreeNode root = makeTree("PenUp");
    executeCommand(makeBasicCommand(root));
    root = makeTree("IsPenDown");
    assertEquals(executeCommand(makeBasicCommand(root)), 0);
    root = makeTree("PenDown");
    executeCommand(makeBasicCommand(root));
    root = makeTree("IsPenDown");
    assertEquals(executeCommand(makeBasicCommand(root)), 1);
  }

  /**
   * Tests the IsShowing Command
   */
  @Test
  void testIsShowing() {
    TreeNode root = makeTree("HideTurtle");
    executeCommand(makeBasicCommand(root));
    root = makeTree("IsShowing");
    assertEquals(executeCommand(makeBasicCommand(root)), 0);
    root = makeTree("ShowTurtle");
    executeCommand(makeBasicCommand(root));
    root = makeTree("IsShowing");
    assertEquals(executeCommand(makeBasicCommand(root)), 1);
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
   * Tests the Quotient command
   */
  @Test
  void testQuotient() {
    TreeNode child = makeNode("65");
    TreeNode child2 = makeNode("10");
    TreeNode root = makeTree("Quotient", child, child2);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 6.5, TOLERANCE);
  }

  /**
   * Tests the Remainder command
   */
  @Test
  void testRemainder() {
    TreeNode child = makeNode("65");
    TreeNode child2 = makeNode("10");
    TreeNode root = makeTree("Remainder", child, child2);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, 5, TOLERANCE);
  }

  /**
   * Tests the Minus command
   */
  @Test
  void testMinus() {
    TreeNode child = makeNode("65");
    TreeNode root = makeTree("Minus", child);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(val, -65, TOLERANCE);
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

  /**
   * Tests the cosine command
   */
  @Test
  void testCosine() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Cosine", child);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(0.5, val, TOLERANCE);
  }

  /**
   * Tests the tangent command
   */
  @Test
  void testTangent() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Tangent", child);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(Math.sqrt(3), val, TOLERANCE);
  }

  /**
   * Tests the tangent command
   */
  @Test
  void testArcTangent() {
    TreeNode child = makeNode("1.73");
    TreeNode root = makeTree("ArcTangent", child);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(60, val, TOLERANCE);
  }

  /**
   * Tests the natural log command
   */
  @Test
  void testNaturalLog() {
    TreeNode child = makeNode("10");
    TreeNode root = makeTree("NaturalLog", child);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(2.30, val, TOLERANCE);
  }

  /**
   * Tests the power command
   */
  @Test
  void testPower() {
    TreeNode base = makeNode("3");
    TreeNode exponent = makeNode("5");
    TreeNode root = makeTree("Power", base, exponent);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(243, val, TOLERANCE);
  }

  /**
   * Tests the random command
   */
  @Test
  void testRandom() {
    TreeNode max = makeNode("5");
    TreeNode root = makeTree("RandomNumber", max);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(2.5, val, 2.5);
  }


  /**
   * Tests the pi command
   */
  @Test
  void testPi() {
    TreeNode root = makeTree("Pi");
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(3.14, val, TOLERANCE);
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

  /**
   * Tests the LessThan command
   */
  @Test
  void testGreaterThan() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("40");
    TreeNode root = makeTree("GreaterThan", child1, child2);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("GreaterThan", child2, child1);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  /**
   * Tests the Equal command
   */
  @Test
  void testEqual() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("40");
    TreeNode root = makeTree("Equal", child1, child2);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("Equal", child1, child1);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  /**
   * Tests the not equal command
   */
  @Test
  void testNotEqual() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("40");
    TreeNode root = makeTree("NotEqual", child1, child2);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("NotEqual", child1, child1);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  /**
   * Tests the and command
   */
  @Test
  void testAnd() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("0");
    TreeNode root = makeTree("And", child1, child2);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("And", child2, child2);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("And", child1, child1);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  /**
   * Tests the and command
   */
  @Test
  void testOr() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("0");
    TreeNode root = makeTree("Or", child1, child2);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("Or", child2, child2);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
    root = makeTree("Or", child1, child1);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  /**
   * Tests the and command
   */
  @Test
  void testNot() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Not", child);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
    child = makeNode("0");
    root = makeTree("Not", child);
    assertEquals(1, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  // Variables, Control Structures, and User Defined Commands

  /**
   * Tests the MakeVariable command
   */
  @Test
  void testMakeVariable() {
    TreeNode name = makeNode(":Awesome");
    TreeNode value = makeNode("60");
    TreeNode root = makeTree("MakeVariable", name, value);
    assertEquals(60, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(60, userInformation.getVariableMap().get(":Awesome"), TOLERANCE);
    moveTurtle(":Awesome");
    assertEquals(60, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the creation and execution of a user defined command
   */
  @Test
  void testMakeUserInstruction() {
    TreeNode name = makeNode("Movement");
    TreeNode paramBlock = makeTree("CommandBlock", makeNode(":distance"));
    TreeNode forward = makeTree("Forward", makeNode(":distance"));
    TreeNode commandBlock = makeTree("CommandBlock", forward);

    TreeNode userCommand = makeTree("MakeUserInstruction", name, paramBlock, commandBlock);
    assertEquals(1, executeCommand(makeBasicCommand(userCommand)), TOLERANCE);

    TreeNode useCommand = makeTree("Movement", makeNode("50"));
    assertEquals(50, makeBasicCommand(useCommand).execute(), TOLERANCE);

    useCommand = makeTree("Movement", makeNode("100"));
    assertEquals(100, makeBasicCommand(useCommand).execute(), TOLERANCE);
    assertEquals(150, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }


  /**
   * Tests the Repeat command
   */
  @Test
  void testRepeat() {
    TreeNode times = makeNode("5");
    TreeNode distance = makeNode("60");
    TreeNode forward = makeTree("Forward", distance);
    TreeNode root = makeTree("Repeat", times, forward);
    assertEquals(60, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(300, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the Repeat command with the :repcount variable
   */
  @Test
  void testRepeatWithRepCount() {
    TreeNode times = makeNode("5");
    TreeNode distance = makeNode(":repcount");
    TreeNode forward = makeTree("Forward", distance);
    TreeNode root = makeTree("Repeat", times, forward);
    assertEquals(5, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(15, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the DoTimes command
   */
  @Test
  void testDoTimes() {
    TreeNode times = makeNode("5");
    TreeNode variable = makeNode(":size");
    TreeNode control = makeTree("CommandBlock", variable, times);

    TreeNode commandsInLoop = makeTree("Forward", variable);
    TreeNode overallCommand = makeTree("DoTimes", control, commandsInLoop);

    assertEquals(5, executeCommand(makeBasicCommand(overallCommand)), TOLERANCE);
    assertEquals(15, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);

  }

  /**
   * Tests the For command
   */
  @Test
  void testFor() {
    TreeNode variable = makeNode(":distance");
    TreeNode start = makeNode("3");
    TreeNode end = makeNode("10");
    TreeNode increment = makeNode("2");
    TreeNode control = makeTree("CommandBlock", variable, start, end, increment);

    TreeNode commandsInLoop = makeTree("Forward", variable);
    TreeNode overallCommand = makeTree("For", control, commandsInLoop);

    assertEquals(9, executeCommand(makeBasicCommand(overallCommand)), TOLERANCE);
    assertEquals(24, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);

  }

  /**
   * Tests the If command
   */
  @Test
  void testIf() {
    TreeNode conditional = makeNode("5");
    TreeNode distance = makeNode("60");
    TreeNode forward = makeTree("Forward", distance);
    TreeNode root = makeTree("If", conditional, forward);
    assertEquals(60, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(60, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
    conditional = makeNode("0");
    root = makeTree("If", conditional, forward);
    assertEquals(0, executeCommand(makeBasicCommand(root)), TOLERANCE);
  }

  /**
   * Tests the IfElse command
   */
  @Test
  void testIfElse() {
    TreeNode conditional = makeNode("5");
    TreeNode distance = makeNode("60");
    TreeNode ifBlock = makeTree("Forward", distance);
    distance = makeNode("50");
    TreeNode elseBlock = makeTree("Forward", distance);
    TreeNode root = makeTree("IfElse", conditional, ifBlock, elseBlock);
    assertEquals(60, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(60, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
    conditional = makeNode("0");
    root = makeTree("IfElse", conditional, ifBlock, elseBlock);
    assertEquals(50, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(110, turtleInformation.getActiveTurtle().getYPosition(), TOLERANCE);
  }

  /**
   * Tests the Repeat command repeat 2 [ repeat 3 [ fd 100 ] ]
   */
  @Test
  void testNestedRepeat() {
    TreeNode child1 = makeNode("2");
    TreeNode child_100 = makeNode("100");
    TreeNode subChild1_1_1 = makeTree("Forward", child_100);
    TreeNode subChild1_1 = makeNode("3");
    TreeNode subChild1_2 = makeTree("CommandBlock", subChild1_1_1);
    TreeNode subChild1 = makeTree("Repeat", subChild1_1, subChild1_2);
    TreeNode child2 = makeTree("CommandBlock", subChild1);
    TreeNode root = makeTree("Repeat", child1, child2);
    assertEquals(100, executeCommand(makeBasicCommand(root)), TOLERANCE);
    assertEquals(600, turtleInformation.getActiveTurtle().getYPosition());
  }

  /**
   * Tests the Set Background method
   */
  @Test
  void testSetBackground() {
    TreeNode color = makeNode("9");
    TreeNode root = makeTree("SetBackground", color);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(9, val, TOLERANCE);
    assertEquals(9, commandBundle.getDisplayInformation().getBackgroundColor(), TOLERANCE);
  }

  /**
   * Tests the set pen color method
   */
  @Test
  void testSetPenColor() {
    TreeNode color = makeNode("8");
    TreeNode root = makeTree("SetPenColor", color);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(8, val, TOLERANCE);
    assertEquals(8, commandBundle.getDisplayInformation().getPenColor(), TOLERANCE);
  }

  /**
   * Tests the set turtle shape method
   */
  @Test
  void testSetTurtleShape() {
    TreeNode shape = makeNode("6");
    TreeNode root = makeTree("SetShape", shape);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(6, val, TOLERANCE);
    assertEquals(6, commandBundle.getDisplayInformation().getTurtleShape(), TOLERANCE);
  }

  /**
   * Tests the set pen size method
   */
  @Test
  void testSetPenSize() {
    TreeNode color = makeNode("5");
    TreeNode root = makeTree("SetPenSize", color);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(5, val, TOLERANCE);
    assertEquals(5, commandBundle.getDisplayInformation().getPenSize(), TOLERANCE);
  }

  /**
   * Tests the set palette command
   */
  @Test
  void testSetPalette() {
    TreeNode index = makeNode("4");
    TreeNode red = makeNode("34");
    TreeNode green = makeNode("212");
    TreeNode blue = makeNode("132");
    TreeNode root = makeTree("SetPalette", index, red, green, blue);
    double val = executeCommand(makeBasicCommand(root));
    assertEquals(4, val, TOLERANCE);
  }

  /**
   * Tests the pen color command
   */
  @Test
  void testPenColor() {
    TreeNode color = makeNode("1");
    TreeNode root = makeTree("SetPenColor", color);
    executeCommand(makeBasicCommand(root));
    TreeNode getColor = makeNode("GetPenColor");
    double val = executeCommand(makeBasicCommand(getColor));
    assertEquals(1, val, TOLERANCE);
  }

  /**
   * Tests the pen color command
   */
  @Test
  void testShape() {
    TreeNode shape = makeNode("2");
    TreeNode root = makeTree("SetShape", shape);
    executeCommand(makeBasicCommand(root));
    TreeNode getShape = makeNode("GetShape");
    double val = executeCommand(makeBasicCommand(getShape));
    assertEquals(2, val, TOLERANCE);
  }

  /**
   * Tests the ID command
   */
  @Test
  void testID(){
    TreeNode basicCommand = makeTreeWithStrings("BasicCommand", "2");
    TreeNode tell = makeTree("Tell", basicCommand);
    makeBasicCommand(tell).execute();
    TreeNode ID = makeNode("ID");
    assertEquals(2, makeBasicCommand(ID).execute(), TOLERANCE);
    basicCommand = makeTreeWithStrings("BasicCommand", "3", "7", "4");
    tell = makeTree("Tell", basicCommand);
    makeBasicCommand(tell).execute();
    assertEquals(3, makeBasicCommand(ID).execute(), TOLERANCE);
  }


  // Combining methods

  // Helper methods below


  // Makes a Tree with the top node being the string, and all children being the list of nodes
  private TreeNode makeTreeWithStrings(String root, String... children) {
    List<TreeNode> treeChildren = new ArrayList<>();
    for(String s: children){
      treeChildren.add(makeNode(s));
    }
    return new TreeNode(root, root, treeChildren, null);
  }

  // Makes a Tree with the top node being the string, and all children being the list of nodes
  private TreeNode makeTree(String root, TreeNode... children) {
    return new TreeNode(root, root, Arrays.asList(children.clone()), null);
  }

  // Makes a single TreeNode
  private TreeNode makeNode(String val) {
    return new TreeNode(val, null);
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
