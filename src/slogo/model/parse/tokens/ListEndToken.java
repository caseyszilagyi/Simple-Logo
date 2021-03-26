package slogo.model.parse.tokens;

/**
 * object that specifies the end of any type of list so that the commandblock parser class is able to recognize the end of the list
 * in counting parameters.
 *
 * @author jincho
 */
public class ListEndToken extends Token{

  /**
   * constructs this list end token when a ] is reached
   * @param command identifier of this token in regex "]"
   */
  public ListEndToken(String command) {
    super(command);
  }

  /**
   * a list end token doesn't have parameters, so it doesn't keep track of a parameter count
   * parameters
   * @param blockSize the updating number of parameters for this specific token
   * @param command the command that is being checked for if it updates the number of parameters or is a parameter of a command that is considered
   *                a parameter of this token (ex: [ fd sum 50 50 ] where fd is a direct parameter of ths list token, so when sum is checked,
   *                it wont increment the counter as it is a sub parameter of the list aka parameter of a parameter)
   * @return
   */
  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize;
  }

  /**
   * since this is not a list or command, it doesn't have parameters of its own and therefore has no need to add parameter tokens
   * @param param the command that is being added the the list of parameter tokens for this token, whether that be direct or a sub param.
   */
  @Override
  public void addParamToken(Token param) {
    return;
  }
}
