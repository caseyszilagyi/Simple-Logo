package slogo.model.parse;


import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.ErrorHandler;
import slogo.model.parse.tokens.ListToken;
import slogo.model.tree.TreeNode;

public class MakeTokens {

  private final List<String> cleanedString;
  private List<TreeNode> tokens;
  private static final String TOKEN_PACKAGE = MakeTokens.class.getPackageName() + ".tokens.";
  private static final String COMMAND_PACKAGE = "slogo.model.resources.commands.";
  private static final String COMMAND_WITH_LISTS = "CommandBlocks";
  private ResourceBundle expectedParams;

  public MakeTokens(List<String> cleanedString) {
    this.cleanedString = cleanedString;
    expectedParams = ResourceBundle.getBundle(COMMAND_PACKAGE + COMMAND_WITH_LISTS);
    tokens = new ArrayList<>();
  }

  private void tokenize() {
    for(String s : cleanedString) {
      
    }
  }

  private ListToken makeListTokens(String listType) {
    ListToken toRet = null;
    try {
      toRet = (ListToken) Class.forName(TOKEN_PACKAGE + listType).getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new ErrorHandler("ListCannotBeMade");
    }
    return toRet;
  }



}
