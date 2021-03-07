package slogo.model.execution;

import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.tree.TreeNode;

/**
 * Once the input has been parsed into tree form, this class takes that tree and turns it into a
 * functional command chain that will return TurtleMovement objects
 */
public class TreeParser {

  BasicCommandClassLoader COMMAND_CLASS_LOADER = new BasicCommandClassLoader();
  String TREE;

  private final TreeNode commandRoot;

  public TreeParser(TreeNode root, String test){
    commandRoot = root;
    TREE = test;
  }

  /**
   * post-order traversal through tree passed in. execute commands as you traverse and reach a command node. if not, save the children as parameters for command
   *
   */
  public void parseCommands(TreeNode root) {
    if (root == null) { return; }
    for (TreeNode child : root.getChildren()) {
      parseCommands(child);
    }
    //execute if the root is a command
    //if not then add urself to the parameters?
  }

}


