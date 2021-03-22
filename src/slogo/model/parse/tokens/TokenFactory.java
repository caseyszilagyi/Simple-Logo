package slogo.model.parse.tokens;

import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.model.SLogoCommandExecutor;
import slogo.model.parse.TokensParser;

public class TokenFactory {

  private static final String TOKENS_MAP = "TokenSyntax";

  private ResourceBundle tokenMap;

  public TokenFactory() {
    tokenMap = ResourceBundle.getBundle(SLogoCommandExecutor.LANGUAGES_PACKAGE + TOKENS_MAP);
  }

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
