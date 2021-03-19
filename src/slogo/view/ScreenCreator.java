package slogo.view;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import slogo.controller.FrontEndExternalAPI;

/**
 * Create the main screen where visuals and panes will be displayed
 *
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class ScreenCreator {

  private static final String TITLE = "SLogo";
  private static final double DEFAULT_X = 1150.0;
  private static final double DEFAULT_Y = 800.0;

  private BorderPane root;
  private Scene scene;
  private Stage stage;
  private HistoryDisplayPane historyDisplayPane;
  private UserCommandPane userCommand;
  private ViewPane viewPane;
  private FrontEndExternalAPI viewController;
  private String styleSheet;
  private int frameDelay;

  public ScreenCreator(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    stage = new Stage();
    stage.setResizable(true);
    root = new BorderPane();
    scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    styleSheet = "slogo/view/resources/default.css";
    scene.getStylesheets().add(styleSheet);

    historyDisplayPane = new HistoryDisplayPane(viewController);
    root.setCenter(historyDisplayPane.getBox());

    userCommand = new UserCommandPane(viewController);
    root.setBottom(userCommand.getBox());

    viewPane = new ViewPane(stage);
    root.setLeft(viewPane.getBox());

  }


  public void moveTurtle(List<Double> parameters) {

    System.out.println("parameters: " + parameters);
    viewPane.updateTurtle(parameters);

//    if(parameters.get(5) == 1){
//      reset();
//    }
  }

  //TODO: REMOVE LATER THIS IS ONLY FOR DEBUGGING
  private void reset() {

    viewPane = new ViewPane(stage);
    root.setCenter(viewPane.getBox());

  }

  public String getLanguage() {
    return viewPane.getLanguage();
  }

  public void updateCommandHistory(Queue<String> commandHistory) {
    historyDisplayPane.updateCommandHistory(commandHistory);
  }


  public void updateVariablesAndUserDefinedCommands(Map<String, Double> variables,
      Map<String, String> userDefinedCommands) {
    historyDisplayPane.updateDisplayOfInformation(variables, userDefinedCommands);
  }

  public void displayCommandStringOnTextArea(String command) {
    userCommand.displayCommandStringOnTextArea(command);
  }
}
