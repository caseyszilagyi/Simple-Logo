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

  private BorderPane myRoot;
  private Scene myScene;
  private Stage myStage;
  private PossibleCommandPane myPossibleCommandPane;
  private UserCommandPane myUserCommandPane;
  private ViewPane myViewPane;

  public ScreenCreator() {
    myStage = new Stage();
    myStage.setResizable(true);

    myRoot = new BorderPane();
    myScene = new Scene(myRoot, DEFAULT_X, DEFAULT_Y);
    myStage.setScene(myScene);
    myStage.setTitle(TITLE);
    myStage.show();

    myPossibleCommandPane = new PossibleCommandPane();
    myRoot.setRight(myPossibleCommandPane.getBox());

    myUserCommandPane = new UserCommandPane();
    myRoot.setBottom(myUserCommandPane.getBox());

    myViewPane = new ViewPane();
    myRoot.setCenter(myViewPane.getBox());
  }
}