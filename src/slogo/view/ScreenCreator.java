package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.controller.ViewController;

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
  private ViewController viewController;
  private VBox buttonBox;

  public ScreenCreator(ViewController viewController) {
    this.viewController = viewController;
    stage = new Stage();
    stage.setResizable(true);

    root = new BorderPane();
    scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();

    possibleCommandPane = new PossibleCommandPane();
    root.setRight(possibleCommandPane.getBox());

    userCommand = new UserCommandPane(viewController);
    root.setBottom(userCommand.getBox());

    viewPane = new ViewPane();
    root.setCenter(viewPane.getBox());

    // TODO: remove later (testing)
    // For testing for now
    buttonBox = new VBox();
    buttonBox.setAlignment(Pos.TOP_CENTER);
    buttonBox.setSpacing(5.0);
    root.setLeft(buttonBox);
    addTitle();
    createButtons();
  }

  // TODO: remove later (testing)
  private void addTitle() {
    Text title = new Text("Testing points");
    buttonBox.getChildren().add(title);
  }

  // TODO: remove later (testing)
  private void createButtons() {
    Button centerButton = buttonCreation("(0.0, 0.0)");
    centerButton.setOnAction(event -> viewPane.moveTurtle(0, 0));
    Button planeI = buttonCreation("(3.0, 4.0)");
    planeI.setOnAction(event -> viewPane.moveTurtle(3.0, 4.0));
    Button planeII = buttonCreation("(-3.0, 4.0)");
    planeII.setOnAction(event -> viewPane.moveTurtle(-3.0, 4.0));
    Button planeIII = buttonCreation("(-3.0, -4.0)");
    planeIII.setOnAction(event -> viewPane.moveTurtle(-3.0, -4.0));
    Button planeIV = buttonCreation("(3.0, -4.0)");
    planeIV.setOnAction(event -> viewPane.moveTurtle(3.0, -4.0));
    Button planeV = buttonCreation("Turn 90 degrees");
    planeV.setOnAction(event -> viewPane.turnTurtle(90));
    Button planeVI = buttonCreation("Turn 45 degrees");
    planeVI.setOnAction(event -> viewPane.turnTurtle(45));
  }

  // TODO: remove later (testing)
  private Button buttonCreation(String text) {
    Button button = new Button(text);
    buttonBox.getChildren().add(button);
    return button;
  }
}
