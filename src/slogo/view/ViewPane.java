package slogo.view;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.controller.FrontEndExternalAPI;


/**
 * Creates the view for where the turtle will be displayed.
 *
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class ViewPane implements FrontEndInternalAPI {

  private static final String VIEW_PANE_ID = "ViewPane";

  private static final int ROWS = 700;
  private static final int COLS = 600;

  private GridPane viewPane;
  private TurtleDisplayPane turtleDisplay;
  private ViewChoicePane choiceDisplay;
  private FrontEndExternalAPI viewController;

  /**
   * Purpose: Creates the pane that displays the turtle and the choices associated with it
   * Assumptions: None
   * Parameters: FrontEndExternalAPI viewController, Stage s, ResourceBundle idResource
   * Exception: None
   */
  public ViewPane(FrontEndExternalAPI viewController, Stage s, ResourceBundle idResource) {
    this.viewController = viewController;
    viewPane = new GridPane();
    viewPane.setId(VIEW_PANE_ID);
    viewPane.getStyleClass().add(VIEW_PANE_ID);
    viewPane.setMaxWidth(ROWS + 10.0);
    viewPane.setMinWidth(ROWS + 10.0);
    turtleDisplay = new TurtleDisplayPane(viewController, viewPane, ROWS, COLS);
    choiceDisplay = new ViewChoicePane(viewController, s, viewPane, turtleDisplay, idResource);
  }

  /**
   * Purpose: Gets the language of the combo box in the choice pane
   * Assumptions: There is a language in the choice pane
   * Parameters: None
   * Exception: None
   * Return: String language
   */
  public String getLanguage() {
    return choiceDisplay.getLanguage();
  }

  /**
   * Sets the turtle position to be updated to the x and y coordinates
   * Dependency exists with the ScreenCreator class that calls this method
   * @param xCoordinate xCoord of the turtle
   * @param yCoordinate yCoord of the turtle
   */
  public void moveTurtle(double xCoordinate, double yCoordinate) {
    turtleDisplay.moveTurtle(xCoordinate, yCoordinate, choiceDisplay.getPenColor());
  }

  /**
   * Purpose: Returns the GridPane to the ScreenCreator to build the final screen
   * Assumptions: None
   * Parameters: None
   * Exception: None
   * Return: Pane viewPane
   */
  public Pane getBox() {
    return viewPane;
  }

  //These magic index values need to be processed in some other way
  //Current set up for these parameters is not SHY enough since we have to have
  // prior knowledge about the order of these parameters

  /**
   * Purpose: Updates and displays the updated states of the turtle and displays them in the
   * TurtleDisplayPane
   * Assumptions: all commands queue were properly constructed and the backend sent the correct
   * updated states for each of the turtles
   * Parameters: None
   * Exception: None
   */
  public void updateTurtleStates() {
    turtleDisplay.updateTurtleStates();
  }

  /**
   * Passes the information to the frontend (ScreenCreator) so that the type and the commands
   * can be added to the queue for animation.
   * Dependency: ScreenCreator class that calls this method
   * @param commandType command type to be executed
   * @param commandValues values of the results of the commands
   */
  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    turtleDisplay.updateCommandQueue(commandType, commandValues);
  }

  /**
   * Purpose: Clears the turtle view screen of the drawn lines and returns the turtle
   *          to the center of the display.
   * Assumptions: None
   * Parameters: None
   * Exception: None
   */
  public void clearScreen() {
    turtleDisplay.clearScreen();
  }

  /**
   * Purpose:
   * Assumptions:
   * Parameters:
   * Exception: None
   */
  public void setActiveTurtle(int turtleID) {
    turtleDisplay.setActiveTurtle(turtleID);
  }

  /**
   * This allows the frontend to keep a list of all frontend turtles that are set to active by the
   * backend. This allows the frontend to display different turtle images for active/inactive turtles
   * @param iDs all IDs of the active turtles
   */
  public void setActiveTurtles(List<Integer> iDs) {
    turtleDisplay.setActiveTurtles(iDs);
  }
}

