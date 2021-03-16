package slogo.model;


import java.util.ArrayList;
import java.util.List;
import slogo.model.tree.TreeNode;

public class MakeTreeNodes {

  private List<String> cleanedString;
  private List<TreeNode> nodes;

  public MakeTreeNodes(List<String> cleanedString) {
    this.cleanedString = cleanedString;
    nodes = new ArrayList<>();
  }



}
