package slogo.view;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.controller.ViewController;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewChoicePane {
  private static final double ICON_WIDTH = 20.0;
  private static final double ICON_HEIGHT = 20.0;

  private static final String CHOICE_PANE_ID = "ChoicePane";
  private static final String COLOR_PICKER = "color-picker";
  private static final String ICON = "icon";
  private static final String DEFAULT_RESOURCES = HistoryDisplayPane.class.getPackageName() + ".resources.";
  private static final String LANGUAGE_OPTIONS = DEFAULT_RESOURCES + "buttons.languages.LangaugeOptions";
  private static final String IMAGE_RESOURCES = DEFAULT_RESOURCES + "Image";
  private static final String REFLECTION_RESOURCES = DEFAULT_RESOURCES + "buttons.ViewChoiceReflectionActions";

  private GridPane choicePane;
  private BorderPane viewPane;
  private ColorPicker penColorPicker;
  private ColorPicker backgroundColorPicker;
  private Stage stage;
  private Button backgroundColorPickerButton;
  private Button penColorPickerButton;
  private ComboBox<String> languageComboBox;
  private String language;
  private Color penColor = Color.BLACK;
  private Color currentPenColor = Color.BLACK;
  private String currentBackgroundColor = "d3d3d3";
  private TurtleDisplayPane turtleDisplay;
  private ResourceBundle idsForTesting;
  private ResourceBundle imageResources;
  private ResourceBundle reflectionResources;
  private FrontEndExternalAPI viewController;

  public ViewChoicePane(FrontEndExternalAPI viewController, Stage s, BorderPane root, TurtleDisplayPane turtleDisplayPane, ResourceBundle idResources) {
    this.viewController = viewController;
    stage = s;
    viewPane = root;
    turtleDisplay = turtleDisplayPane;

    choicePane = new GridPane();
    choicePane.getStyleClass().add(CHOICE_PANE_ID);
    viewPane.setTop(choicePane);

    idsForTesting = idResources;
    imageResources = ResourceBundle.getBundle(IMAGE_RESOURCES);
    reflectionResources = ResourceBundle.getBundle(REFLECTION_RESOURCES);

    createBackgroundButton();
    createPenButton();
    createTurtleImageButton();
    createNewWindowButton();
    createLanguageComboBox();
  }

  private void createBackgroundButton() {
    String key = "ChangeBackgroundButton";
    backgroundColorPickerButton = makeButton(key, 0);
  }

  private void changeBackgroundButton() {
    choicePane.getChildren().remove(backgroundColorPickerButton);
    String key = "ChangeBackgroundColorPicker";
    backgroundColorPicker = makeColorPicker(key, 0, Color.valueOf(currentBackgroundColor));
  }

  private void changeBackgroundColor() {
    currentBackgroundColor = backgroundColorPicker.getValue().toString();
    BackgroundFill backgroundFill = new BackgroundFill(backgroundColorPicker.getValue(),
            new CornerRadii(10), new Insets(10));
    Background background = new Background(backgroundFill);
    turtleDisplay.setBackground(background);
    choicePane.getChildren().remove(backgroundColorPicker);
    choicePane.add(backgroundColorPickerButton, 0, 0);
  }

  private void createPenButton() {
    String key = "ChangePenButton";
    penColorPickerButton = makeButton(key, 1);
  }

  private void changePenButton() {
    choicePane.getChildren().remove(penColorPickerButton);
    String key = "ChangePenColorPicker";
    penColorPicker = makeColorPicker(key, 1, currentPenColor);
  }

  private void changePenColor() {
    penColor = penColorPicker.getValue();
    currentPenColor = penColor;
    choicePane.getChildren().remove(penColorPicker);
    choicePane.add(penColorPickerButton, 1, 0);
  }

  public Color getPenColor() {
    return penColor;
  }

  private void createTurtleImageButton() {
    String key = "TurtleIconButton";
    makeButton(key, 2);
  }

  public void uploadTurtleImage() {
    FileChooser turtleImageChooser = new FileChooser();
    File file = turtleImageChooser.showOpenDialog(stage);
    String turtleImageFile = file.toURI().toString();
    Image turtleImage = new Image(turtleImageFile);
    turtleDisplay.setTurtleImage(turtleImage);
  }

  private void createNewWindowButton() {
    String key = "NewWindowButton";
    makeButton(key, 3);
  }

  private void createNewScreen() {
    FrontEndExternalAPI viewController = new ViewController();
    BackEndExternalAPI modelController = new ModelController();
    viewController.setModelController(modelController);
    modelController.setViewController(viewController);
  }

  private void createLanguageComboBox() {
    ResourceBundle languageOptions = ResourceBundle.getBundle(LANGUAGE_OPTIONS);
    Object[] allLanguages = languageOptions.keySet().toArray();
    List<String> displayLanguages = new ArrayList<>();
    for (Object allLanguage : allLanguages) {
      displayLanguages.add(languageOptions.getString(allLanguage.toString()));
    }
    languageComboBox = new ComboBox<>();
    languageComboBox.getItems().addAll(displayLanguages);
    languageComboBox.setId(idsForTesting.getString("LanguageComboBox"));
    language = "English";
    languageComboBox.setValue(languageOptions.getString(language));
    choicePane.add(languageComboBox, 10, 0);
    languageComboBox.setOnAction(handler -> {
      int value = displayLanguages.indexOf(languageComboBox.getValue());
      language = allLanguages[value].toString();
      viewController.setLanguage(language);
    });
  }

  private Button makeButton(String key, int col) {
    Button button = new Button();
    button.setGraphic(setIcon(imageResources.getString(key)));
    button.getStyleClass().add(ICON);
    button.setId(idsForTesting.getString(key));
    button.setOnAction(event -> reflectionMethod(key));
    choicePane.add(button, col, 0);
    return button;
  }

  private ColorPicker makeColorPicker(String key, int col, Color color) {
    ColorPicker colorPicker = new ColorPicker(color);
    colorPicker.setId(idsForTesting.getString(key));
    colorPicker.getStyleClass().add(COLOR_PICKER);
    colorPicker.setOnAction(event -> reflectionMethod(key));
    choicePane.add(colorPicker, col, 0);
    return colorPicker;
  }

  private void reflectionMethod(String key) {
    try {
      String methodName = reflectionResources.getString(key);
      Method m = ViewChoicePane.this.getClass().getDeclaredMethod(methodName);
      m.invoke(ViewChoicePane.this);
    }
    catch (Exception e) {
      new Alert(Alert.AlertType.ERROR);
    }
  }

  public String getLanguage() {
      return language;
  }

  private ImageView setIcon(String icon) {
    ImageView iconView = new ImageView(new Image(icon));
    iconView.setFitWidth(ICON_WIDTH);
    iconView.setFitHeight(ICON_HEIGHT);
    return iconView;
  }
}