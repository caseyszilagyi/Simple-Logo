package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import slogo.controller.FrontEndExternalAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Creates the pane where the user will input their commands and run them
 */
public class UserCommandPane {
  private static final double WIDTH = 600.0;
  private static final double HEIGHT = 100.0;
  private static final String USER_COMMAND_PANE_ID = "UserCommandPane";
  private static final String TEXT_AREA = "text-area";
  private static final String BUTTON = "regular-button";
  private static final String FILE_PATH = "src/slogo/view/resources/reference";
  private static final String COMBO_BOX = "combo-box";
  private static final String DEFAULT_MESSAGE = "Choose command";

  private HBox box;
  private TextArea textArea;
  private FrontEndExternalAPI viewController;
  private ComboBox<String> helpComboBox;
  private Button helpButton;

  public UserCommandPane(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    box = new HBox();
    box.getStyleClass().add(USER_COMMAND_PANE_ID);
    addTextField();
    createButtons();
  }

  private void createButtons() {
    Button runButton = makeButton("Run");
    runButton.setOnAction(event -> viewController.processUserCommandInput(textArea.getText()));
    Button clearButton = makeButton("Clear");
    clearButton.setOnAction(event -> textArea.clear());
    helpButton = makeButton("Help");
    helpButton.setOnAction(event -> createHelpButton());
  }

  private void createHelpButton() {
    box.getChildren().remove(helpButton);
    File directoryPath = new File(FILE_PATH);
    String[] contents = directoryPath.list();
    ArrayList<String> allReferences = new ArrayList<>(Arrays.asList(contents));
    Collections.sort(allReferences);
    helpComboBox = new ComboBox<>();
    helpComboBox.getItems().addAll(allReferences);
    helpComboBox.setValue(DEFAULT_MESSAGE);
    helpComboBox.getStyleClass().add(COMBO_BOX);
    box.getChildren().add(helpComboBox);
    helpComboBox.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        displayCommandInformation(helpComboBox.getValue());
      }
    });
  }

  private void displayCommandInformation(String command) {
    String fileName = FILE_PATH + "/" + command;
    String line = null;
    StringBuilder text = new StringBuilder();
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while((line = bufferedReader.readLine()) != null)
      {
        text.append(line);
        text.append("\n");
      }
      bufferedReader.close();
    }
    catch(IOException ex)
    {
      System.out.println("Error reading file named '" + fileName + "'");
    }
    Alert info = new Alert(AlertType.INFORMATION);
    info.setContentText(text.toString());
    info.showAndWait();
    box.getChildren().remove(helpComboBox);
    box.getChildren().add(helpButton);
  }

  private Button makeButton(String text) {
    Button button = new Button(text);
    button.setPrefHeight(HEIGHT);
    button.getStyleClass().add(BUTTON);
    box.getChildren().add(button);
    return button;
  }

  private void addTextField() {
    textArea = new TextArea();
    textArea.setPrefWidth(WIDTH);
    textArea.setPrefHeight(HEIGHT);
    textArea.getStyleClass().add(TEXT_AREA);
    box.getChildren().add(textArea);
  }

  public HBox getBox() {
    return box;
  }
}