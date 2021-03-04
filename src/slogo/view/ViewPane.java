package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Creates the view for where the turtle will be displayed
 */
public class ViewPane {
  private HBox myBox;
  public ViewPane() {
    myBox = new HBox();
    myBox.setAlignment(Pos.CENTER);
    myBox.setSpacing(5.0);
    // change once there is css file only used for testing
    myBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: red;");
  }

  public HBox getBox() {
    return myBox;
  }
}
