package slogo.view;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.controller.FrontEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;

/**
 * Create the main screen where visuals and panes will be displayed
 *
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class ScreenCreator implements FrontEndInternalAPI {

  private static final String TITLE = "SLogo";
  private static final double DEFAULT_X = 1250.0;
  private static final double DEFAULT_Y = 800.0;
  private static final String DEFAULT_RESOURCES =
      HistoryDisplayPane.class.getPackageName() + ".resources.";
  private static final String IDS_FOR_TESTING = DEFAULT_RESOURCES + "IDsforTesting";

  private BorderPane root;
  private Scene scene;
  private Stage stage;
  private HistoryDisplayPane historyDisplayPane;
  private UserCommandPane userCommand;
  private ViewPane viewPane;
  private CommandButtonPane commandButtonPane;
  private Timeline timeline;

  /**
   * Purpose: Create the main stage and scene that holds all the other scenes.
   * Assumptions: There is a FrontEndExternalAPI
   * Parameters: FrontEndExternalAPI viewController
   * Exception: None
   */
  public ScreenCreator(FrontEndExternalAPI viewController) {
    stage = new Stage();
    stage.setResizable(true);
    root = new BorderPane();
    scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    String defaultLanguage = "English";
    ResourceBundle idResource = ResourceBundle.getBundle(IDS_FOR_TESTING);

    String styleSheet = "slogo/view/resources/default.css";
    scene.getStylesheets().add(styleSheet);

    historyDisplayPane = new HistoryDisplayPane(viewController, idResource, defaultLanguage);
    root.setRight(historyDisplayPane.getBox());

    userCommand = new UserCommandPane(viewController, idResource, defaultLanguage);
    root.setBottom(userCommand.getBottomPaneBoxArea());

    viewPane = new ViewPane(viewController, stage, idResource);
    root.setCenter(viewPane.getBox());

    commandButtonPane = new CommandButtonPane(viewController, idResource, defaultLanguage);
    root.setLeft(commandButtonPane.getBox());

    runSimulation();
  }

  /**
   * Purpose:
   * Assumptions:
   * Parameters:
   * Exception: None
   */
  private void setAnimationSpeed() {
    timeline.setRate(userCommand.getAnimationSpeed());
  }

  /**
   * Purpose: Returns the language from the combobox displayed on the view pane.
   * Assumptions: There is a language displayed on the view pane.
   * Parameters: None
   * Exception: None
   */
  public String getLanguage() {
    return viewPane.getLanguage();
  }

  /**
   * Sets the language of the command and the UI
   * Dependency: ScreenCreator class that calls this method
   * @param language string representing the language
   */
  public void updateLanguage(String language) {
    commandButtonPane.updateLanguage(language);
    userCommand.updateLanguage(language);
    historyDisplayPane.updateLanguage(language);
  }

  /**
   * Updates the tab to show the command history
   * Dependency exists with HistoryDisplayPane
   * @param commandHistory list of all command strings that was inputted to the text area
   */
  public void updateCommandHistory(Queue<String> commandHistory) {
    historyDisplayPane.updateCommandHistory(commandHistory);
  }

  /**
   * updates the variables and user defined commands to be displayed in the tab
   * @param variables name and value of the variables
   * @param userDefinedCommands name and object of the user-defined commands
   */
  public void updateVariablesAndUserDefinedCommands(Map<String, Double> variables,
      Map<String, UserDefinedCommand> userDefinedCommands) {
    historyDisplayPane.updateDisplayOfInformation(variables, userDefinedCommands);
  }

  /**
   * Allows for interactive buttons so that clicking a command history/variable/user-defined commands
   * would display the relevant command in the text area
   * @param command the command to display in the text area
   */
  public void displayCommandStringOnTextArea(String command) {
    userCommand.displayCommandStringOnTextArea(command);
  }

  private void runSimulation() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

      updateTurtleStates();
      setAnimationSpeed();
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    timeline.setRate(300);
  }

  /**
   * Purpose: Updates and displays the updated states of the turtle and displays them in the
   * TurtleDisplayPane
   * Assumptions: all commands queue were properly constructed and the backend sent the correct
   * updated states for each of the turtles
   * Parameters: None
   * Exception: None
   */
  public void updateTurtleStates() {
    viewPane.updateTurtleStates();
  }

  /**
   * Passes the information to the frontend (ScreenCreator) so that the type and the commands
   * can be added to the queue for animation.
   * Dependency: ScreenCreator class that calls this method
   * @param commandType command type to be executed
   * @param commandValues values of the results of the commands
   */
  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    viewPane.updateCommandQueue(commandType, commandValues);
  }

  /**
   * Sets the turtle position to be updated to the x and y coordinates
   * Dependency exists with the ScreenCreator class that calls this method
   * @param xPosition xCoord of the turtle
   * @param yPosition yCoord of the turtle
   */
  public void setTurtlePosition(double xPosition, double yPosition) {
    viewPane.moveTurtle(xPosition, yPosition);
  }

  /**
   * Purpose: Clear and reset the view pane when clearscreen is called
   * Assumptions: None
   * Parameters: None
   * Exception: None
   */
  public void clearScreen() {
    viewPane.clearScreen();
  }

  /**
   * Sets the ID of the turtle to be updated/displayed
   * @param turtleID ID of the turtle to be updated/displayed for animation
   */
  public void setActiveTurtle(int turtleID) {
    viewPane.setActiveTurtle(turtleID);
  }

  /**
   * This allows the frontend to keep a list of all frontend turtles that are set to active by the
   * backend. This allows the frontend to display different turtle images for active/inactive turtles
   * @param iDs all IDs of the active turtles
   */
  public void setActiveTurtles(List<Integer> iDs) {
    viewPane.setActiveTurtles(iDs);
  }

}
