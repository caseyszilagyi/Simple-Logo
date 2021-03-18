package slogo.view;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

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
  private static final String RESOURCE_PATH = "slogo.view.resources.languages";

  private BorderPane root;
  private Scene scene;
  private Stage stage;
  private HistoryDisplayPane historyDisplayPane;
  private UserCommandPane userCommand;
  private ViewPane viewPane;
  private FrontEndExternalAPI viewController;
  private String styleSheet;
  private ResourceBundle languageResource;

  public ScreenCreator(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    stage = new Stage();
    stage.setResizable(true);
    root = new BorderPane();
    scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    String defaultLanguage = "English";
    languageResource = ResourceBundle.getBundle(RESOURCE_PATH + "." + defaultLanguage);

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

  public String getLanguage() {
    return viewPane.getLanguage();
  }

  public void updateLanguage() {
    String language = viewPane.getLanguage();
    languageResource = ResourceBundle.getBundle(RESOURCE_PATH + "." + language);
  }

  public void updateCommandHistory(Queue<String> commandHistory) {
    historyDisplayPane.updateCommandHistory(commandHistory);
  }


  public void updateCommandHistory(Map<String, Double> variables,
      Map<String, String> userDefinedCommands) {
    historyDisplayPane.updateDisplayOfInformation(variables, userDefinedCommands);
  }

  public void displayCommandStringOnTextArea(String command) {
    userCommand.displayCommandStringOnTextArea(command);
  }
}
