package slogo.model.parse.tokens;

import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.model.SLogoCommandExecutor;
import slogo.model.parse.TokensParser;

/**
 * Makes all tokens with reflection. Token classes for reflection are defined in properties files TokensSyntax and CommandBlocks.
 * All tokens are of the Token abstract class but their types are specified from the TokenParser
 * class in the makeToken call. Due to the shared methods across all the Token object types, rather than having a Token interface, i chose
 * to have an abstract class, but this still hides the implementation of what specific Token type is made from the "maker" of tokens, or the
 * method call that calls the makeToken method.
 *
 * @author jincho
 */
public class TokenFactory {

  private static final String TOKENS_MAP = "TokenSyntax";

  private final ResourceBundle tokenMap;

  /**
   * constructs the TokenFactory object responsible for making a single Token with refleciton based on the String representation of
   * the desired purpose of the Token is. Gets information from the TokenParser class when the makeToken method is called and uses info from the
   * TokenSyntax and CommandBlocks properties files.
   */
  public TokenFactory() {
    tokenMap = ResourceBundle.getBundle(SLogoCommandExecutor.LANGUAGES_PACKAGE + TOKENS_MAP);
  }

  /**
   * method that makes the proper Token according to what is desired when method is called (the String representation of what is desired
   * with command). Uses reflection to make the proper token type as specified in properties files with proper mapping obtained
   * through the private tokenType method.
   *
   * @param command states the command name, or for lists the list type, that the respective token should be made for and returned.
   * @return Token of proper subclass type as specified by command.
   */
  public Token makeToken(String command) {
    String type = tokenType(command);
    if (command.contains("List")) { type = command; }
    Token toRet;
    try {
      toRet = (Token) Class.forName(TokensParser.TOKEN_PACKAGE + type).getDeclaredConstructor(String.class).newInstance(command);
    } catch (Exception e) {
      throw new ErrorHandler("TokenCannotBeMade");
    }
    return toRet;
  }

  private String tokenType(String command) {
    String regexType = "";
    for (String key : TokensParser.syntaxMap.keySet()) {
      Pattern check = TokensParser.syntaxMap.get(key);
      if (check.matcher(command).matches()) {
        regexType = key;
        break;
      }
    }
    if (!regexType.equals("")) {
      return tokenMap.getString(regexType);
    }
    return command;
  }

}
