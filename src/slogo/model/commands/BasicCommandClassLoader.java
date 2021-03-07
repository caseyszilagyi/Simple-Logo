package slogo.model.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This class is used to create instances of command objects at runtime
 *
 * @author Casey Szilagyi
 */
public class BasicCommandClassLoader {

  private final ClassLoader CLASS_LOADER;
  private final String COMMAND_CLASSES_PACKAGE = "slogo.model.commands.basic_commands.";

  /**
   * Instantiates the ClassLoader
   */
  public BasicCommandClassLoader() {
    CLASS_LOADER = new ClassLoader() {
    };
  }



  /**
   * Makes a Variable BasicCommand. This needs to be done in a slightly different way because the
   * constructor takes a string and an integer
   * @param identifier The name of the variable
   * @param number The name of the basicCommand
   * @return The BasicCommand object
   */
  public BasicCommand makeVariable(String identifier, Double number) {
    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + "MakeVariable")
          .getDeclaredConstructor(String.class, Double.class).newInstance(identifier, number);
      myCommand = (BasicCommand) command;
    } catch (Exception e){
      System.out.println("Basic command class loader");
    }

    return myCommand;
  }


  /**
   * Makes a Constant BasicCommand. This needs to be done in a slightly different way because the
   * constructor takes integers rather than BasicCommands
   *
   * @param number The name of the basicCommand
   * @return The BasicCommand object
   */
  public BasicCommand makeConstant(double number) {
    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + "Constant")
          .getDeclaredConstructor(double.class).newInstance(number);
      myCommand = (BasicCommand) command;
    } catch (Exception e){
      System.out.println("Basic command class loader");
    }

    return myCommand;
  }

  /**
   * Makes a basicCommand with the given string name
   *
   * @param node The node to make the basic command with
   * @return The BasicCommand object
   */
  public BasicCommand makeCommand(CommandInformationBundle informationBundle, TreeNode node, List<TreeNode> children) {

    if(isConstant(node)){
      return makeConstant(Double.parseDouble(node.getValue()));
    }

    if(informationBundle.getCommand((node.getValue())) != null){
      return informationBundle.getCommand((node.getValue()));
    }

    if(informationBundle.getVariable((node.getValue())) != null){
      return informationBundle.getVariable((node.getValue()));
    }

    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + node.getValue())
          .getDeclaredConstructor(List.class).newInstance((Object) children);
      myCommand = (BasicCommand) command;
    } catch (Exception e){
      System.out.println("Command Doesn't exist!!!!");
    }
    return myCommand;
  }

  // Checks if the node is a constant
  private boolean isConstant(TreeNode node) {
    try {
      Integer.parseInt(node.getValue());
      return true;
    } catch(Exception e) {
      return false;
    }

  }



}
