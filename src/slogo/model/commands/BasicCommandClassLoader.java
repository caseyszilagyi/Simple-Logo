package slogo.model.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.commands.basic_commands.UserDefinedCommand;
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
   * Makes a basicCommand with the given string name
   *
   * @param node The node to make the basic command with
   * @return The BasicCommand object
   */
  public BasicCommand makeCommand(CommandInformationBundle informationBundle, TreeNode node) {

    // Checks if node is constant
    if (isConstant(node)) {
      return makeConstant(Double.parseDouble(node.getCommand()));
    }

    // Checks if node is user defined command
    if (informationBundle.getCommandMap().containsKey(node.getCommand())) {
      return callUserDefinedCommand(informationBundle, node);
    }

    // Checks if node is user defined parameter/variable
    if (node.getValue().charAt(0) - ':' == 0) {
      // Checks for global variable

      if (informationBundle.getVariableMap().containsKey(node.getCommand())) {
        return makeConstant(informationBundle.getVariableMap().get(node.getCommand()));
      }

      // Checks for local parameter
      for (int i = informationBundle.getParameterMap().size() - 1; i >= 0; i--) {
        if (informationBundle.getParameterMap().get(i).containsKey(node.getCommand())) {
          return makeConstant(informationBundle.getParameterMap().get(i).get(node.getCommand()));
        }
      }

    }

    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + node.getCommand())
          .getDeclaredConstructor(CommandInformationBundle.class, List.class)
          .newInstance(informationBundle, (Object) node.getChildren());
      myCommand = (BasicCommand) command;
    } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    return myCommand;
  }

  //
  private BasicCommand callUserDefinedCommand(CommandInformationBundle informationBundle,
      TreeNode node) {
    UserDefinedCommand command = informationBundle.getCommandMap().get(node.getCommand());
    command.passParams(node.getChildren());
    return command;
  }

  // Makes a constant BasicCommand
  private BasicCommand makeConstant(double number) {
    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + "Constant")
          .getDeclaredConstructor(double.class).newInstance(number);
      myCommand = (BasicCommand) command;
    } catch (Exception e) {
      System.out.println("Basic command class loader");
    }

    return myCommand;
  }


  // Checks if the node is a constant
  private boolean isConstant(TreeNode node) {
    try {
      Double.parseDouble(node.getValue());
      return true;
    } catch (Exception e) {
      return false;
    }

  }


}
