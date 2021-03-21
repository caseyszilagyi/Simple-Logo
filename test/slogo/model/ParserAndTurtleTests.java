package slogo.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Main;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.DisplayInformation;
import slogo.model.execution.TurtleInformation;
import slogo.model.execution.UserDefinedInformation;
import slogo.model.turtle.DummyViewController;

/**
 * Tests designed to test more complicated commands with the parser and turtle movements,
 * including those with loops and multiple turtles
 */
public class ParserAndTurtleTests {

  static final double TOLERANCE = 0.05;
  private CommandExecutor EXECUTOR;
  private CommandInformationBundle BUNDLE;
  private TurtleInformation TURTLE_INFO;
  private DisplayInformation DISPLAY_INFO;
  private UserDefinedInformation USER_INFO;

  /**
   * Sets up the turtle and the classloader
   */
  @BeforeEach
  void setUp() {
    BackEndExternalAPI modelController = new ModelController();
    modelController.setViewController(new DummyViewController());
    BUNDLE = new CommandInformationBundle(modelController);
    EXECUTOR = new SLogoCommandExecutor(modelController, BUNDLE);
    TURTLE_INFO = BUNDLE.getTurtleInformation();
    DISPLAY_INFO = BUNDLE.getDisplayInformation();
    USER_INFO = BUNDLE.getUserDefinedInformation();
  }
  


}
