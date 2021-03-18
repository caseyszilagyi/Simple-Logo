package slogo.model.commands.basic_commands.command_types;

import java.util.List;
import java.util.function.Consumer;
import slogo.controller.BackEndExternalAPI;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.execution.Turtle;

/**
 * This abstract class is designed to be implemented by any BasicCommand that alters the state of
 * the turtle in any way
 *
 * @author Casey Szilagyi
 */
public abstract class TurtleAlteringCommand extends TurtleQueryCommand {

  private final Turtle activeTurtle;
  private final CommandInformationBundle BUNDLE;
  private final BackEndExternalAPI MODEL_CONTROLLER;
  private final List<Turtle> ALL_TURTLES;

  /**
   * Makes the BasicCommand and saves the turtle
   *
   * @param informationBundle The only part of this bundle that is needed is the turtle
   */
  public TurtleAlteringCommand(CommandInformationBundle informationBundle) {
    super(informationBundle);
    activeTurtle = informationBundle.getActiveTurtle();
    ALL_TURTLES = informationBundle.getAllTurtles();
    BUNDLE = informationBundle;
    MODEL_CONTROLLER = informationBundle.getModelController();

  }

  /**
   * Executes the command. This is implemented in the subclass because it is different for each
   * command
   *
   * @return The double value representing the result of the executed method
   */
  public abstract double execute();

  /**
   * Changes the X position of the turtle
   *
   * @param change The change in X position
   */
  protected void changeTurtleX(double change) {
    activeTurtle.changeXPosition(change);
  }

  /**
   * Changes the Y position of the turtle
   *
   * @param change The change in Y position
   */
  protected void changeTurtleY(double change) {
    activeTurtle.changeYPosition(change);
  }

  /**
   * Changes the angle of the turtle
   *
   * @param change The change in angle in degrees, in the counter clockwise direction
   */
  protected void changeTurtleAngle(double change) {
    activeTurtle.rotateCounterClockwise(change);
  }

  /**
   * Sets the X position of the turtle
   *
   * @param position The X position
   */
  protected void setTurtleX(double position) {
    activeTurtle.setXPosition(position);
  }

  /**
   * Sets the Y position of the turtle
   *
   * @param position The Y position
   */
  protected void setTurtleY(double position) {
    activeTurtle.setYPosition(position);
  }

  /**
   * Sets the angle position of the turtle
   *
   * @param angle The angle
   */
  protected void setAngle(double angle) {
    activeTurtle.setAngle(angle);
    MODEL_CONTROLLER.setTurtleAngle(getAngle());
  }

  /**
   * Sets the pen state
   *
   * @param penState The pen state
   */
  protected void changePenState(double penState) {
    activeTurtle.setPenState(penState);
    MODEL_CONTROLLER.setPenState(penState);
  }

  /**
   * Sets the turtle visibility
   *
   * @param visibility The visibility
   */
  protected void changeTurtleVisibility(double visibility) {
    activeTurtle.setVisibility(visibility);
    MODEL_CONTROLLER.setTurtleVisibility(visibility);
  }

  /**
   * Resets the screen
   */
  protected void reset() {
    activeTurtle.clearScreen();
    updateFrontEnd();
    activeTurtle.allowLines();
    MODEL_CONTROLLER.clearScreen();
  }

  protected void updateFrontEnd() {
    BUNDLE.updateTurtle();
    MODEL_CONTROLLER.setTurtlePosition(getXCoordinate(), getYCoordinate());
  }


  /**
   * Will need to change this to take in a hashset of all the active turtles (individual
   * method different for a single turtle?) and then the actions to execute on those
   * turtles. In the java example, the roster is something that will already be in this
   * class, so this method will take two parameters
   *
   * Actually I guess my "roster" is the set of currently active turtle IDs
   *
   * Can we make it so that the turtle itself accepts the lambda expression
   * rather than doing it in this class? seems tricky. I'm not exactly sure
   * how it would work to call specific methods in the turtle class
   *
   * Actually the tester is the same for every method that would call this, so
   * not sure if that part would be useful. Can still maybe pass up these methods?
   * Although not sure how useful that is. I guess so that the logic of only
   * doing it on certain turtles is contained up here rather than in the subclasses
   */
  protected void updateTurtle(Consumer<Turtle> turtleAction){
    turtleAction.accept(activeTurtle);
    updateFrontEnd();
  }


}
