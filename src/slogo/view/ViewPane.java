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
   * Purpose: Moves the turtle on the display screen based on x and y coordinates from back end
   * Assumptions: x and y coordinates are valid
   * Parameters: double xCoordinate, double yCoordinate
   * Exception: None
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
   * Purpose:
   * Assumptions:
   * Parameters:
   * Exception: None
   */
  public void updateTurtleStates() {
    turtleDisplay.updateTurtleStates();
  }

  /**
   * Purpose:
   * Assumptions:
   * Parameters:
   * Exception: None
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
   * Purpose:
   * Assumptions:
   * Parameters:
   * Exception: None
   */
  public void setActiveTurtles(List<Integer> iDs) {
    turtleDisplay.setActiveTurtles(iDs);
  }
}

