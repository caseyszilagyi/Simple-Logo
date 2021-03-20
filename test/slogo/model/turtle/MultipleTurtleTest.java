package slogo.model.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.Turtle;
import slogo.model.tree.TreeNode;


/**
 * This class is designed to test the implementations of each basic command separately in order to
 * guarantee that it is working before trying to concatenate them together or have them rely on each
 * other
 */
public class MultipleTurtleTest {

  static final double TOLERANCE = 0.05;
  private CommandInformationBundle commandBundle;
  private BasicCommandClassLoader loader;
  private BackEndExternalAPI modelController;
  private FrontEndExternalAPI viewController;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    modelController = new ModelController();
    commandBundle = new CommandInformationBundle(modelController);
    loader = new BasicCommandClassLoader();
  }


  /**
   * Testing the tell method
   */
  @Test
  void basicTestTell(){
    TreeNode tell = makeTree("Tell", "1", "2", "3");
    executeCommand(makeBasicCommand(tell));
    moveTurtle("50");
    List<Turtle> turtles = commandBundle.getAllTurtles();
    assertEquals(50, turtles.get(0).getYPosition(), TOLERANCE);
    assertEquals(50, turtles.get(1).getYPosition(), TOLERANCE);
    assertEquals(50, turtles.get(2).getYPosition(), TOLERANCE);
  }




  // Helper methods below


  // Makes a Tree with the top node being the string, and all children being the list of nodes
  private TreeNode makeTree(String root, String... children) {
    List<TreeNode> treeChildren = new ArrayList<>();
    for(String s: children){
      treeChildren.add(makeNode(s));
    }
    return new TreeNode(root, root, treeChildren);
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
    TreeNode node = makeTree("Forward", distance);
    BasicCommand forward = makeBasicCommand(node);
    forward.execute();
  }

  // Rotates the turtle a specified angle in the clockwise direction, useful for testing queries
  private void rotateTurtleClockwise(String angle) {
    TreeNode node = makeTree("Right", angle);
    BasicCommand rotate = makeBasicCommand(node);
    rotate.execute();
  }

  private double executeCommand(BasicCommand command) {
    return command.execute();
  }


}