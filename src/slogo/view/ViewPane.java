package slogo.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
  private static final double ICON_WIDTH = 20.0;
  private static final double ICON_HEIGHT = 20.0;

  private static final String BACKGROUND_ICON = "BackgroundIcon.gif";
  private static final String PEN_ICON = "PenIcon.gif";
  private static final String TURTLE_ICON = "TurtleIcon.gif";
  private static final String NEW_WINDOW_ICON = "NewWindow.gif";

  private static final String VIEW_PANE_ID = "ViewPane";
  private static final String CHOICE_PANE_ID = "ChoicePane";
  private static final String BACKGROUND_COLOR_PICKER_ID = "BackgroundColorPicker";
  private static final String PEN_COLOR_PICKER_ID = "PenColorPicker";
  private static final String COLOR_PICKER = "color-picker";
  private static final String ICON = "icon";
  private static final String BUTTON = "regular-button";

  private static final String LANGUAGE_OPTIONS = "slogo.model.resources.languages.LangaugeOptions";
  private BorderPane viewPane;
  private GridPane choicePane;
  private ColorPicker penColorPicker;
  private ColorPicker backgroundColorPicker;
  private Stage stage;
  private Button backgroundColorPickerButton;
  private Button penColorPickerButton;
  private ComboBox<String> languages;
  private ResourceBundle languageOptions;
  private String language;
  private Color penColor = Color.BLACK;
  private Color currentPenColor = Color.BLACK;
  private String currentBackgroundColor = "d3d3d3";
  private TurtleDisplayPane turtleDisplay;

  public ViewPane(Stage s) {
    stage = s;
    viewPane = new BorderPane();
    viewPane.setId(VIEW_PANE_ID);
    viewPane.getStyleClass().add(VIEW_PANE_ID);

    turtleDisplay = new TurtleDisplayPane(viewPane);

    createChoicePane();
    viewPane.setTop(choicePane);
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
    Object[] allLanguages = languageOptions.keySet().toArray();
    List<String> displayLanguages = new ArrayList<>();
    languages = new ComboBox<>();
    for (int i = 0; i < allLanguages.length; i ++) {
      displayLanguages.add(languageOptions.getString(allLanguages[i].toString()));
    }
    languages.getItems().addAll(displayLanguages);
    languages.setValue(defaultLanguage);
    language = "English";
    choicePane.add(languages, 10, 0);
    languages.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int value = displayLanguages.indexOf(languages.getValue());
        language = allLanguages[value].toString();
        System.out.println(language);
      }
    });
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
    turtleDisplay.setBackground(background);
    choicePane.getChildren().remove(backgroundColorPicker);
    choicePane.add(backgroundColorPickerButton, 0, 0);
  }

  // TODO: check for inappropriate file type (not .gif)
  private void uploadTurtleImage() {
    /*FileChooser turtleImageChooser = new FileChooser();
    File file = turtleImageChooser.showOpenDialog(stage);
    turtleImageFile = file.toURI().toString();
    turtleImage = new Image(turtleImageFile);
    turtle.setImage(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);*/
  }

  public void moveTurtle(double xCoordinate, double yCoordinate) {
    turtleDisplay.moveTurtle(xCoordinate, yCoordinate, penColor);
  }

  public BorderPane getBox() {
    return viewPane;
  }

  //These magic index values need to be processed in some other way
  //Current set up for these parameters is not SHY enough since we have to have
  // prior knowledge about the order of these parameters
  public void updateTurtle(List<Double> parameters) {
    System.out.println("parameters: " + parameters);
    moveTurtle(parameters.get(0), parameters.get(1));
    turtleDisplay.updateTurtle(parameters);
  }

/*  public void moveTurtleByDistance(double distance) {
    // do the calculations to make the turtle go forward
    // THIS WAS WAY HARDER THAN I THOGUGHT
    // because the angles/getrotate are all messed up
    double turtleX;
    double turtleY;
    double turtleAngle = ((-turtle.getRotate() - 90) * Math.PI) / (180);
    turtleX = turtle.getX() - Math.cos(turtleAngle) * distance;
    turtleY = turtle.getY() + Math.sin(turtleAngle) * distance;
    if (!penUP) {
      //createLine(turtleX, turtleY);
    }

    turtle.setX(turtleX);
    turtle.setY(turtleY);

  }*/

}