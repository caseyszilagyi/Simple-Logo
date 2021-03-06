package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Creates the pane where the user will input their commands and run them
 */
public class UserCommandPane {
  public static final double WIDTH = 600.0;
  public static final double HEIGHT = 100.0;

  private HBox myBox;
  private TextArea myTextArea;

  public UserCommandPane() {
    myBox = new HBox();
    myBox.setAlignment(Pos.CENTER_LEFT);
    myBox.setSpacing(5.0);
    // change once there is css file only used for testing
    myBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: green;");
    addTextField();
    createButtons();
  }

  private void createButtons() {
    Button runButton = buttonCreation("Run");
    runButton.setOnAction(event -> System.out.println(myTextArea.getText()));
    Button clearButton = buttonCreation("Clear");
    clearButton.setOnAction(event -> myTextArea.clear());
  }

  private Button buttonCreation(String text) {
    Button button = new Button(text);
    button.setPrefHeight(HEIGHT);
    //button.getStyleClass().add("button");
    myBox.getChildren().add(button);
    return button;
  }

  private void addTextField() {
    myTextArea = new TextArea();
    myTextArea.setPrefWidth(WIDTH);
    myTextArea.setPrefHeight(HEIGHT);
    myBox.getChildren().add(myTextArea);
  }

  public HBox getBox() {
    return myBox;
  }
}
