package slogo.model.parse.tokens;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * abstract token class that is specific to list tokens - constant, command, and variable. this token deals with counting the number of parameters
 * in a list, since this is not predefined. this will help in the CommandParser when the AST is constructed since these lists have an undefined
 * number of parameters and this is necessary info for knowing how many children to expect for a parent node (children = parameters). this is necessary
 * in setting up the list of commands from input in a way for the command block parser to create the CommandBlockParser that uses the increment
 * paramcount method to know how many parameters that command block (identified by []) should expect and put this info
 * in accessible location for the tree construction and translates the list tokens into command blocks that are then recognized by the command classes that execute the actual
 * block of commands.
 *
 * @author jincho
 */
public abstract class ListToken extends Token {
  protected List<Token> params;
  protected Deque<List<String>> paramTokensExpected;

  /**
   *
   *
   * @param command
   */
  public ListToken(String command) {
    super("CommandBlock");
    paramTokensExpected = new ArrayDeque<>();
    params = new ArrayList<>();
  }

  /**
   * clears the stack of list of expected parameters for commands within a list. each token has a stack of arraylists that is added to whenever
   * a command is reached in the list with the list of string representations of the parameters that is expected. this stack prevents
   * the list token from recognizing sub parameters of commands in the list as its own parameters.
   * everytime a subparameter is satisfied in the list of strings at the top of the stack, this method is called to remove it from the stack. once this one
   * string is removed from the list in the top of the stack, it moves down the stack as much as possible by popping empty lists of parameters, empty lists
   * indicating the parameters of a command in the list were satisfied, until it reaches a list of parameters not completely satisfied or the stack si empty,
   * meaning that all commands in the list have been satisfied.
   * @param stack stack of list of expected parameters by the commands within the list
   */
  protected void reduceTokenParamStack(Deque<List<String>> stack) {
    while (!stack.isEmpty()) {
      stack.peek().remove(0);
      if(stack.peek().isEmpty()) {
        stack.pop();
      } else { return; }
    }
  }

  /**
   * calls method addparamtoken to update the list of parameters for this specific list based on the command given and the updating blocksize count.
   *
   * @param blockSize updating count of parameters for the list
   * @param command token that is in the list and being checked for if it is a new direct child node of the list or a parameter of a token in the list
   * @return number of parameters in the list
   */
  @Override
  public int incrementParamCount(int blockSize, Token command) {
    addParamToken(command);
    return params.size();
  }






}
