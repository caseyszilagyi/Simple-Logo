package slogo.model.parse.tokens;

/**
 * this token represents all commands, constants, and variables that can be reduced to constants, as all that we have implemented
 * should be able to reduce to a double or returns a double, this encompasses everything other than lists and [ ] which just indicate the start
 * and end of list and have no meaning outside of that. a num token would count as 1 parameter of a command as all of its own parameters and those
 * parameters' parameters, etc would condense up into one double and therefore one parameter.
 *
 * @author jincho
 */
public class NumToken extends Token{

  /**
   * constructs a num token and saves the command/variable/constant it is associated with for future reference
   * @param command specific name of the command/var/const
   */
  public NumToken(String command) { super(command); }

  /**
   * because a variable and all basic commands have a defined number of parameters, this is not used as a way to count parameters for
   * these tokens
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
   * because a variable and all basic commands have a defined number of parameters, this is not used as a way to count parameters for
   * these tokens
   * @param param the command that is being added the the list of parameter tokens for this token, whether that be direct or a sub param.
   */
  @Override
  public void addParamToken(Token param) {
    return;
  }

}
