package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CommandButtonPane {
  private static final String BUTTON_PANE_ID = "CommandButtonPane";
  private static final String BUTTON = "regular-button";

  private VBox buttonBox;

  public CommandButtonPane() {
    buttonBox = new VBox();
    buttonBox.getStyleClass().add(BUTTON_PANE_ID);
    makeButtons();
  }

  private void makeButtons() {
    Button forwardButton = makeButton("Forward 50");
    Button backwardButton = makeButton("Backward 50");
    Button rightButton = makeButton("Right 90");
    Button leftButton = makeButton("Left 90");
    Button changePenButton = makeButton("Change Pen State");
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
