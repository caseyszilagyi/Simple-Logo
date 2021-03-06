package slogo.model.turtle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class is designed to test the implementations of each basic command separately in order to
 * guarantee that it is working before trying to concatenate them together or have them rely on each
 * other
 */
public class BasicCommandTester {

  private Turtle turtle;
  private BasicCommandClassLoader loader;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    turtle = new Turtle();
    loader = new BasicCommandClassLoader();
  }


  /**
   * Tests the forward command
   */
  @Test
  void testForward() {
    BasicCommand forward = makeBasicCommand("Forward", makeConstantCommand(50));
    forward.execute(turtle);
    assertEquals(turtle.getXPosition(), 50);
  }

  /**
   * Tests the baclcommand
   */
  @Test
  void testBack() {
    BasicCommand back = makeBasicCommand("Back", makeConstantCommand(50));
    back.execute(turtle);
    assertEquals(turtle.getXPosition(), -50);
  }

  // Arithmetic Testing

  /**
   * Tests the Sum command
   */
  @Test
  void testSum() {
    BasicCommand sum = makeBasicCommand("Sum", makeConstantCommand(50), makeConstantCommand(70));
    assertEquals(sum.execute(turtle), 120);
  }

  // Helper methods below

  /**
   * Makes a BasicCommand when passed a string name
   *
   * @param commandName The string representing the class name
   * @param commands    The BasicCommands that are passed to the constructor
   * @return The BasicCommand of the given type
   */
  private BasicCommand makeBasicCommand(String commandName, BasicCommand... commands) {
    return loader.makeCommand(commandName, commands);
  }

  /**
   * Makes a constant BasicCommand when passed an integer
   *
   * @param value The constant value
   * @return The constant in BasicCommand form
   */
  private BasicCommand makeConstantCommand(int value) {
    return loader.makeConstant(value);
  }


}
