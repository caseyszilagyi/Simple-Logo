package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import slogo.controller.ViewController;

/**
 * Creates the pane where the user will input their commands and run them
 */
public class UserCommandPane {
  private static final double WIDTH = 600.0;
  private static final double HEIGHT = 100.0;

  private HBox box;
  private TextArea textArea;
  private ViewController viewController;

  public UserCommandPane(ViewController viewController) {
    this.viewController = viewController;
    box = new HBox();
    box.setAlignment(Pos.CENTER_LEFT);
    box.setSpacing(5.0);
    // change once there is css file only used for testing
    box.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: green;");
    addTextField();
    createButtons();
  }

  private void createButtons() {
    Button runButton = buttonCreation("Run");
    runButton.setOnAction(event -> viewController.processUserCommandInput(textArea.getText()));
    Button clearButton = buttonCreation("Clear");
    clearButton.setOnAction(event -> textArea.clear());
  }

  private Button buttonCreation(String text) {
    Button button = new Button(text);
    button.setPrefHeight(HEIGHT);
    //button.getStyleClass().add("button");
    box.getChildren().add(button);
    return button;
  }


  private void addTextField() {
    textArea = new TextArea();
    textArea.setPrefWidth(WIDTH);
    textArea.setPrefHeight(HEIGHT);
    box.getChildren().add(textArea);
  }

  public HBox getBox() {
    return box;
  }
}
