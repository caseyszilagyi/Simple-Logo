package slogo.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slogo.Main;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ViewController;
import util.DukeApplicationTest;

public class AnimationTest extends DukeApplicationTest {
  private Main main;
  private FrontEndTurtle frontEndTurtle;
  private ScreenCreator screenCreator;
  private ViewPane viewPane;
  private TurtleDisplayPane turtleDisplayPane;
  private FrontEndExternalAPI viewController;
  private BackEndExternalAPI backEndExternalAPI;
  private ResourceBundle idResource;
  private GridPane gridPane;
  private Map<Integer, FrontEndSprite> allTurtleInformation;
  private static final double TURTLE_WIDTH = 50;
  private static final double TURTLE_HEIGHT = 50;
  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private int currentID;

  private double penUP = 1;

  private AnchorPane turtleViewPane;

  private double centerX;
  private double centerY;

  String turtleImageFile = "Turtle2.gif";

  @BeforeEach
  void setUp(){
    createTurtle(1);
  }
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
    gridPane = new GridPane();
    turtleDisplayPane = new TurtleDisplayPane(viewController, gridPane, 700, 600);
    allTurtleInformation = new HashMap<>();
    turtleViewPane = new AnchorPane();
    commandsToBeExecuted = new ArrayDeque<>();
    typeToBeUpdated = new ArrayDeque<>();

  }

  @Test
  void testMoveTurtle(){
    double xCoord = 0;
    double yCoord = 0;
    turtleDisplayPane.moveTurtle(0,0,Color.BLUE);
    assertEquals(xCoord, 0);
    assertEquals(yCoord, 0);
  }

  @Test
  void testUpdateTurtlePositions(){
    turtleDisplayPane.updateTurtleStates();
  }

  @Test
  void testClearScreen(){
    commandsToBeExecuted.add(2.0);
    typeToBeUpdated.add("Position");
    turtleDisplayPane.clearScreen();
    clearScreen();
    assert(commandsToBeExecuted.isEmpty());
    assert(typeToBeUpdated.isEmpty());
  }

  @Test
  void testSetTurtleImage(){
    turtleDisplayPane.setTurtleImage(new Image("Turtle2.gif"));
  }

  @Test
  void testUpdateCommandQueue(){
    List<Double> commandValues = new ArrayList<>();
    commandValues.add(1.0);
    commandValues.add(2.0);
    turtleDisplayPane.updateTurtleStates();
    turtleDisplayPane.updateCommandQueue("Positions", commandValues);
    updateCommandQueue("Positions", commandValues);
    assertEquals(typeToBeUpdated.pop(), "Positions");

  }

  @Test
  void testSetActiveTurtle(){
    setActiveTurtle(2);
    turtleDisplayPane.setActiveTurtle(2);
    assertEquals(currentID, 2);
  }

  private void createTurtle(int id) {

    Image turtleImage = new Image(turtleImageFile);
    String imageID = "Turtle" + id;
    ImageView turtle = new ImageView(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtle.setId(imageID);
    turtleViewPane.getChildren().add(turtle);

    turtle.setX(centerX);
    turtle.setY(centerY);
    turtle.setRotate(0);

    FrontEndSprite turtleInformation = new FrontEndTurtle(centerX, centerY, turtle, penUP);

    this.allTurtleInformation.put(id, turtleInformation);

  }

  public void clearScreen() {
    commandsToBeExecuted.clear();
    typeToBeUpdated.clear();

    turtleViewPane.getChildren().clear();
    for(Map.Entry<Integer, FrontEndSprite> entry : allTurtleInformation.entrySet()){
      createTurtle(entry.getKey());
    }

  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    typeToBeUpdated.add(commandType);
    commandsToBeExecuted.addAll(commandValues);

  }

  public void setActiveTurtle(int turtleID) {
    if(!allTurtleInformation.containsKey(turtleID)){
      createTurtle(turtleID);
    }

    currentID = turtleID;
    commandsToBeExecuted.add((double) turtleID);
    typeToBeUpdated.add("SetID");

  }

}
