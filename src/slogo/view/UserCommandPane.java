package slogo.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import slogo.controller.FrontEndExternalAPI;

/**
 * Creates the pane where the user will input their commands and run them
 */
public class UserCommandPane {

  private static final double WIDTH = 600.0;
  private static final double HEIGHT = 90.0;
  private static final String USER_COMMAND_PANE_ID = "UserCommandPane";
  private static final String TEXT_AREA = "text-area";
  private static final String BUTTON = "regular-button";
  private static final String FILE_PATH = "src/slogo/view/resources/reference";
  private static final String COMBO_BOX = "combo-box";
  private static final String DEFAULT_MESSAGE = "Choose command";
  private static final String TEXT_AREA_ID = "textArea";
  private static final String RUN_BUTTON_ID = "runButton";
  private static final String CLEAR_BUTTON_ID = "clearButton";
  private static final String DEFAULT_RESOURCES = HistoryDisplayPane.class.getPackageName() + ".resources.buttons.";
  private static final String REFLECTION_RESOURCE = DEFAULT_RESOURCES + "UserCommandReflectionActions";
  private static final String BUTTON_LANGUAGE = DEFAULT_RESOURCES + "languages.UserCommand";

  private GridPane bottomPaneBoxArea;
  private TextArea userInputTextArea;
  private FrontEndExternalAPI viewController;
  private ComboBox<String> helpDropDownBoxWithInformationAboutCommands;
  private Node helpButton;
  private Slider sliderToControlTurtleSpeed;
  private ResourceBundle idsForTesting;
  private ResourceBundle reflectionResource;
  private ResourceBundle buttonLanguageResource;
  private String errorMessage = "";

  public UserCommandPane(FrontEndExternalAPI viewController, ResourceBundle idResource, String lang) {
    this.viewController = viewController;
    bottomPaneBoxArea = new GridPane();
    bottomPaneBoxArea.getStyleClass().add(USER_COMMAND_PANE_ID);
    idsForTesting = idResource;
    reflectionResource = ResourceBundle.getBundle(REFLECTION_RESOURCE);
    buttonLanguageResource = ResourceBundle.getBundle(BUTTON_LANGUAGE + lang);
    addTextArea();
    createButtons();
    createSlider();
  }

  private void createSlider() {
    sliderToControlTurtleSpeed = new Slider(10, 5000, 100);
    bottomPaneBoxArea.add(sliderToControlTurtleSpeed, 5, 0);
  }

  public double getAnimationSpeed(){
    return sliderToControlTurtleSpeed.getValue();
  }

  private void createButtons() {
    makeButton("RunButton", 1);
    makeButton("ClearButton", 2);
    helpButton = makeButton("HelpButton", 3);
  }

  private void run() {
    try{
      viewController.processUserCommandInput(userInputTextArea.getText());
    }catch (Exception exception){
      errorMessage = exception.getMessage();
      Alert error = new Alert(AlertType.ERROR);
      error.setContentText(errorMessage);
      error.showAndWait();
    }
  }

  private void clear() {
    userInputTextArea.clear();
  }

  private void createHelpButton() {
    bottomPaneBoxArea.getChildren().remove(helpButton);
    File directoryPath = new File(FILE_PATH);
    ArrayList<String> allReferences = new ArrayList<>(Arrays.asList(directoryPath.list()));
    Collections.sort(allReferences);
    helpDropDownBoxWithInformationAboutCommands = new ComboBox<>();
    helpDropDownBoxWithInformationAboutCommands.getItems().addAll(allReferences);
    helpDropDownBoxWithInformationAboutCommands.setValue(buttonLanguageResource.getString("DefaultMessage"));
    helpDropDownBoxWithInformationAboutCommands.getStyleClass().add(COMBO_BOX);
    bottomPaneBoxArea.add(helpDropDownBoxWithInformationAboutCommands, 3, 0);
    helpDropDownBoxWithInformationAboutCommands.setOnAction(handler -> {
      displayCommandInformation(helpDropDownBoxWithInformationAboutCommands.getValue());
    });
  }

  private void displayCommandInformation(String command) {
    String fileName = FILE_PATH + "/" + command;
    String line = null;
    StringBuilder text = new StringBuilder();
    try {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while ((line = bufferedReader.readLine()) != null) {
        text.append(line);
        text.append("\n");
      }
      bufferedReader.close();
    } catch (IOException ex) {
      System.out.println("Error reading references");
    }
    Alert info = new Alert(AlertType.INFORMATION);
    info.setContentText(text.toString());
    info.showAndWait();
    bottomPaneBoxArea.getChildren().remove(helpDropDownBoxWithInformationAboutCommands);
    bottomPaneBoxArea.add(helpButton, 3, 0);
  }

  private Node makeButton(String key, int col) {
    Button button = new Button();
    button.setText(buttonLanguageResource.getString(key));
    button.setPrefHeight(HEIGHT);
    button.getStyleClass().add(BUTTON);
    button.setId(idsForTesting.getString(key));
    button.setOnAction(event -> reflectionMethod(key));
    bottomPaneBoxArea.add(button, col, 0);
    return button;
  }

  private void reflectionMethod(String key) {
    try {
      String methodName = reflectionResource.getString(key);
      Method m = UserCommandPane.this.getClass().getDeclaredMethod(methodName);
      m.invoke(UserCommandPane.this);
    }
    catch (Exception e) {
      new Alert(Alert.AlertType.ERROR);
    }
  }

  private void addTextArea() {
    userInputTextArea = new TextArea();
    userInputTextArea.setPrefWidth(WIDTH);
    userInputTextArea.setPrefHeight(HEIGHT);
    userInputTextArea.getStyleClass().add(TEXT_AREA);
    userInputTextArea.setId(idsForTesting.getString("TextArea"));
    bottomPaneBoxArea.add(userInputTextArea, 0, 0);
  }

  public GridPane getBottomPaneBoxArea() {
    return bottomPaneBoxArea;
  }

  public void displayCommandStringOnTextArea(String command) {
    userInputTextArea.setText(command);
  }

  public void updateLanguage(String lang) {
    buttonLanguageResource = ResourceBundle.getBundle(BUTTON_LANGUAGE + lang);
    bottomPaneBoxArea.getChildren().clear();
    addTextArea();
    createButtons();
    createSlider();
  }
}