package slogo.model.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.ErrorHandler;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.commands.basic_commands.command_types.Command;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.UserDefinedInformation;
import slogo.model.tree.TreeNode;

/**
 * This class is used to create instances of command objects at runtime
 *
 * @author Casey Szilagyi
 */
public class BasicCommandClassLoader {

  private final ClassLoader CLASS_LOADER;
  private final String COMMAND_CLASSES_PACKAGE = BasicCommand.class.getPackageName();

  /**
   * Instantiates the ClassLoader
   */
  public BasicCommandClassLoader() {
    CLASS_LOADER = new ClassLoader() {
    };
  }


  /**
   * Makes a basicCommand with the given string name
   *
   * @param node The node to make the basic command with
   * @return The BasicCommand object
   */
  public BasicCommand makeCommand(CommandInformationBundle informationBundle, TreeNode node) {

    // Checks for constant, user defined variable, or user defined command
    BasicCommand myCommand = checkSpecialCases(informationBundle, node);
    if(myCommand != null){
      return myCommand;
    }

    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + "." + node.getCommand())
          .getDeclaredConstructor(CommandInformationBundle.class, List.class)
          .newInstance(informationBundle, (Object) node.getChildren());
      myCommand = (BasicCommand) command;
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      System.out.println(node.getCommand());
      throw new ErrorHandler ("InvalidCommandName");
    }

    return myCommand;
  }

  // Checks for constant, user defined command, or variable. These are not part of the
  // default commands so they need special cases to check them
  private BasicCommand checkSpecialCases(CommandInformationBundle informationBundle, TreeNode node){

    String nodeName = node.getCommand();
    UserDefinedInformation userInfo = informationBundle.getUserDefinedInformation();

    if (isConstant(nodeName)) {
      return makeConstant(Double.parseDouble(nodeName));
    }

    if (isUserDefinedCommand(nodeName, userInfo)) {
      return getUserDefinedCommand(userInfo, node);
    }

    if (isVariableOrParameter(node)) {
      try{
        return getVariableOrParameter(userInfo, nodeName);
      }
      catch (Exception e){
        throw new ErrorHandler(e);
      }
    }
    return null;
  }

  // gets the variable or parameter value corresponding to the node. Throws an error
  // if it is does not exist
  private BasicCommand getVariableOrParameter(UserDefinedInformation userInfo, String nodeName) throws ErrorHandler {

    if(userInfo.hasParameter(nodeName)){
      return makeConstant(userInfo.getParameter(nodeName));
    }

    if (userInfo.hasVariable(nodeName)) {
      return makeConstant(userInfo.getVariable(nodeName));
    }
    System.out.println("invalid var: "+nodeName);
    throw new ErrorHandler("InvalidVariableName");
  }

  // Gets the user defined BasicCommand from the information bundle. Only called if the bundle
  // actually has the command
  private BasicCommand getUserDefinedCommand(UserDefinedInformation userInfo,
      TreeNode node) {
    UserDefinedCommand command = userInfo.getCommand(node.getCommand());
    command.passParams(node.getChildren());
    return command;
  }

  // Makes a constant BasicCommand
  private BasicCommand makeConstant(double number) {
    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + "." + "Constant")
          .getDeclaredConstructor(double.class).newInstance(number);
      myCommand = (BasicCommand) command;
    } catch (Exception e) {
      System.out.println("FailedConstantInstantiation");
    }

    return myCommand;
  }


  // Checks if the node has the format of a user defined variable or  parameter
  private boolean isVariableOrParameter(TreeNode node){
    return (node.getValue().charAt(0) - ':' == 0);
  }

  // Checks if the node is a user defined command
  private boolean isUserDefinedCommand(String nodeName, UserDefinedInformation userInfo){
    return userInfo.hasCommand(nodeName);
  }

  // Checks if the node is a constant
  private boolean isConstant(String nodeName) {
    try {
      Double.parseDouble(nodeName);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
