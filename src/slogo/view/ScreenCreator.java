package slogo.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Create the main screen where visuals and panes will be displayed
 */
public class ScreenCreator {
  public static final String TITLE = "SLogo";
  public static final double DEFAULT_X = 1000.0;
  public static final double DEFAULT_Y = 600.0;

  private BorderPane root;
  private Scene scene;
  private Stage stage;
  private PossibleCommandPane possibleCommandPane;
  private UserCommandPane userCommand;
  private ViewPane viewPane;

  public ScreenCreator() {
    stage = new Stage();
    stage.setResizable(true);

    root = new BorderPane();
    scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    possibleCommandPane = new PossibleCommandPane();
    root.setRight(possibleCommandPane.getBox());

    userCommand = new UserCommandPane();
    root.setBottom(userCommand.getBox());

    viewPane = new ViewPane();
    root.setCenter(viewPane.getBox());
  }
}
