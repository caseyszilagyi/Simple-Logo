package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class turtleDisplay {
  public static final String TURTLE_IMAGE = "Turtle2.gif";
  public static final double TURTLE_WIDTH = 70.0;
  public static final double TURTLE_HEIGHT = 70.0;

  private ImageView turtle;
  private HBox root;

  public turtleDisplay(HBox hBox) {
    root = hBox;
    createTurtle();
  }

  private void createTurtle() {
    Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(TURTLE_IMAGE));
    turtle = new ImageView(image);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    root.getChildren().add(turtle);
  }
}
