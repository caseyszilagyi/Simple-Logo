package slogo.model.parse.tokens;

import java.util.ArrayList;
import java.util.List;
import slogo.model.parse.CommandParser;

/**
 * Class that specifies a list of commands such as the second list in defining a user defined instruction where only the commands
 * are considered as parameters/direct children of this list. This is taken into consideration in the addParamToken method.
 *
 * @author jincho
 */
public class CommandList extends ListToken {
  /**
   * Constructs the CommandList Token object with the makeToken reflection in the TokenFactory class.
   * @param command CommandList, which is the saved command for this particular token, but will be changed in CommandBlockParser to be
   *                the unique CommandBlock identifier
   */
  public CommandList(String command) { super(command); }

  /**
   * counts the number of parameters this list will consider as its direct child nodes. has a stack that keeps track of the commands that
   * need to be fulfilled with their parameters using the consecutive parameters. each time a new token is made within a list,
   * this method is called to see whether or not it is a direct parameter of the CommandBlock that represents the whole list. It helps
   * the token keep track of how many parameters the tree parser should expect when creating the AST.
   * @param param Token that is being checked to see if it is a parameter of the list or a parameter of a comman within the list
   */
  public void addParamToken(Token param) {
    if (isDefinedCommand(param.getValue())) {
      if (paramTokensExpected.isEmpty()) {
        params.add(param);
      }
      List<String> expected = new ArrayList<>(CommandParser.commandParam.get(param.getCommand()));
      if (!expected.isEmpty()) {
        paramTokensExpected.push(expected);
      }
    } else {
      reduceTokenParamStack(paramTokensExpected);
    }
  }
}
