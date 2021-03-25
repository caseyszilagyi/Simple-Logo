package slogo.model.parse.tokens;

import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;
import slogo.model.parse.Parser;

/**
 * Tokens are used to groups all the individual commands, constants, variables, lists into broader groups such that they are easier to parse through in order to obtain
 * more information prior to making an AST for parsing and execution. It uses a mix of regex syntax and properties files with specific information on
 * commands in terms of expected parameters in order to create these tokens through groupings. One of the biggest issues that is addressed with the creation of Tokens
 * is the issue of different types of lists and how to count the number of parameters within. Due to the fact that the commands, constants, and variables within are at
 * different levels of the hierarchy of commands for a tree, they shuold not all be counted as parameters. grouping these lists into what is expected allows for the command block parser to use token information to
 * extract this specific information without having to know what type of list or command is associated with these blocks.
 *
 * @author jincho
 */
public abstract class Token {

  private String command;
  private String value;
  private static final Pattern CONSTANT_REGEX = Parser.syntaxMap.get("Constant");

  /**
   * constructs a token object and saves the command that corresponds to it.
   * command instance variable is the general command that corresponds to this token
   * value is if the command needs to be unique or specified. for example, a general CommandBlock command would be represented by a toekn
   * with a command value of "CommandBlock" but a value of "CommandBlock_#" with # being a unique identifier for the command block in terms
   * of storing the number of parameters that list/command block takes.
   * @param command string representation of the command corresponding to this token
   */
  public Token(String command) {
    this.command = command;
    this.value = command;
  }

  /**
   * gives access to the general command name corresponding to this token
   * @return String representing the command name
   */
  public String getCommand() { return command; }

  /**
   * gives access to the more specific String representation of the unique identifier of the command corresponding to this token if
   * necessary (for command blocks or other potential examples of undefined param counts)
   * @return String representing the more specific command name
   */
  public String getValue() { return  value; }

  /**
   * sets the more specific/unique identifier of this token's command to be val
   * @param val the updated specific identifier for this token's command
   */
  public void setValue(String val) { value = val; }

  /**
   * checks to see if a command has been defined, whether it be a basic command that was predefined or is a user defined command
   * from a previous command input or the current one and it preceded.
   * @param command string representation of the command input that is being checked
   * @return true if command is an already-defined command
   */
  protected boolean isDefinedCommand(String command) { return CommandParser.commandParam.containsKey(command); }

  /**
   * checks if a command in considered a constant based on the string representation of the command using regex.
   * @param command string representation of the command input that is being checked.
   * @return true if the command is a constant regex
   */
  protected boolean isConstant(String command) { return CONSTANT_REGEX.matcher(command).matches(); }

  /**
   * updates the parameter count of a command while parsing through a series of commands based on the provided command parameter.
   * this is more specific for commands that do not have a defined number of parameters, in the case of our implementation
   * of Slogo, this only takes lists identified by [] into consideration as these are the only cases where the number of parameters
   * is uncertain as any number of commands, constants, variables can be placed in [ ].
   * @param blockSize the updating number of parameters for this specific token
   * @param command the command that is being checked for if it updates the number of parameters or is a parameter of a command that is considered
   *                a parameter of this token (ex: [ fd sum 50 50 ] where fd is a direct parameter of ths list token, so when sum is checked,
   *                it wont increment the counter as it is a sub parameter of the list aka parameter of a parameter)
   * @return updated param count for this token after checking command
   */
  public abstract int incrementParamCount(int blockSize, Token command);

  /**
   * sees if the command represented by the token will be added as a direct parameter to this token or if it will be added as a subparameter. this method
   * helps keep track of all the parameters and sub parameters so that only the direct parameters are counted in instances where # params is
   * not specified (in a list).
   * @param param the command that is being added the the list of parameter tokens for this token, whether that be direct or a sub param.
   */
  public abstract void addParamToken(Token param);

}
