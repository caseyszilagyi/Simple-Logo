package slogo.view;

import java.lang.reflect.Method;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import slogo.controller.FrontEndExternalAPI;

public class TurtleDisplayPane implements FrontEndInternalAPI{
  private static final double TURTLE_WIDTH = 50;
  private static final double TURTLE_HEIGHT = 50;
  private static final String DEFAULT_RESOURCES = TurtleDisplayPane.class.getPackageName() + ".resources.";
  public static final String UPDATE_NEXT_RESOURCE = DEFAULT_RESOURCES + "UpdateNextReflectionActions";
  private static final String ERROR_LANGUAGE = DEFAULT_RESOURCES + ".errormessages.Error";

  private static final String PANE_BOX_ID = "TurtleView";
  private static final String LINE_ID = "Line";
  private final double centerX;
  private final double centerY;
  private GridPane viewPane;
  private AnchorPane turtleViewPane;
  private double penUP = 1;
  double x;
  double y;
  private Paint penColor;
  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private int INCREMENT_FACTOR = 10;
  private double rows;
  private double cols;
  private Map<Integer, FrontEndSprite> allTurtleInformation;
  private int FIRST_TURTLE = 1;
  private int currentID = 1;
  private FrontEndExternalAPI viewController;
  private String cS = "clearscreen";
  private ResourceBundle errorLanguageResource;

  String turtleImageFile = "Turtle2.gif";
  String inactiveTurtleImageFile = "Turtle3.gif";

  public TurtleDisplayPane(FrontEndExternalAPI viewController,GridPane root, double r, double c) {
    this.viewController = viewController;
    viewPane = root;
    rows = r;
    cols = c;

    turtleViewPane = new AnchorPane();
    viewPane.add(turtleViewPane, 0, 1);
    turtleViewPane.setId(PANE_BOX_ID);
    turtleViewPane.getStyleClass().add(PANE_BOX_ID);
    String lang = "English";
    errorLanguageResource = ResourceBundle.getBundle(ERROR_LANGUAGE + lang);

    turtleViewPane.setMaxHeight(cols);
    turtleViewPane.setMaxWidth(rows);
    turtleViewPane.setMinHeight(cols);
    turtleViewPane.setMinWidth(rows);

    centerX = rows / 2 - TURTLE_HEIGHT / 2;
    centerY = cols / 2 - TURTLE_WIDTH / 2;

    commandsToBeExecuted = new ArrayDeque<>();
    typeToBeUpdated = new ArrayDeque<>();
    allTurtleInformation = new HashMap<>();

    createTurtle(FIRST_TURTLE);
  }

  @Override
  public void updateTurtlePositions() {
    String key;
    ResourceBundle updateNextActionResources = ResourceBundle.getBundle(UPDATE_NEXT_RESOURCE);
    if(!typeToBeUpdated.isEmpty()) {
      key = typeToBeUpdated.removeFirst();
      try {
        String methodName = updateNextActionResources.getString(key);
        Method m = TurtleDisplayPane.this.getClass().getDeclaredMethod(methodName);
        m.invoke(TurtleDisplayPane.this);
      }
      catch (Exception e) {
        new Alert(Alert.AlertType.ERROR);
      }
    }
  }

  private void updatePosition() {
    double nextX = commandsToBeExecuted.pop();
    double nextY = commandsToBeExecuted.pop();

    if(nextY < 0 || nextX < 0 || nextY > cols - TURTLE_HEIGHT || nextX > rows - TURTLE_WIDTH){
      Alert error = new Alert(AlertType.ERROR);
      error.setContentText(errorLanguageResource.getString("TurtleOutOfBounds"));
      nextX = centerX;
      nextY = centerY;
      error.show();
      viewController.processUserCommandInput(cS);
    }

    if (allTurtleInformation.get(currentID).getPenState() == 1) {
      createLine(nextX, nextY, penColor);
    }
    allTurtleInformation.get(currentID).getTurtle().setX(nextX);
    allTurtleInformation.get(currentID).getTurtle().setY(nextY);
  }

  private void clearQueue() {
    commandsToBeExecuted.clear();
    typeToBeUpdated.clear();
  }

  private void updateAngles() {
    allTurtleInformation.get(currentID).getTurtle().setRotate(90 - commandsToBeExecuted.pop());
  }

