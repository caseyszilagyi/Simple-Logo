package slogo.model.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * https://ruslanspivak.com/lsbasi-part7/
 */
public class TreeNode {
    String val;
    List<TreeNode> children = new LinkedList<>();

    public TreeNode(String data){
        val = data;
    }

    public TreeNode(String data,List<TreeNode> child){
        val = data;
        children = child;
    }

}
