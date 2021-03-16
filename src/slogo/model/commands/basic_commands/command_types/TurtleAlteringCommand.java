package slogo.model.commands.basic_commands.command_types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.turtle.Turtle;

/**
 * This abstract class is designed to be implemented by any BasicCommand that alters the state of
 * the turtle in any way
 *
 * @author Casey Szilagyi
 */
public abstract class TurtleAlteringCommand extends TurtleQueryCommand {

  private final Turtle TURTLE;
  private final CommandInformationBundle BUNDLE;
  private final List<Turtle> ACTIVE_TURTLES = new ArrayList<>();

  /**
   * Makes the BasicCommand and saves the turtle
   *
   * @param informationBundle The only part of this bundle that is needed is the turtle
   */
  public TurtleAlteringCommand(CommandInformationBundle informationBundle) {
    super(informationBundle);
    TURTLE = informationBundle.getTurtle();
    BUNDLE = informationBundle;
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
    TURTLE.changeXPosition(change);
  }

  /**
   * Changes the Y position of the turtle
   *
   * @param change The change in Y position
   */
  protected void changeTurtleY(double change) {
    TURTLE.changeYPosition(change);
  }

  /**
   * Changes the angle of the turtle
   *
   * @param change The change in angle in degrees, in the counter clockwise direction
   */
  protected void changeTurtleAngle(double change) {
    TURTLE.rotateCounterClockwise(change);
  }

  /**
   * Sets the X position of the turtle
   *
   * @param position The X position
   */
  protected void setTurtleX(double position) {
    TURTLE.setXPosition(position);
  }

  /**
   * Sets the Y position of the turtle
   *
   * @param position The Y position
   */
  protected void setTurtleY(double position) {
    TURTLE.setYPosition(position);
  }

  /**
   * Sets the angle position of the turtle
   *
   * @param angle The angle
   */
  protected void setAngle(double angle) {
    TURTLE.setAngle(angle);
  }

  /**
   * Sets the pen state
   *
   * @param penState The pen state
   */
  protected void changePenState(double penState) {
    TURTLE.setPenState(penState);
  }

  /**
   * Sets the turtle visibility
   *
   * @param visibility The visibility
   */
  protected void changeTurtleVisibility(double visibility) {
    TURTLE.setVisibility(visibility);
  }

  /**
   * Resets the screen
   */
  protected void reset() {
    TURTLE.clearScreen();
    updateFrontEnd();
    TURTLE.allowLines();
  }

  protected void updateFrontEnd() {
    BUNDLE.updateTurtle();
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
  protected double updateTurtle(ToDoubleFunction<Turtle> action){
    double result = action.applyAsDouble(TURTLE);
    updateFrontEnd();
    return result;
  }


}
