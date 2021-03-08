package slogo.model.turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.ModelController;
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
  private ModelController modelController;
  static final double TOLERANCE = 0.0005;

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
    assertEquals(50, commandBundle.getTurtle().getXPosition(), TOLERANCE);
  }

  /**
   * Tests the backward command
   */
  @Test
  void testBackward() {
    TreeNode child = makeNode("60");
    TreeNode root = makeTree("Backward", child);
    executeCommand(makeBasicCommand(root));
    assertEquals(-60, commandBundle.getTurtle().getXPosition(), TOLERANCE);
  }

  // Turtle Queries

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


  // Boolean Operations

  /**
   * Tests the LessThan command
   */
  @Test
  void testLessThan() {
    TreeNode child1 = makeNode("60");
    TreeNode child2 = makeNode("40");
    TreeNode root = makeTree("LessThan", child1, child2);
    assertEquals(0, executeCommand(makeBasicCommand(root)) , TOLERANCE);
    root = makeTree("LessThan", child2, child1);
    assertEquals(1, executeCommand(makeBasicCommand(root)) , TOLERANCE);
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
    assertEquals(60, executeCommand(makeBasicCommand(root)) , TOLERANCE);
    assertEquals(60, commandBundle.getVariableMap().get("Awesome") , TOLERANCE);
  }


  // Helper methods below



  // Makes a Tree with the top node being the string, and all children being the list of nodes
  private TreeNode makeTree(String root, TreeNode... children){
    return new TreeNode(root, root,  Arrays.asList(children.clone()));
  }

  // Makes a single TreeNode
  private TreeNode makeNode(String val){
    return new TreeNode(val);
  }


  // Makes a basic command out of the command name and
  private BasicCommand makeBasicCommand(TreeNode node) {
    return loader.makeCommand(commandBundle, node);
  }

  // Moves the turtle a specified distance, useful for testing queries
  private void moveTurtle(String distance) {
    TreeNode node = makeTree("Forward", makeNode("50"));
    BasicCommand forward = makeBasicCommand(node);
    forward.execute();
  }

  private double executeCommand(BasicCommand command) {
    return command.execute();
  }

  /*
  // Rotates the turtle a specified angle, useful for testing queries
  private void rotateTurtle(double angle){
    BasicCommand forward = makeBasicCommand("Forward,", makeConstantCommand(distance));
    forward.execute(commandBundle);
  }
  */



}
