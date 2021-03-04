package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Creates the pane where the user will input their commands and run them
 */
public class UserCommandPane {
  public static final int TEXT_FIELD_WIDTH = 200;
  private HBox myBox;
  private TextField myTextField;
  public UserCommandPane() {
    myBox = new HBox();
    myBox.setAlignment(Pos.CENTER_LEFT);
    myBox.setSpacing(5.0);
    // change once there is css file only used for testing
    myBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: green;");
    addTextField();
  }

  private void addTextField() {
    myTextField = new TextField();
    myTextField.setMinWidth(TEXT_FIELD_WIDTH);
    myBox.getChildren().add(myTextField);
  }

  public HBox getBox() {
    return myBox;
  }
}
