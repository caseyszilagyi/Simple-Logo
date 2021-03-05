package slogo.model.commands;

/**
 * Once the input has been parsed into tree form, this class takes that tree and turns it into a
 * functional command chain that will return TurtleMovement objects
 */
public class TreeParser {

  BasicCommandClassLoader COMMAND_CLASS_LOADER = new BasicCommandClassLoader();
  String TREE;

  public TreeParser(String test){
    TREE = test;
  }


  //for testing, still need to convert to tree form
  public static void main(String[] args){
    TreeParser testing = new TreeParser("fd 50");

  }

}


