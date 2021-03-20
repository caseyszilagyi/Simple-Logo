package slogo.model;

import java.util.Map;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * This interface is meant to be implemented by any class that takes in an input
 * and acts upon a certain set of objects.
 */
public interface CommandExecutor {

  /**
   * This method is passed a string and a command is executed as a result
   * @param command The raw string that is put in
   */
  public void executeCommand(String command, String language);


  /**
   * Gets an unmodifiable copy of the map of user defined commands
   *
   * @return The copy of the command map
   */
  public Map<String, UserDefinedCommand> getCommandMap();

  /**
   * Gets an unmodifiable copy of the user defined variables map
   *
   * @return The variable map
   */
  public Map<String, Double> getVariableMap();

}
