package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Creates the view for where the turtle will be displayed
 */
public class ViewPane {
  public static final String TURTLE_IMAGE = "Turtle2.gif";
  public static final double TURTLE_WIDTH = 70.0;
  public static final double TURTLE_HEIGHT = 70.0;

  private HBox paneBox;
  private turtleDisplay turtle;
  public ViewPane() {
    paneBox = new HBox();
    paneBox.setAlignment(Pos.CENTER);
    paneBox.setSpacing(5.0);
    // change once there is css file only used for testing
    paneBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: red;");
    turtle = new turtleDisplay(paneBox);
  }

  public HBox getBox() {
    return paneBox;
  }
}
