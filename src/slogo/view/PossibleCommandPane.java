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
  private static final String TITLE = "POSSIBLE COMMANDS";
  private static final String POSSIBLE_COMMAND_PANE_ID = "PossibleCommandPane";
  private static final String SUB_GROUP_PANE_ID = "SubGroupPane";

  private VBox box;

  public PossibleCommandPane() {
    box = new VBox();
    box.getStyleClass().add(POSSIBLE_COMMAND_PANE_ID);
    displayTitle();
    createCommandSubGroups();
  }

  private void createCommandSubGroups() {
    VBox subGroupBox = new VBox();
    subGroupBox.getStyleClass().add(SUB_GROUP_PANE_ID);

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

    box.getChildren().add(subGroupBox);
  }

  private void displayTitle() {
    Text title = new Text(TITLE);
    box.getChildren().add(title);
  }

  public VBox getBox() {
    return box;
  }
}
