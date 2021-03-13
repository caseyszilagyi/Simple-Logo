package slogo.view;

import java.util.Deque;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * Create the HBox for the display for history of commands, variables, and user commands
 */
public class HistoryDisplayPane {
  private static final String TITLE = "HISTORY, VARIABLES, AND USER COMMANDS";
  private static final String HISTORY_DISPLAY_PANE_ID = "HistoryDisplayPane";
  private static final String HISTORY_DISPLAY_PANE_TEXT = "HistoryDisplayPaneText";
  private static final String HISTORY_PANE_ID = "HistoryPane";
  private static final String HISTORY_BOX_ID = "HistoryBox";
  private static final String BUTTON = "button";
  private static final String HISTORY_BUTTON = "history-button";
  private static final double TABS_HEIGHT = 600.0;

  private BorderPane basePane;
  private ScrollPane historyPane;
  private TabPane tabPane;
  private VBox historyBox;
  private ScrollPane varPane;
  private VBox varBox;
  private ScrollPane userPane;
  private VBox userBox;

  private Button clearButton;

  public HistoryDisplayPane() {
    basePane = new BorderPane();
    basePane.getStyleClass().add(HISTORY_DISPLAY_PANE_ID);

    displayTitle();
    createHistoryPane();
    createVariablePane();
    createUserCommandsPane();
    createTabPane();
  }

  private void createTabPane() {
    tabPane = new TabPane();
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

  public void updateCommandHistory(Deque<String> commandHistory) {
    historyBox.getChildren().clear();
    for(String command : commandHistory){
      Button button = makeButton(command, historyBox);
      //Label label = new Label(command, new Rectangle(50, 50));
      historyBox.getChildren().add(button);
    }
  }

  private Button makeButton(String text, VBox vBox) {
    Button button = new Button(text);
    button.setWrapText(true);
    button.setPrefWidth(vBox.getWidth());
    button.getStyleClass().add(HISTORY_BUTTON);
    return button;
  }
}
