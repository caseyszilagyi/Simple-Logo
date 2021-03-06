package slogo.view;

import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import slogo.controller.FrontEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;


/**
 * Create the HBox for the display for history of commands, variables, and user commands
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class HistoryDisplayPane {

  private static final String HISTORY_DISPLAY_PANE_ID = "HistoryDisplayPane";
  private static final String HISTORY_DISPLAY_PANE_TEXT = "HistoryDisplayPaneText";
  private static final String HISTORY_PANE_ID = "HistoryPane";
  private static final String HISTORY_BOX_ID = "HistoryBox";
  private static final String EXAMPLE_BUTTON = "example-button";
  private static final String HISTORY_BUTTON = "history-button";
  private static final String BUTTON = "regular-button";
  private static final double TABS_HEIGHT = 570.0;
  private static final double TABS_WIDTH = 403.0;
  public static final String DEFAULT_RESOURCE_PACKAGE =
      HistoryDisplayPane.class.getPackageName() + ".resources.";
  private static final String EXAMPLE_FILE = "buttons.languages.ExampleCode";
  private static final String DISPLAY_BUTTONS =
      DEFAULT_RESOURCE_PACKAGE + "buttons.languages.HistoryDisplay";

  private BorderPane basePane;
  private Node historyPane;
  private VBox historyBox;
  private Node varPane;
  private VBox varBox;
  private Node userPane;
  private VBox userBox;
  private Node exPane;
  private VBox exBox;
  private FrontEndExternalAPI viewController;
  private VBox topBox;
  private Queue<String> displayCommandHistory;
  private ResourceBundle exampleCode;
  private ResourceBundle idsForTesting;
  private ResourceBundle historyTabResource;
  private TabPane tabPane;
  private double textWidth = 300.0;
  private Label title;
  private double SPACING = 5.0;

  /**
   * Purpose: Creates a BorderPane that holds the scrolling VBoxes that display
   *          information about history, variables, user defined commands,
   *          and examples.
   * Assumptions: None
   * Parameters: FrontEndExternalAPI viewController, ResourceBundle idResource,
   *             String lang
   * Exception: None
   */
  public HistoryDisplayPane(FrontEndExternalAPI viewController, ResourceBundle idResource,
      String lang) {
    basePane = new BorderPane();
    basePane.getStyleClass().add(HISTORY_DISPLAY_PANE_ID);
    this.viewController = viewController;
    exampleCode = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + EXAMPLE_FILE + lang);
    idsForTesting = idResource;
    historyTabResource = ResourceBundle.getBundle(DISPLAY_BUTTONS + lang);
    topBox = new VBox();
    topBox.getStyleClass().add(HISTORY_PANE_ID);
    basePane.setTop(topBox);
    tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    basePane.setCenter(tabPane);
    displayTitle(textWidth);
    createHistoryPane();
    createVariablePane();
    createUserCommandsPane();
    createExamplePane();
    createTabs();
    createClearHistoryButton();
  }

  private void createClearHistoryButton() {
    String key = "HistoryClear";
    Button clearButton = new Button(historyTabResource.getString(key));
    clearButton.getStyleClass().add(BUTTON);
    clearButton.setId(idsForTesting.getString(key));
    clearButton.setOnAction(event -> clearHistory());
    topBox.getChildren().add(clearButton);
  }

  private void clearHistory() {
    historyBox.getChildren().clear();
    displayCommandHistory.clear();
  }

  private void createTabs() {
    Tab history = createTab("HistoryTab");
    history.setContent(historyPane);
    Tab var = createTab("VariableTab");
    var.setContent(varPane);
    Tab user = createTab("UserTab");
    user.setContent(userPane);
    Tab ex = createTab("ExampleTab");
    ex.setContent(exPane);
    makeExampleCodeButtons();
    tabPane.setMaxWidth(TABS_WIDTH - SPACING * 2);
    tabPane.getTabs().addAll(history, var, user, ex);
  }

  private Tab createTab(String key) {
    Tab tab = new Tab(historyTabResource.getString(key));
    tab.setId(idsForTesting.getString(key));
    return tab;
  }

  private void createExamplePane() {
    double spacing = SPACING;
    exBox = makeBox();
    exBox.setSpacing(spacing);
    exPane = makeScrollPane(exBox);
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

  private Node makeScrollPane(VBox vBox) {
    ScrollPane scrollPane = new ScrollPane(vBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    scrollPane.getStyleClass().add(HISTORY_PANE_ID);
    return scrollPane;
  }

  private void displayTitle(double text_width) {
    title = new Label(historyTabResource.getString("Title"));
    title.setWrapText(true);
    title.setPrefWidth(text_width);
    title.getStyleClass().add(HISTORY_DISPLAY_PANE_TEXT);
    topBox.getChildren().add(title);
  }

  /**
   * Purpose: Return the BorderPane that is the baseBox (in the general
   *          form of a Node).
   * Assumptions: None
   * Parameters: None
   * Exception: None
   * Returns: Node baseBox
   */
  public Node getBox() {
    return basePane;
  }

  /**
   * updates the variables and user defined commands to be displayed in the tab
   * @param variables name and value of the variables
   * @param userDefinedCommands name and object of the user-defined commands
   */
  public void updateDisplayOfInformation(Map<String, Double> variables,
      Map<String, UserDefinedCommand> userDefinedCommands) {
    updateVariableDisplay(variables);
    updateUserDefinedCommands(userDefinedCommands);
  }

  private void updateUserDefinedCommands(Map<String, UserDefinedCommand> userDefinedCommands) {
    userBox.getChildren().clear();
    for (Map.Entry<String, UserDefinedCommand> command : userDefinedCommands.entrySet()) {
      Button button = makeButton(command.getKey(), userBox, HISTORY_BUTTON, "PreviousUserButton");
      userBox.getChildren().add(button);
      String dummy = command.getKey();
      for (int i = 0; i < command.getValue().getParamCount(); i++) {
        dummy += " 50";
      }
      String finalDummy = dummy;
      button.setOnAction(event -> displayOnTextArea(finalDummy));
    }
  }

  /**
   * Updates the tab to show the command history
   * Dependency exists with HistoryDisplayPane
   * @param commandHistory list of all command strings that was inputted to the text area
   */
  public void updateCommandHistory(Queue<String> commandHistory) {
    displayCommandHistory = commandHistory;
    historyBox.getChildren().clear();
    for (String command : commandHistory) {
      Button button = makeButton(command, historyBox, HISTORY_BUTTON, "PreviousCommandButton");
      historyBox.getChildren().add(button);
      button.setOnAction(event -> displayOnTextArea(command));
    }
  }

  /**
   * updates the variables and user defined commands to be displayed in the tab
   * @param variables name and value of the variables
   */
  public void updateVariableDisplay(Map<String, Double> variables) {
    varBox.getChildren().clear();
    for (Map.Entry<String, Double> entry : variables.entrySet()) {
      Button button = makeButton(entry.getKey() + " = " + entry.getValue(), varBox, HISTORY_BUTTON,
          "PreviousVarButton");
      varBox.getChildren().add(button);
      button.setOnAction(
          event -> displayOnTextArea("make " + entry.getKey() + " " + entry.getValue()));
    }
  }

  private void makeExampleCodeButtons() {
    double prefWidth = TABS_WIDTH - 20.0;
    Object[] allExCode = exampleCode.keySet().toArray();
    for (Object example : allExCode) {
      String exampleCodeString = exampleCode.getString(example.toString());
      String exampleCodewithLabel = example + ": " + exampleCodeString;
      Button button = makeButton(exampleCodewithLabel, exBox, EXAMPLE_BUTTON, "examplebutton");
      button.setPrefWidth(prefWidth);
      exBox.getChildren().add(button);
      button.setOnAction(event -> displayOnTextArea(exampleCodeString));
    }
  }

  private void displayOnTextArea(String code) {
    viewController.displayCommandStringOnTextArea(code);
  }

  private Button makeButton(String text, VBox vBox, String styleClass, String id) {
    Button button = new Button(text);
    button.setId(idsForTesting.getString(id));
    button.setWrapText(true);
    button.setPrefWidth(vBox.getWidth());
    button.getStyleClass().add(styleClass);
    return button;
  }

  /**
   * Purpose: Update the buttons and text displayed on this pane based on
   *          the language of the program.
   * Assumptions: There is a language set for the program
   * Parameters: String lang
   * Exception: None
   */
  public void updateLanguage(String lang) {
    exampleCode = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + EXAMPLE_FILE + lang);
    historyTabResource = ResourceBundle.getBundle(DISPLAY_BUTTONS + lang);
    exBox.getChildren().clear();
    tabPane.getTabs().clear();
    topBox.getChildren().clear();
    createTabs();
    if (lang.equals("Chinese")) {
      textWidth = 150.0;
    } else {
      textWidth = 300.0;
    }
    displayTitle(textWidth);
    createClearHistoryButton();
  }
}