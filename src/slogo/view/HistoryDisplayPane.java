package slogo.view;

import java.util.Map;
import java.util.Queue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import slogo.controller.FrontEndExternalAPI;


/**
 * Create the HBox for the display for history of commands, variables, and user commands
 */
public class HistoryDisplayPane {

  private static final String TITLE = "HISTORY, VARIABLES, AND USER COMMANDS";
  private static final String HISTORY_DISPLAY_PANE_ID = "HistoryDisplayPane";
  private static final String HISTORY_DISPLAY_PANE_TEXT = "HistoryDisplayPaneText";
  private static final String HISTORY_PANE_ID = "HistoryPane";
  private static final String HISTORY_BOX_ID = "HistoryBox";
  private static final String BUTTON = "regular-button";
  private static final String HISTORY_BUTTON = "history-button";
  private static final double TABS_HEIGHT = 570.0;

  private BorderPane basePane;
  private ScrollPane historyPane;
  private VBox historyBox;
  private ScrollPane varPane;
  private VBox varBox;
  private ScrollPane userPane;
  private VBox userBox;
  private FrontEndExternalAPI viewController;

  private Button clearButton;

  public HistoryDisplayPane(FrontEndExternalAPI viewController) {
    basePane = new BorderPane();
    basePane.getStyleClass().add(HISTORY_DISPLAY_PANE_ID);
    this.viewController = viewController;
    displayTitle();
    createHistoryPane();
    createVariablePane();
    createUserCommandsPane();
    createTabPane();
  }

  private void createTabPane() {
    TabPane tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    Tab history = new Tab("History");
    history.setContent(historyPane);
    Tab var = new Tab("Variables");
    var.setContent(varPane);
    Tab user = new Tab("User Defined Commands");
    user.setContent(userPane);
    tabPane.getTabs().addAll(history, var, user);
    basePane.setCenter(tabPane);
  }

  private void createUserCommandsPane() {
    userBox = makeBox();
    userPane = makeScrollPane(userBox);
  }

  private void createVariablePane() {
    varBox = makeBox();
    varPane = makeScrollPane(varBox);

  }

  private void createHistoryPane() {
    historyBox = makeBox();
    historyPane = makeScrollPane(historyBox);
  }

  private VBox makeBox() {
    VBox box = new VBox();
    box.setPrefHeight(TABS_HEIGHT);
    box.getStyleClass().add(HISTORY_BOX_ID);
    return box;
  }

  private ScrollPane makeScrollPane(VBox vBox) {
    ScrollPane scrollPane = new ScrollPane(vBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    scrollPane.getStyleClass().add(HISTORY_PANE_ID);
    return scrollPane;
  }

  private void displayTitle() {
    Label title = new Label(TITLE);
    title.setWrapText(true);
    title.getStyleClass().add(HISTORY_DISPLAY_PANE_TEXT);
    basePane.setTop(title);
  }

  public BorderPane getBox() {
    return basePane;
  }

  public void updateDisplayOfInformation(Map<String, Double> variables,
      Map<String, String> userDefinedCommands) {
    updateVariableDisplay(variables);
    updateUserDefinedCommands(userDefinedCommands);
  }

  private void updateUserDefinedCommands(Map<String, String> userDefinedCommands) {
    userBox.getChildren().clear();
    for (Map.Entry<String, String> command : userDefinedCommands.entrySet()) {
      Button button = makeButton(command.getKey(), userBox, HISTORY_BUTTON);
      userBox.getChildren().add(button);
      button
          .setOnAction(event -> viewController.displayCommandStringOnTextArea(command.getValue()));
    }
  }

  public void updateCommandHistory(Queue<String> commandHistory) {
    historyBox.getChildren().clear();
    for (String command : commandHistory) {
      Button button = makeButton(command, historyBox, HISTORY_BUTTON);
      historyBox.getChildren().add(button);
      button.setOnAction(event -> viewController.displayCommandStringOnTextArea(command));
    }
  }

  public void updateVariableDisplay(Map<String, Double> variables) {
    varBox.getChildren().clear();
    for (Map.Entry<String, Double> entry : variables.entrySet()) {
      Button button = makeButton(entry.getKey() + " = " + entry.getValue(), varBox, HISTORY_BUTTON);
      varBox.getChildren().add(button);
    }
  }

  private Button makeButton(String text, VBox vBox, String styleClass) {
    Button button = new Button(text);
    button.setWrapText(true);
    button.setPrefWidth(vBox.getWidth());
    button.getStyleClass().add(styleClass);
    return button;
  }
}