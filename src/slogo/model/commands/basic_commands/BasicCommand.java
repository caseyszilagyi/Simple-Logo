package slogo.model.commands.basic_commands;

/**
 * This interface is designed to be used for all of the basic commands that the turtle can have
 * called on it
 *
 * @author Casey Szilagyi
 */
public interface BasicCommand {

  /**
   * The execute command is what is called in order to use the command. This could be modifying the
   * turtle, storing a variable, storing a user defined command,  or just returning a value after
   * arithmetic
   *
   * @return An double representing the result of the command
   */
  public double execute();


}
