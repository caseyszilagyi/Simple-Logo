package slogo.view;

import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 * Create the HBox for the display for history of commands, variables, and user commands
 */
public class HistoryDisplayPane {
  private static final String TITLE = "HISTORY, VARIABLES,\nAND USER COMMANDS";
  private static final String HISTORY_DISPLAY_PANE_ID = "HistoryDisplayPane";
  private static final String HISTORY_DISPLAY_PANE_TEXT = "HistoryDisplayPaneText";
  private static final String HISTORY_PANE_ID = "HistoryPane";

  private GridPane basePane;
  private ScrollPane historyPane;

  public HistoryDisplayPane() {
    basePane = new GridPane();
    basePane.getStyleClass().add(HISTORY_DISPLAY_PANE_ID);
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
    historyPane = new ScrollPane();
    historyPane.setFitToWidth(true);
    historyPane.setFitToHeight(true);
    historyPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    historyPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    HBox.setHgrow(historyPane, Priority.ALWAYS);
    historyPane.getStyleClass().add(HISTORY_PANE_ID);
    basePane.add(historyPane, 0, 1);
  }

  private void displayTitle() {
    Text title = new Text(TITLE);
    title.getStyleClass().add(HISTORY_DISPLAY_PANE_TEXT);
    basePane.add(title, 0, 0);
  }

  public GridPane getBox() {
    return basePane;
  }

  public void updateCommandHistory(List<String> commandHistory) {
    int count = 1;
    for(String command : commandHistory){
      Label label = new Label(command, new Rectangle(50, 50));
      basePane.add(label,0,count);
      count++;
    }
  }
}
