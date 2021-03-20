package slogo.view;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Creates the view for where the turtle will be displayed
 *
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class ViewPane {
  private static final String VIEW_PANE_ID = "ViewPane";

  private static final int ROWS = 700;
  private static final int COLS = 600;

  private BorderPane viewPane;
  private TurtleDisplayPane turtleDisplay;
  private ViewChoicePane choiceDisplay;
  private double xCoord;
  private double yCoord;
  private double previousAngle = 90;

  public ViewPane(Stage s) {
    viewPane = new BorderPane();
    viewPane.setId(VIEW_PANE_ID);
    viewPane.getStyleClass().add(VIEW_PANE_ID);
    viewPane.setMaxWidth(ROWS + 10.0);
    viewPane.setMinWidth(ROWS + 10.0);
    xCoord = 0;
    yCoord = 0;
    turtleDisplay = new TurtleDisplayPane(viewPane, ROWS, COLS);
    choiceDisplay = new ViewChoicePane(s, viewPane, turtleDisplay);
  }

  // TODO: Think of better way to pass language
  public String getLanguage() {
    return choiceDisplay.getLanguage();
  }

  public void moveTurtle(double xCoordinate, double yCoordinate) {
    System.out.println();
    System.out.println("Move turtle called");

    System.out.println();
    turtleDisplay.moveTurtle(xCoordinate, yCoordinate, choiceDisplay.getPenColor());
  }

  public BorderPane getBox() {
    return viewPane;
  }

  //These magic index values need to be processed in some other way
  //Current set up for these parameters is not SHY enough since we have to have
  // prior knowledge about the order of these parameters
  public void updateTurtle(List<Double> parameters) {
    moveTurtle(parameters.get(0), parameters.get(1));

      turtleDisplay.updateTurtle(parameters);

  }



  public void displayTurtleUpdates() {
    turtleDisplay.updateTurtlePosition();
  }
}


/*  public void moveTurtleByDistance(double distance) {
    // do the calculations to make the turtle go forward
    // THIS WAS WAY HARDER THAN I THOGUGHT
    // because the angles/getrotate are all messed up
    double turtleX;
    double turtleY;
    double turtleAngle = ((-turtle.getRotate() - 90) * Math.PI) / (180);
    turtleX = turtle.getX() - Math.cos(turtleAngle) * distance;
    turtleY = turtle.getY() + Math.sin(turtleAngle) * distance;
    if (!penUP) {
      //createLine(turtleX, turtleY);
    }

    turtle.setX(turtleX);
    turtle.setY(turtleY);

  }*/

