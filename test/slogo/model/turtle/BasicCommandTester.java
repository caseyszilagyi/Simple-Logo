package slogo.model.turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    commandBundle = new CommandInformationBundle();
    loader = new BasicCommandClassLoader();
  }


  /**
   * Tests the forward command
   */
  @Test
  void testForward() {
    TreeNode child = makeNode("50");
    TreeNode root = makeTree("Forward", child);
    makeBasicCommand(root).execute(commandBundle);
    assertEquals(50, commandBundle.getTurtle().getXPosition());
  }


  // Helper methods below



  // Makes a Tree with the top node being the string, and all children being the list of nodes
  private TreeNode makeTree(String root, TreeNode... children){
    return new TreeNode(root, Arrays.asList(children.clone()));
  }

  // Makes a single TreeNode
  private TreeNode makeNode(String val){
    return new TreeNode(val);
  }


  // Makes a basic command out of the command name and
  private BasicCommand makeBasicCommand(TreeNode node) {
    return loader.makeCommand(commandBundle, node, node.getChildren());
  }

  // Makes a constant command with the given integer
  private BasicCommand makeConstantCommand(double value) {
    return loader.makeConstant(value);
  }

  // Moves the turtle a specified distance, useful for testing queries
  private void moveTurtle(String distance) {
    TreeNode node = makeTree("Forward", makeNode("50"));
    BasicCommand forward = makeBasicCommand(node);
    forward.execute(commandBundle);
  }

  private double executeCommand(BasicCommand command) {
    return command.execute(commandBundle);
  }
  /*
  // Rotates the turtle a specified angle, useful for testing queries
  private void rotateTurtle(double angle){
    BasicCommand forward = makeBasicCommand("Forward,", makeConstantCommand(distance));
    forward.execute(commandBundle);
  }
  */



}
