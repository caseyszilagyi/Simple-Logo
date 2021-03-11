package slogo.view;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Create the HBox for the display for history of commands, variables, and user commands
 */
public class HistoryDisplayPane {
  private static final String TITLE = "HISTORY, VARIABLES,\nAND USER COMMANDS";
  private static final String HISTORY_DISPLAY_PANE_ID = "HistoryDisplayPane";
  private static final String HISTORY_DISPLAY_PANE_TEXT = "HistoryDisplayPaneText";

  private VBox box;

  public HistoryDisplayPane() {
    box = new VBox();
    box.getStyleClass().add(HISTORY_DISPLAY_PANE_ID);
    displayTitle();
    createHistoryPane();
    createVariablePane();
    createUserCommandsPane();
  }

  private void createUserCommandsPane() {
  }

  private void createVariablePane() {
  }

  private void createHistoryPane() {
  }

  private void displayTitle() {
    Text title = new Text(TITLE);
    title.getStyleClass().add(HISTORY_DISPLAY_PANE_TEXT);
    box.getChildren().add(title);
  }

  public VBox getBox() {
    return box;
  }
}
