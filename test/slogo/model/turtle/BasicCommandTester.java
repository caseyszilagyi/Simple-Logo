package slogo.model.turtle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.*;


/**
 * This class is designed to test the implementations of each basic command separately in order
 * to guarantee that it is working before trying to concatenate them together or have them rely on
 * each other
 */
public class BasicCommandTester {

  private Turtle turtle;
  private BasicCommandClassLoader loader;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp(){
    turtle = new Turtle();
    loader = new BasicCommandClassLoader();
  }


  @Test
  void testForward(){
    BasicCommand forward = loadBasicCommand("Forward");
    forward.execute(turtle);


  }


  /**
   * Makes a basic command when passed a string name
   * @param commandName The string representing the class name
   * @return The basic command
   */
  BasicCommand loadBasicCommand(String commandName){
    return loader.makeCommand(commandName);
  }
}
