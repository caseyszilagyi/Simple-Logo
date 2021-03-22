package slogo.view;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.controller.FrontEndExternalAPI;


/**
 * Creates the view for where the turtle will be displayed
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


  public String getLanguage() {
    return choiceDisplay.getLanguage();
  }

  public void moveTurtle(double xCoordinate, double yCoordinate) {
    turtleDisplay.moveTurtle(xCoordinate, yCoordinate, choiceDisplay.getPenColor());
  }

  public Pane getBox() {
    return viewPane;
  }

  //These magic index values need to be processed in some other way
  //Current set up for these parameters is not SHY enough since we have to have
  // prior knowledge about the order of these parameters


  public void updateTurtleStates() {
    turtleDisplay.updateTurtleStates();
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    turtleDisplay.updateCommandQueue(commandType, commandValues);
  }

  public void clearScreen() {
    turtleDisplay.clearScreen();
  }

  public void setActiveTurtle(int turtleID) {
    turtleDisplay.setActiveTurtle(turtleID);
  }

  public void setActiveTurtles(List<Integer> iDs) {
    turtleDisplay.setActiveTurtles(iDs);
  }
}

