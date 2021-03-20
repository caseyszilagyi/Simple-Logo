package slogo.model.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.tree.TreeNode;

/**
 * creates a n-ary tree of all commands and their parameters associated with this string input
 *
 * @author jincho jiyunhyo
 */
public class CommandParser implements Parser {

  // where to find resources specifically for this class
  private static final String RESOURCES_PACKAGE =
      "slogo.model.resources.commands.";
  private static final String LANGUAGES_PACKAGE = "slogo.model.resources.languages.";
  public List<String> preOrderResults = new ArrayList<>();
  // "types" and the regular expression patterns that recognize those types
  // note, it is a list because order matters (some patterns may be more generic)
  private Map<String, List<String>> parameters;
  public static Map<String, Pattern> syntaxMap;
  private TreeNode commandTree;
  private List<String> cleanCommands;
  private BackEndExternalAPI modelController;
  private InputCleaner inputCleaner;
  private MakeTokens tokenMaker;


  public CommandParser(String rawInput, String language, BackEndExternalAPI modelController) {
    this.modelController = modelController;
    parameters = new HashMap<>();
    syntaxMap = new HashMap<>();
    addParamCounts("CommandsParam");
    addRegExPatterns("Syntax");
    addUserDefParamCounts();
    inputCleaner = new InputCleaner(rawInput, language, modelController, this);
    tokenMaker = new MakeTokens(inputCleaner.cleanString(), this);
    cleanCommands = tokenMaker.tokenString();
    commandTree = new TreeNode(null);
    System.out.println("Command Taken in by the parser: " + rawInput);
    System.out.println("Clean command: " + cleanCommands);
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addParamCounts(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      List<String> params = new ArrayList<>(Arrays.asList(resources.getString(key).split(" ")));
      params.removeIf(command -> command.equals(""));
      addSingleParamCount(key, params);
//            System.out.println("Key: " + key);
//            System.out.println("Number: " + Arrays.asList(resources.getString(key).split(" ")));
//            System.out.println();
    }
  }


  /**
   * adds a single command and parameter count pair to the map
   *
   * @param command    command type name
   * @param paramCount number of parameters command will take in
   */
  public void addSingleParamCount(String command, List<String> paramCount) {
    parameters.put(command, paramCount);
  }

  private void addRegExPatterns(String regEx) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + regEx);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      syntaxMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  private void addUserDefParamCounts() {
    Map<String, UserDefinedCommand> userDefCommands = modelController.getUserDefinedCommands();
    for (String key : userDefCommands.keySet()) {
      int paramCounts = userDefCommands.get(key).getParamCount();
      List<String> paramString = new ArrayList<>();
      for(int i=0; i<paramCounts; i++) {
        paramString.add("NUM");
      }
      parameters.put(key, paramString);
    }
  }

  /**
   * makes the tree at the tree root node commandTree
   *
   * @return root node of command tree
   */
  public TreeNode makeTree() {
    System.out.println(parameters);
    Deque<String> commandQueue = new LinkedList<>(cleanCommands);
    System.out.println("QUEUE: " + commandQueue);

    while (!commandQueue.isEmpty()) {
      String command = commandQueue.removeFirst();
      TreeNode child = new TreeNode(command);
      child = checkCommandBlock(child);
      commandTree.addChild(child);
      insertNodeRecursive(commandQueue, child);
    }
    printPreOrder(commandTree);
    System.out.println(preOrderResults);
//    if (preOrderResults.size() != cleanCommands.size() + 1) {
//      throw new ErrorHandler("WrongParamNum");
//    }
    return commandTree;
  }

  private void printPreOrder(TreeNode root) {
    preOrderResults.add(root.getValue());
    if (root == null) {
      return;
    }
    System.out.println("Value: " + root.getCommand());
    for (TreeNode child : root.getChildren()) {
      System.out.println("Child to look at "+child.getValue());
      printPreOrder(child);
    }
  }

  private TreeNode insertNodeRecursive(Deque<String> splitCommands, TreeNode root) {
    if (getParamCount(root.getValue()) == 0) {
      System.out.println(root.getValue() + " is a leaf");
      return root;
    }
    System.out.println();
    int paramCount = getParamCount(root.getValue());
    System.out.println("param count: "+paramCount +" for "+root.getValue());
    for (int i = 0; i < getParamCount(root.getValue()); i++) {
      System.out.println("looking at "+i+"th param for "+root.getValue());
      String command;
      try {
        command = splitCommands.removeFirst();
      } catch (Exception e) {
        throw new ErrorHandler("WrongParamNum");
      }
      TreeNode dummy = new TreeNode(command);
      dummy = checkCommandBlock(dummy);
      root.addChild(dummy);
      System.out.println("Parent: " + root.getCommand());
      System.out.println("Child: " + dummy.getCommand());
      System.out.println("Split commands "+splitCommands);
      System.out.println("Split commands size "+splitCommands.size());
      insertNodeRecursive(splitCommands, dummy);
    }
    System.out.println(root.getCommand());
    if (!correctChildren(root.getChildren(), parameters.get(root.getValue()))) {
      throw new ErrorHandler("WrongParamNum");
    }
    return root;
  }

  /**
   * Returns respective parameter counts for the command specified
   *
   * @param text String representation of the command
   * @return String rep of the number of params needed for command
   */
  public Integer getParamCount(String text) {
    try {
      System.out.println("param: "+parameters.get(text).get(0));
      return parameters.get(text).size();
    } catch (Exception e) {
      return 0;
    }
  }

  private TreeNode checkCommandBlock(TreeNode node) {
    if (node.getCommand().contains("CommandBlock")) {
      node = new TreeNode(node.getCommand(), "CommandBlock");
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

  private boolean match(String text, Pattern... regex) {
    for (Pattern p : regex) {
      if(p.matcher(text).matches()) { return true;}
    }
    return false;
  }

  @Override
  public List<String> translateCommand(List<String> commandsBeforeTranslation) {
    return null;
  }
}