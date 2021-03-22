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
  private String styleSheet;
  private Timeline timeline;
  private ResourceBundle idResource;

  public ScreenCreator(FrontEndExternalAPI viewController) {
    stage = new Stage();
    stage.setResizable(true);
    root = new BorderPane();
    scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    String defaultLanguage = "English";
    idResource = ResourceBundle.getBundle(IDS_FOR_TESTING);

    styleSheet = "slogo/view/resources/default.css";
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


  private void setAnimationSpeed() {
    timeline.setRate(userCommand.getAnimationSpeed());
  }

  public String getLanguage() {
    return viewPane.getLanguage();
  }

  public void updateLanguage(String language) {
    commandButtonPane.updateLanguage(language);
    userCommand.updateLanguage(language);
    historyDisplayPane.updateLanguage(language);
  }

  public void updateCommandHistory(Queue<String> commandHistory) {
    historyDisplayPane.updateCommandHistory(commandHistory);
  }


  public void updateVariablesAndUserDefinedCommands(Map<String, Double> variables,
      Map<String, UserDefinedCommand> userDefinedCommands) {
    historyDisplayPane.updateDisplayOfInformation(variables, userDefinedCommands);
  }

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

  public void updateTurtleStates() {
    viewPane.updateTurtleStates();
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    viewPane.updateCommandQueue(commandType, commandValues);
  }

  public void setTurtlePosition(double xPosition, double yPosition) {
    viewPane.moveTurtle(xPosition, yPosition);
  }

  public void clearScreen() {
    viewPane.clearScreen();
  }

  public void setActiveTurtle(int turtleID) {
    viewPane.setActiveTurtle(turtleID);
  }

  public void setActiveTurtles(List<Integer> iDs) {
    viewPane.setActiveTurtles(iDs);
  }

}