  private void updatePen() {
    allTurtleInformation.get(currentID).setPenState(commandsToBeExecuted.removeFirst());
  }

  private void updateVisibility() {
    allTurtleInformation.get(currentID).getTurtle().setVisible(commandsToBeExecuted.removeFirst() == 1);
  }

  private void setID() {
    currentID = (int) Math.round(commandsToBeExecuted.pop());
  }

  private void updateTurtleImages() {

  }

  private void setPenThickness(){
    allTurtleInformation.get(currentID).setPenThickness(commandsToBeExecuted.removeFirst());
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

  public void moveTurtle(double xCoordinate, double yCoordinate, Paint penColor) {
    this.penColor = penColor;

    x = turtleViewPane.getWidth() / 2 + xCoordinate * turtleViewPane.getWidth() / rows - TURTLE_WIDTH / 2;
    y = turtleViewPane.getHeight() / 2 - yCoordinate * turtleViewPane.getHeight() / cols - TURTLE_HEIGHT / 2;

    double xIncrement = (x - allTurtleInformation.get(currentID).getxCoord())/ INCREMENT_FACTOR;
    double yIncrement = (y - allTurtleInformation.get(currentID).getyCoord())/ INCREMENT_FACTOR;

    for(int i = 1; i <= INCREMENT_FACTOR; i++){
      commandsToBeExecuted.add(allTurtleInformation.get(currentID).getxCoord() + xIncrement * i);
      commandsToBeExecuted.add(allTurtleInformation.get(currentID).getyCoord() + yIncrement * i);
      typeToBeUpdated.add("Positions");
    }

    allTurtleInformation.get(currentID).setxCoord(x);
    allTurtleInformation.get(currentID).setyCoord(y);
  }

  private void createLine(double x, double y, Paint penColor) {
    Line line1 = new Line(allTurtleInformation.get(currentID).getTurtle().getX() + TURTLE_WIDTH / 2, allTurtleInformation.get(currentID).getTurtle().getY() + TURTLE_WIDTH / 2,
            x + TURTLE_HEIGHT / 2, y + TURTLE_HEIGHT / 2);
    line1.setStroke(penColor);
    line1.setId(LINE_ID);
    line1.setStrokeWidth(allTurtleInformation.get(currentID).getPenThickness());
    turtleViewPane.getChildren().add(line1);
  }

  public void clearScreen() {
    commandsToBeExecuted.clear();
    typeToBeUpdated.clear();

    turtleViewPane.getChildren().clear();
    for(Map.Entry<Integer, FrontEndSprite> entry : allTurtleInformation.entrySet()){
      createTurtle(entry.getKey());
    }

  }

  public void setBackground(Background background) {
    turtleViewPane.setBackground(background);
  }

  public void setTurtleImage(Image turtleImage) {
    allTurtleInformation.get(currentID).getTurtle().setImage(turtleImage);
    allTurtleInformation.get(currentID).getTurtle().setFitWidth(TURTLE_WIDTH);
    allTurtleInformation.get(currentID).getTurtle().setFitHeight(TURTLE_HEIGHT);
    allTurtleInformation.get(currentID).getTurtle().setId("Turtle" + currentID);
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    typeToBeUpdated.add(commandType);
    commandsToBeExecuted.addAll(commandValues);
  }

  public void updateLanguage(String lang) {
    errorLanguageResource = ResourceBundle.getBundle(ERROR_LANGUAGE + lang);
  }

  public void setActiveTurtle(int turtleID) {
    if(!allTurtleInformation.containsKey(turtleID)){
      createTurtle(turtleID);
    }

    currentID = turtleID;
    commandsToBeExecuted.add((double) turtleID);
    typeToBeUpdated.add("SetID");

  }

  public void setActiveTurtles(List<Integer> iDs) {
    for(Integer turtleID : allTurtleInformation.keySet().toArray(new Integer[0])){
      if(iDs.contains(turtleID)){
        allTurtleInformation.get(turtleID).getTurtle().setImage(new Image(turtleImageFile));
      }else{
        allTurtleInformation.get(turtleID).getTurtle().setImage(new Image(inactiveTurtleImageFile));
      }
    }
  }
}