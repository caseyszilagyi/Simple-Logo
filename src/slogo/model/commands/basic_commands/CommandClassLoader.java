package slogo.model.commands.basic_commands;

import java.lang.reflect.InvocationTargetException;

/**
 * This class is used to create instances of command objects at runtime
 *
 * @author Casey Szilagyi
 */
public class CommandClassLoader {

  private final ClassLoader CLASS_LOADER;
  private final String COMMAND_CLASSES_PACKAGE = "slogo.model.commands.basic_commands.";

  /**
   * Instantiates the ClassLoader
   */
  public CommandClassLoader(){
    CLASS_LOADER = new ClassLoader() {
    };
  }

  /**
   * Makes a basicCommand with the given string name
   * @param commandName The name of the basicCommand
   * @return The BasicCommand object
   */
  public BasicCommand makeCommand(String commandName){
    BasicCommand myCommand = null;
    try{
      Object command = CLASS_LOADER.loadClass(COMMAND_CLASSES_PACKAGE + commandName).getDeclaredConstructor().newInstance();
      myCommand = (BasicCommand) command;
    } catch (ClassNotFoundException e){
    } catch(NoSuchMethodException e) {
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
