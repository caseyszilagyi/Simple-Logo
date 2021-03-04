package slogo.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Create the main screen where visuals and panes will be displayed
 */
public class ScreenCreator {
  public static final String TITLE = "SLogo";

  private BorderPane myRoot;
  private Scene myScene;
  private Stage myStage;

  public ScreenCreator() {
    myStage = new Stage();
    myStage.setResizable(true);

    myRoot = new BorderPane();
    myScene = new Scene(myRoot);
    myStage.setScene(myScene);
    myStage.setTitle(TITLE);
    myStage.show();
  }
}
