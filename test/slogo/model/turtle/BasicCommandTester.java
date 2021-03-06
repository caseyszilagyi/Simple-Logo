package slogo.model.turtle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.*;
import slogo.model.execution.CommandInformationBundle;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class is designed to test the implementations of each basic command separately in order to
 * guarantee that it is working before trying to concatenate them together or have them rely on each
 * other
 */
public class BasicCommandTester {

  private CommandInformationBundle commandBundle;
  private BasicCommandClassLoader loader;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    commandBundle = new CommandInformationBundle();
    loader = new BasicCommandClassLoader();
  }


  /**
   * Tests the forward command
   */
  @Test
  void testForward() {
    BasicCommand forward = makeBasicCommand("Forward", makeConstantCommand(50));
    forward.execute(commandBundle);
    assertEquals(commandBundle.getTurtle().getXPosition(), 50);
  }

  /**
   * Tests the backward command
   */
  @Test
  void testBackward() {
    BasicCommand back = makeBasicCommand("Backward", makeConstantCommand(50));
    back.execute(commandBundle);
    assertEquals(commandBundle.getTurtle().getXPosition(), -50);
  }



  // Arithmetic Testing

  /**
   * Tests the Sum command
   */
  @Test
  void testSum() {
    BasicCommand sum = makeBasicCommand("Sum", makeConstantCommand(50), makeConstantCommand(70));
    assertEquals(sum.execute(commandBundle), 120);
  }

  // Control Structure Testing

  /**
   * Tests the command to make variables
   */
  @Test
  void testMake(){
    BasicCommand make = makeVariableCommand("Yo", makeConstantCommand(50));
    make.execute(commandBundle);
    assertEquals(commandBundle.getVariable("Yo"), 50);
  }

  



  // Helper methods below





  // Makes a basic command out of the command name and
  private BasicCommand makeBasicCommand(String commandName, BasicCommand... commands) {
    return loader.makeCommand(commandName, commands);
  }

  // Makes a constant command with the given integer
  private BasicCommand makeConstantCommand(int value) {
    return loader.makeConstant(value);
  }

  // Makes a constant command with the given integer
  private BasicCommand makeVariableCommand(String varName, BasicCommand value) {
    return loader.makeVariable(varName, value);
  }


}
