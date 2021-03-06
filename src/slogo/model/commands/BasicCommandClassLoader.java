package slogo.model.commands;

import java.lang.reflect.InvocationTargetException;
import slogo.model.commands.basic_commands.BasicCommand;

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
   * Makes a Constant BasicCommand. This needs to be done in a slightly different way because the
   * constructor takes integers rather than BasicCommands
   *
   * @param number The name of the basicCommand
   * @return The BasicCommand object
   */
  public BasicCommand makeConstant(int number) {
    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + "Constant")
          .getDeclaredConstructor(int.class).newInstance(number);
      myCommand = (BasicCommand) command;
    } catch (ClassNotFoundException e) {
    } catch (NoSuchMethodException e) {
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return myCommand;
  }

  /**
   * Makes a basicCommand with the given string name
   *
   * @param commandName The name of the basicCommand
   * @param commands    The BasicCommands that will be given to the constructor of the command
   * @return The BasicCommand object
   */
  public BasicCommand makeCommand(String commandName, BasicCommand... commands) {
    BasicCommand myCommand = null;
    try {
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + commandName)
          .getDeclaredConstructor(BasicCommand[].class).newInstance((Object) commands);
      myCommand = (BasicCommand) command;
    } catch (ClassNotFoundException e) {
    } catch (NoSuchMethodException e) {
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return myCommand;
  }


}
