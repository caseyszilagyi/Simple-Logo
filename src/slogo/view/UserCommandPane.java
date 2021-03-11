package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import slogo.controller.FrontEndExternalAPI;

/**
 * Creates the pane where the user will input their commands and run them
 */
public class UserCommandPane {
  private static final double WIDTH = 600.0;
  private static final double HEIGHT = 100.0;
  private static final String USER_COMMAND_PANE_ID = "UserCommandPane";
  private static final String TEXT_AREA = "text-area";
  private static final String BUTTON = "button";

  private HBox box;
  private TextArea textArea;
  private FrontEndExternalAPI viewController;

  public UserCommandPane(FrontEndExternalAPI viewController) {
    this.viewController = viewController;
    box = new HBox();
    box.getStyleClass().add(USER_COMMAND_PANE_ID);
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
