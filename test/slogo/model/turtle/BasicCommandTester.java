package slogo.model.turtle;

import java.util.ArrayList;
import java.util.List;
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

  // Queries Testing

  /**
   * Tests the XCoordinate command
   */
  @Test
  void testXCoordinate() {
    moveTurtle(50);
    BasicCommand x = makeBasicCommand("XCoordinate");
    assertEquals(executeCommand(x), 50);
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

  // Boolean Operations Testing

  /**
   * Tests the Less than command
   */
  @Test
  void testLessThan() {
    BasicCommand lessThan = makeBasicCommand("LessThan", makeConstantCommand(50),
        makeConstantCommand(40));
    assertEquals(lessThan.execute(commandBundle), 0);
    lessThan = makeBasicCommand("LessThan", makeConstantCommand(40), makeConstantCommand(50));
    assertEquals(lessThan.execute(commandBundle), 1);
  }

  // Control Structure Testing

  /**
   * Tests the command to make variables
   */
  @Test
  void testMakeVariable() {
    BasicCommand make = makeVariableCommand("Yo", makeConstantCommand(50));
    make.execute(commandBundle);
    assertEquals(commandBundle.getVariable("Yo"), 50);
  }


  /**
   * Tests the repeat command. Uses the forward method to do so.
   */
  @Test
  void testRepeat() {
    BasicCommand forward = makeBasicCommand("Forward", makeConstantCommand(5));
    BasicCommand repeat = makeBasicCommand("Repeat", makeConstantCommand(5), forward);
    repeat.execute(commandBundle);
    assertEquals(commandBundle.getTurtle().getXPosition(), 25);
  }

  // Helper methods below


  // Makes a basic command out of the command name and
  private BasicCommand makeBasicCommand(String commandName, BasicCommand... commands) {
    return loader.makeCommand(commandName, commands);
  }

  // Makes a constant command with the given integer
  private BasicCommand makeConstantCommand(double value) {
    return loader.makeConstant(value);
  }

  // Makes a constant command with the given integer
  private BasicCommand makeVariableCommand(String varName, BasicCommand value) {
    return loader.makeVariable(varName, value);
  }

  // Moves the turtle a specified distance, useful for testing queries
  private void moveTurtle(double distance) {
    BasicCommand forward = makeBasicCommand("Forward", makeConstantCommand(distance));
    forward.execute(commandBundle);
  }

  private double executeCommand(BasicCommand command) {
    return command.execute(commandBundle);
  }
  /*
  // Rotates the turtle a specified angle, useful for testing queries
  private void rotateTurtle(double angle){
    BasicCommand forward = makeBasicCommand("Forward,", makeConstantCommand(distance));
    forward.execute(commandBundle);
  }
  */


}
