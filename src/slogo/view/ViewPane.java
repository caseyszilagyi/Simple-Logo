package slogo.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.controller.ViewController;

/**
 * Creates the view for where the turtle will be displayed
 *
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class ViewPane {

  private static final double TURTLE_WIDTH = 50;
  private static final double TURTLE_HEIGHT = 50;

  private static final double ICON_WIDTH = 20.0;
  private static final double ICON_HEIGHT = 20.0;

  private static final String BACKGROUND_ICON = "BackgroundIcon.gif";
  private static final String PEN_ICON = "PenIcon.gif";
  private static final String TURTLE_ICON = "TurtleIcon.gif";
  private static final String NEW_WINDOW_ICON = "NewWindow.gif";

  private static final int rows = 700;
  private static final int cols = 600;

  private static final String VIEW_PANE_ID = "ViewPane";
  private static final String PANE_BOX_ID = "TurtleView";
  private static final String CHOICE_PANE_ID = "ChoicePane";
  private static final String BACKGROUND_COLOR_PICKER_ID = "BackgroundColorPicker";
  private static final String PEN_COLOR_PICKER_ID = "PenColorPicker";
  private static final String COLOR_PICKER = "color-picker";
  private static final String ICON = "icon";
  private static final String BUTTON = "regular-button";

  private static final String LANGUAGE_OPTIONS = "slogo.model.resources.languages.LangaugeOptions";
  private final double centerX;
  private final double centerY;
  private BorderPane viewPane;
  private AnchorPane turtleViewPane;
  private ImageView turtle;
  private GridPane choicePane;
  private Image turtleImage;
  private ColorPicker penColorPicker;
  private ColorPicker backgroundColorPicker;
  private Stage stage;
  private Button backgroundColorPickerButton;
  private Button penColorPickerButton;
  private ComboBox languages;
  private ResourceBundle languageOptions;
  private String language;
  private boolean penUP = false;
  private Color penColor = Color.BLACK;
  private String turtleImageFile = "Turtle2.gif";
  private Color currentPenColor = Color.BLACK;
  private String currentBackgroundColor = "d3d3d3";

  public ViewPane(Stage s) {
    stage = s;
    viewPane = new BorderPane();
//
//    viewPane.setMaxHeight(200);
//    viewPane.setMaxWidth(200);

    viewPane.setId(VIEW_PANE_ID);
    viewPane.getStyleClass().add(VIEW_PANE_ID);
    turtleViewPane = new AnchorPane();
    viewPane.setCenter(turtleViewPane);
    turtleViewPane.setId(PANE_BOX_ID);
    turtleViewPane.getStyleClass().add(PANE_BOX_ID);

    //set size of the ViewPane
    turtleViewPane.setMaxHeight(cols);
    turtleViewPane.setMaxWidth(rows);
    turtleViewPane.setMinHeight(cols);
    turtleViewPane.setMinWidth(rows);

    //Get the center
    centerX = rows / 2 - TURTLE_HEIGHT / 2;
    centerY = cols / 2 - TURTLE_WIDTH / 2;

    createChoicePane();
    viewPane.setTop(choicePane);
    createTurtle();

    System.out.println("Height of TurtlePane 2: " + turtleViewPane.getHeight());
  }

  private void createChoicePane() {
    choicePane = new GridPane();
    choicePane.getStyleClass().add(CHOICE_PANE_ID);

    createBackgroundColorPicker();

    createPenColorPicker();

    Button turtleImageButton = new Button();
    ImageView turtleIcon = setIcon(TURTLE_ICON);
    turtleImageButton.setGraphic(turtleIcon);
    turtleImageButton.getStyleClass().add(ICON);
    choicePane.add(turtleImageButton, 2, 0);
    turtleImageButton.setOnAction(event -> uploadTurtleImage());

    createLanguageComboBox();

    Button addNewScreen = new Button();
    ImageView addIcon = setIcon(NEW_WINDOW_ICON);
    addNewScreen.setGraphic(addIcon);
    addNewScreen.getStyleClass().add(ICON);
    choicePane.add(addNewScreen, 3, 0);
    addNewScreen.setOnAction(event -> createNewScreen());
  }

  private void createNewScreen() {
    FrontEndExternalAPI viewController = new ViewController();
    BackEndExternalAPI modelController = new ModelController();
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);
  }

  private void createLanguageComboBox() {
    languageOptions = ResourceBundle.getBundle(LANGUAGE_OPTIONS);
    String defaultLanguage = languageOptions.getString("English");
//    ArrayList<String> allLanguages = new ArrayList<>() {{
//      add("Chinese");
//      add("English");
//      add("French");
//      add("German");
//      add("Italian");
//      add("Portuguese");
//      add("Russian");
//      add("Spanish");
//      add("Urdu");
//    }};
    Object[] allLanguages = languageOptions.keySet().toArray();
    languages = new ComboBox();
    for (int i = 0; i < allLanguages.length; i ++) {
      languages.getItems().add(languageOptions.getString(allLanguages[i].toString()));
    }
//
//    ArrayList<String> allLanguageDisplay = new ArrayList<>() {{
//      add(languageOptions.getString("Chinese"));
//      add(languageOptions.getString("English"));
//      add(languageOptions.getString("French"));
//      add(languageOptions.getString("German"));
//      add(languageOptions.getString("Italian"));
//      add(languageOptions.getString("Portuguese"));
//      add(languageOptions.getString("Russian"));
//      add(languageOptions.getString("Spanish"));
//      add(languageOptions.getString("Urdu"));
//    }};
//    languages = new ComboBox();
//    languages.getItems().addAll(allLanguageDisplay);
    languages.setValue(defaultLanguage);
    language = "English";
    choicePane.add(languages, 10, 0);
//    languages.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        int value = allLanguageDisplay.indexOf(languages.getValue());
//        language = allLanguages.(value);
//      }
//    });
  }

  public String getLanguage() {
    return language;
  }

  private void createPenColorPicker() {
    penColorPickerButton = new Button();
    ImageView icon = setIcon(PEN_ICON);
    penColorPickerButton.setGraphic(icon);
    penColorPickerButton.getStyleClass().add(ICON);
    choicePane.add(penColorPickerButton, 1, 0);
    penColorPickerButton.setOnAction(event -> changePenButton());
  }

  private void changePenButton() {
    choicePane.getChildren().remove(penColorPickerButton);
    penColorPicker = new ColorPicker(currentPenColor);
    penColorPicker.setId(PEN_COLOR_PICKER_ID);
    penColorPicker.getStyleClass().add(COLOR_PICKER);
    choicePane.add(penColorPicker, 1, 0);
    penColorPicker.setOnAction(event -> changePenColor());
  }

  private void changePenColor() {
    penColor = penColorPicker.getValue();
    currentPenColor = penColor;
    choicePane.getChildren().remove(penColorPicker);
    choicePane.add(penColorPickerButton, 1, 0);
  }

  private void createBackgroundColorPicker() {
    backgroundColorPickerButton = new Button();
    ImageView icon = setIcon(BACKGROUND_ICON);
    backgroundColorPickerButton.setGraphic(icon);
    backgroundColorPickerButton.setOnAction(event -> changeBackgroundButton());
    backgroundColorPickerButton.getStyleClass().add(ICON);
    choicePane.add(backgroundColorPickerButton, 0, 0);
  }

  private ImageView setIcon(String icon) {
    ImageView iconView = new ImageView(new Image(icon));
    iconView.setFitWidth(ICON_WIDTH);
    iconView.setFitHeight(ICON_HEIGHT);
    return iconView;
  }

  private void changeBackgroundButton() {
    choicePane.getChildren().remove(backgroundColorPickerButton);
    backgroundColorPicker = new ColorPicker(Color.valueOf(currentBackgroundColor));
    backgroundColorPicker.setId(BACKGROUND_COLOR_PICKER_ID);
    backgroundColorPicker.getStyleClass().add(COLOR_PICKER);
    choicePane.add(backgroundColorPicker, 0, 0);
    backgroundColorPicker.setOnAction(event -> changeBackgroundColor());
  }

  private void changeBackgroundColor() {
    Paint fill = backgroundColorPicker.getValue();
    currentBackgroundColor = fill.toString();
    BackgroundFill backgroundFill =
        new BackgroundFill(fill,
            new CornerRadii(10),
            new Insets(10));
    Background background = new Background(backgroundFill);
    turtleViewPane.setBackground(background);
    choicePane.getChildren().remove(backgroundColorPicker);
    choicePane.add(backgroundColorPickerButton, 0, 0);
  }

  // TODO: check for inappropriate file type (not .gif)
  private void uploadTurtleImage() {
    FileChooser turtleImageChooser = new FileChooser();
    File file = turtleImageChooser.showOpenDialog(stage);
    turtleImageFile = file.toURI().toString();
    turtleImage = new Image(turtleImageFile);
    turtle.setImage(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
  }

  private void createTurtle() {
    turtleImage = new Image(turtleImageFile);
    turtle = new ImageView(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtleViewPane.getChildren().add(turtle);

    System.out.println("Height of TurtlePane 3: " + turtleViewPane.getHeight());
    System.out.println("CenterX" + centerX);
    turtle.setX(centerX);
    turtle.setY(centerY);
  }

  public void moveTurtle(double xCoordinate, double yCoordinate) {

    double turtleCenterX = TURTLE_WIDTH / 2;
    double turtleCenterY = TURTLE_HEIGHT / 2;

    double screenWidth = turtleViewPane.getWidth();
    double screenHeight = turtleViewPane.getHeight();

    double coordinateWidth = screenWidth / rows;
    double coordinateHeight = screenHeight / cols;

    double x = screenWidth / 2 + xCoordinate * coordinateWidth - turtleCenterX;
    double y = screenHeight / 2 - yCoordinate * coordinateHeight - turtleCenterY;

    if (!penUP) {
      createLine(x, y);
    }

    turtle.setX(x);
    turtle.setY(y);

    System.out.println("x center: " + x);
    System.out.println("y center: " + y);
  }

  private void createLine(double x, double y) {
    Line line1 = new Line(turtle.getX() + TURTLE_WIDTH / 2, turtle.getY() + TURTLE_WIDTH / 2,
        x + TURTLE_HEIGHT / 2, y + TURTLE_HEIGHT / 2);
    line1.setStroke(penColor);
    turtleViewPane.getChildren().add(line1);
  }

  public BorderPane getBox() {
    return viewPane;
  }

  public void setPenDown() {
    penUP = false;
  }

  //These magic index values need to be processed in some other way
  //Current set up for these parameters is not SHY enough since we have to have
  // prior knowledge about the order of these parameters
  public void updateTurtle(List<Double> parameters) {
    System.out.println("parameters: " + parameters);
    moveTurtle(parameters.get(0), parameters.get(1));
    turtle.setRotate(90 - parameters.get(2));
    if (parameters.get(3) == 1) {
      setPenDown();
    } else {
      setPenUp();
    }
    if (parameters.get(4) == 1) {
      turtle.setVisible(true);
    } else {
      turtle.setVisible(false);
    }
    if (parameters.get(5) == 1) {
      turtleViewPane.getChildren().clear();
      createTurtle();
    }
  }

  private void setPenUp() {
    penUP = true;
  }

  public void moveTurtleByDistance(double distance) {
    // do the calculations to make the turtle go forward
    // THIS WAS WAY HARDER THAN I THOGUGHT
    // because the angles/getrotate are all messed up
    double turtleX;
    double turtleY;
    double turtleAngle = ((-turtle.getRotate() - 90) * Math.PI) / (180);
    turtleX = turtle.getX() - Math.cos(turtleAngle) * distance;
    turtleY = turtle.getY() + Math.sin(turtleAngle) * distance;
    if (!penUP) {
      createLine(turtleX, turtleY);
    }

    turtle.setX(turtleX);
    turtle.setY(turtleY);

  }

}