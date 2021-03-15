package slogo.model.execution;

import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.tree.TreeNode;

/**
 * Once the input has been parsed into tree form, this class takes that tree and turns it into a
 * functional command chain that will return TurtleMovement objects
 */
public class TreeParser {

  private final TreeNode commandRoot;
  BasicCommandClassLoader COMMAND_CLASS_LOADER = new BasicCommandClassLoader();
  String TREE;

  public TreeParser(TreeNode root, String test) {
    commandRoot = root;
    TREE = test;
  }

  /**
   * post-order traversal through tree passed in. execute commands as you traverse and reach a command node. if not, save the children as parameters for command
   * condenses all subtrees into constant commands as it traverses up.

   public BasicCommand parseCommands(TreeNode root, CommandInformationBundle commandInfo) {
   if (root == null) { return null; }
   List<BasicCommand> childExecutions = new ArrayList<>(); //do we want this to be a list of basic commands or doubles returned from execute?
   for (TreeNode child : root.getChildren()) {
   childExecutions.add(parseCommands(child, commandInfo)); //how to make sure you're sending the updated command info
   }
   String commandType = root.getValue();
   BasicCommand rootCommand = COMMAND_CLASS_LOADER.makeCommand(commandType, (BasicCommand) childExecutions); //need to extract the childExecutions into indiv param
   double rootExecution = rootCommand.execute(commandInfo);
   return new Constant(rootExecution); //since execute is always an int, this will just condense the command and its children to a constant basic command

   //execute if the root is a command (childExecutions is a list of the parameters needed for execute)
   //construct a basic command object respective to string in the node (root.getValue())
   //create an arraylist for the parameters it got from children and use to .execute
   //pass information bubble to execute
   //if execute returns a number then add to the parameters of your parents
   //return execute of the root command
   }
   */

}


