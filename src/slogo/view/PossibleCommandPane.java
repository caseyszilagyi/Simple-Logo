package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;


/**
 * Create the HBox for the display for possible commands
 */
public class PossibleCommandPane {
  private VBox myBox;
  public PossibleCommandPane() {
    myBox = new VBox();
    myBox.setAlignment(Pos.CENTER);
    myBox.setSpacing(5.0);
    // change once there is css file only used for testing
    myBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
  }

  public VBox getBox() {
    return myBox;
  }
}
