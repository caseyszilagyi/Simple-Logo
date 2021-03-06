package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Create the HBox for the display for possible commands
 */
public class PossibleCommandPane {
  public static final String TITLE = "POSSIBLE COMMANDS";
  private VBox myBox;
  public PossibleCommandPane() {
    myBox = new VBox();
    myBox.setAlignment(Pos.TOP_CENTER);
    myBox.setSpacing(5.0);
    // change once there is css file only used for testing
    myBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
    displayTitle();
    createCommandSubGroups();
  }

  private void createCommandSubGroups() {
    VBox subGroupBox = new VBox();
    subGroupBox.setAlignment(Pos.CENTER_LEFT);
    subGroupBox.setSpacing(5.0);

    ToggleGroup toggleGroup = new ToggleGroup();
    RadioButton turtleCommands = new RadioButton("Turtle Commands");
    turtleCommands.setToggleGroup(toggleGroup);
    turtleCommands.setSelected(true);
    subGroupBox.getChildren().add(turtleCommands);

    RadioButton turtleQueries = new RadioButton("Turtle Queries");
    turtleQueries.setToggleGroup(toggleGroup);
    subGroupBox.getChildren().add(turtleQueries);

    RadioButton mathOperations = new RadioButton("Math Operations");
    mathOperations.setToggleGroup(toggleGroup);
    subGroupBox.getChildren().add(mathOperations);

    RadioButton booleanOperations = new RadioButton("Boolean Operations");
    booleanOperations.setToggleGroup(toggleGroup);
    subGroupBox.getChildren().add(booleanOperations);

    // change once there is css file only used for testing
    subGroupBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: seagreen;");

    myBox.getChildren().add(subGroupBox);
  }

  private void displayTitle() {
    Text title = new Text(TITLE);
    myBox.getChildren().add(title);
  }

  public VBox getBox() {
    return myBox;
  }
}
