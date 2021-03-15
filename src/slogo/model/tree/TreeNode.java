package slogo.model.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * https://ruslanspivak.com/lsbasi-part7/
 */
public class TreeNode {

  String val;
  String commandClass;
  List<TreeNode> children = new LinkedList<>();

  public TreeNode(String data) {
    val = data;
    commandClass = data;
  }

  public TreeNode(String data, String command) {
    val = data;
    commandClass = command;
  }

  public TreeNode(String data, String command, List<TreeNode> child) {
    val = data;
    this.commandClass = command;
    children = child;
  }

  /**
   * Adds a single child to this node
   *
   * @param newChild - the child node to be added
   */
  public void addChild(TreeNode newChild) {
    children.add(newChild);
  }

  /**
   * Returns a list of the children of this node
   *
   * @return children - the children of the node
   */
  public List<TreeNode> getChildren() {
    return children;
  }

  /**
   * Returns the value saved in this node
   *
   * @return String representation of translated command, variable, or constant
   */
  public String getValue() {
    return val;
  }

  public void setValue(String command) {
    val = command;
  }

  public String getCommand() {
    return commandClass;
  }

}
