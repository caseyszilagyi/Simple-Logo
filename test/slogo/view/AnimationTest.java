package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slogo.Main;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ViewController;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class AnimationTest extends DukeApplicationTest {
  private Main main;
  private FrontEndTurtle frontEndTurtle;
  private ScreenCreator screenCreator;
  private ViewPane viewPane;
  private TurtleDisplayPane turtleDisplayPane;
  private FrontEndExternalAPI viewController;
  private BackEndExternalAPI backEndExternalAPI;
  private ResourceBundle idResource;

  @Override
  public void start(Stage stage) throws Exception {
    main = new Main();
    main.start(stage);

    main = new Main();
    main.start(stage);
    viewController = new ViewController();
    backEndExternalAPI = new DummyModelController();
    viewController.setModelController(backEndExternalAPI);
    screenCreator = new ScreenCreator(viewController);
    idResource = ResourceBundle.getBundle("IDsforTesting");
    viewPane = new ViewPane(viewController, stage, idResource);
    turtleDisplayPane = new TurtleDisplayPane()
  }

}
