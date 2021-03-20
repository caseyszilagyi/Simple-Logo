package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import slogo.controller.FrontEndExternalAPI;

public class CommandButtonPane {
  private static final String BUTTON_PANE_ID = "CommandButtonPane";
  private static final String BUTTON = "regular-button";
  private FrontEndExternalAPI viewController;

  private VBox buttonBox;

  public CommandButtonPane(FrontEndExternalAPI viewController) {
    buttonBox = new VBox();
    this.viewController = viewController;
    buttonBox.getStyleClass().add(BUTTON_PANE_ID);
    makeButtons();
  }

  private void makeButtons() {
    Button forwardButton = makeButton("Forward 50");
    forwardButton.setOnAction(event -> viewController.processUserCommandInput("fd 50"));
    Button backwardButton = makeButton("Backward 50");
    backwardButton.setOnAction(event -> viewController.processUserCommandInput("bk 50"));
    Button rightButton = makeButton("Right 45");
    rightButton.setOnAction(event -> viewController.processUserCommandInput("rt 45"));
    Button leftButton = makeButton("Left 45");
    leftButton.setOnAction(event -> viewController.processUserCommandInput("lt 45"));
    Button penUpButton = makeButton("Pen Up");
    penUpButton.setOnAction(event -> viewController.processUserCommandInput("penup"));
    Button penDownButton = makeButton("Pen Down");
    penDownButton.setOnAction(event -> viewController.processUserCommandInput("pendown"));
    Button clearscreenButton = makeButton("Clearscreen");
    clearscreenButton.setOnAction(event -> viewController.processUserCommandInput("clearscreen"));
    Button homeButton = makeButton("Home");
    homeButton.setOnAction(event -> viewController.processUserCommandInput("home"));
  }

  private Button makeButton(String text) {
    Button button = new Button(text);
    button.getStyleClass().add(BUTTON);
    buttonBox.getChildren().add(button);
    return button;
  }

  public VBox getBox() {
    return buttonBox;
  }
}
