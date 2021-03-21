package slogo.model.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.SLogoCommandExecutor;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.parse.tokens.Token;
import slogo.model.tree.TreeNode;

/**
 * creates a n-ary tree of all commands and their parameters associated with this string input
 *
 * @author jincho jiyunhyo
 */
public class CommandParser extends Parser {
  public List<String> preOrderResults = new ArrayList<>();
  private TreeNode commandTree;
  private List<Token> cleanCommands;
  private BackEndExternalAPI modelController;
  private InputCleaner inputCleaner;
  private MakeTokens tokenMaker;
  private CommandBlockParser commandBlockParser;


  public CommandParser(String rawInput, String language, BackEndExternalAPI modelController) {
    this.modelController = modelController;
    addUserDefParamCounts();
    inputCleaner = new InputCleaner(rawInput, language, modelController, this);
    tokenMaker = new MakeTokens(inputCleaner.parseResults());
    cleanCommands = tokenMaker.tokenString();
    commandBlockParser = new CommandBlockParser(cleanCommands, this);
    commandTree = new TreeNode(null, null);
    System.out.println("Command Taken in by the parser: " + rawInput);
    System.out.println("Clean command: " + cleanCommands);
  }

  /**
   * adds a single command and parameter count pair to the map
   *
   * @param command    command type name
   * @param paramCount number of parameters command will take in
   */
  public void addSingleParamCount(String command, List<String> paramCount) {
    commandParam.put(command, paramCount);
  }

  private void addUserDefParamCounts() {
    Map<String, UserDefinedCommand> userDefCommands = modelController.getUserDefinedCommands();
    for (String key : userDefCommands.keySet()) {
      int paramCounts = userDefCommands.get(key).getParamCount();
      List<String> paramString = new ArrayList<>();
      for (int i = 0; i < paramCounts; i++) {
        paramString.add("NUM");
      }
      addSingleParamCount(key, paramString);
    }
  }

  /**
   * makes the tree at the tree root node commandTree
   *
   * @return root node of command tree
   */
  public TreeNode makeTree() {
    System.out.println(commandParam);
    Deque<Token> commandQueue = new LinkedList<>(cleanCommands);
    System.out.println("QUEUE: " + commandQueue);

    while (!commandQueue.isEmpty()) {
      String command = commandQueue.removeFirst().getValue();
      TreeNode child = new TreeNode(command, null);
      child = checkCommandBlock(child);
      commandTree.addChild(child);
      insertNodeRecursive(commandQueue, child);
    }
    preOrderTraverse(commandTree);
    System.out.println(preOrderResults);
    return commandTree;
  }

  private void preOrderTraverse(TreeNode root) {
    preOrderResults.add(root.getValue());
    if (root == null) {
      return;
    }
    System.out.println("Value: " + root.getCommand());
    for (TreeNode child : root.getChildren()) {
      System.out.println("Child to look at "+child.getValue());
      preOrderTraverse(child);
    }
  }

  @Override
  public List<String> parseResults() {
    return preOrderResults;
  }

  private TreeNode insertNodeRecursive(Deque<Token> splitCommands, TreeNode root) {
    if (getParam(root.getValue()).size() == 0) {
      System.out.println(root.getValue() + " is a leaf");
      return root;
    }
    System.out.println();
    List<String> params = getParam(root.getValue());
    if(childOfMakeUserInstruction(root) && !root.getCommand().equals("CommandBlock")){
      params = new ArrayList<>();
    }
    System.out.println("param count: "+params.size() +" for "+root.getValue());
    for (int i = 0; i < params.size(); i++) {
      System.out.println("looking at "+i+"th param for "+root.getValue());
      String command;
      Token commandToken;
      try {
        commandToken = splitCommands.removeFirst();
        command = commandToken.getValue();
      } catch (Exception e) {
        throw new ErrorHandler("WrongParamNum");
      }
      TreeNode dummy = new TreeNode(command, root);
      dummy = checkCommandBlock(dummy);
      root.addChild(dummy);

      System.out.println("Parent: " + root.getCommand());
      System.out.println("Child: " + dummy.getCommand());
      System.out.println("Split commands "+splitCommands);
      System.out.println("Split commands size "+splitCommands.size());
      insertNodeRecursive(splitCommands, dummy);
    }
    System.out.println(root.getCommand());
    if (!correctChildren(root.getChildren(), params)) {
      throw new ErrorHandler("WrongParamNum");
    }
    return root;
  }

  private boolean childOfMakeUserInstruction(TreeNode root) {
    TreeNode parent = root.getParent();
    return parent != null && parent.getCommand().equals("MakeUserInstruction");
  }

  /**
   * Returns respective parameter counts for the command specified
   *
   * @param text String representation of the command
   * @return String rep of the number of params needed for command
   */
  public List<String> getParam(String text) {
    try {
      System.out.println("param: "+ commandParam.get(text).get(0));
      return commandParam.get(text);
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  private TreeNode checkCommandBlock(TreeNode node) {
    if (node.getCommand().contains("CommandBlock")) {
      node = new TreeNode(node.getCommand(), "CommandBlock", node.getParent());
    }
    return node;
  }

  private boolean correctChildren(List<TreeNode> children, List<String> expected) {
    System.out.println(children.isEmpty());
    System.out.println(expected);
    if(children.isEmpty() && expected==null) {  return true; }
    if(expected==null || children.size() != expected.size()) {  return false; }
    List<String> toMatch = new ArrayList<>();
    for(int ind=0; ind<children.size(); ind++) {
      TreeNode t = children.get(ind);
      if(match(t.getCommand(), syntaxMap.get("Command"), syntaxMap.get("Variable"), syntaxMap.get("Constant"))) { toMatch.add("NUM"); }
      if(t.getCommand().equals("CommandBlock")) { toMatch.set(ind, "LIST");}
    }
    System.out.println(toMatch);
    System.out.println(expected);
    return toMatch.equals(expected);
  }


}