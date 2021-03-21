package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.controller.ModelController;
import slogo.controller.ViewController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewChoicePane {
  private static final double ICON_WIDTH = 20.0;
  private static final double ICON_HEIGHT = 20.0;

  private static final String BACKGROUND_ICON = "BackgroundIcon.gif";
  private static final String PEN_ICON = "PenIcon.gif";
  private static final String TURTLE_ICON = "TurtleIcon.gif";
  private static final String NEW_WINDOW_ICON = "NewWindow.gif";

  private static final String CHOICE_PANE_ID = "ChoicePane";
  private static final String PEN_COLOR_PICKER_ID = "PenColorPicker";
  private static final String COLOR_PICKER = "color-picker";
  private static final String ICON = "icon";
  private static final String LANGUAGE_OPTIONS = "slogo.view.resources.buttons.languages.LangaugeOptions";
  private static final String IDS_FOR_TESTING = "slogo.view.resources.IDsforTesting";

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
  private FrontEndExternalAPI viewController;

  public ViewChoicePane(FrontEndExternalAPI viewController, Stage s, BorderPane root, TurtleDisplayPane turtleDisplayPane) {
    this.viewController = viewController;
    stage = s;
    viewPane = root;
    turtleDisplay = turtleDisplayPane;

    choicePane = new GridPane();
    choicePane.getStyleClass().add(CHOICE_PANE_ID);
    viewPane.setTop(choicePane);

    idsForTesting = ResourceBundle.getBundle(IDS_FOR_TESTING);

    createBackgroundColorPicker();
    createPenColorPicker();
    createTurtleImageButton();
    createNewWindowButton();
    createLanguageComboBox();
  }

  private void createBackgroundColorPicker() {
    backgroundColorPickerButton = new Button();
    backgroundColorPickerButton.setId(idsForTesting.getString("ChangeBackgroundButton"));
    ImageView icon = setIcon(BACKGROUND_ICON);
    backgroundColorPickerButton.setGraphic(icon);
    backgroundColorPickerButton.setOnAction(event -> changeBackgroundButton());
    backgroundColorPickerButton.getStyleClass().add(ICON);
    choicePane.add(backgroundColorPickerButton, 0, 0);
  }

  private void changeBackgroundButton() {
    choicePane.getChildren().remove(backgroundColorPickerButton);
    backgroundColorPicker = new ColorPicker(Color.valueOf(currentBackgroundColor));
    backgroundColorPicker.setId(idsForTesting.getString("ChangeBackgroundColorPicker"));
    backgroundColorPicker.getStyleClass().add(COLOR_PICKER);
    choicePane.add(backgroundColorPicker, 0, 0);
    backgroundColorPicker.setOnAction(event -> changeBackgroundColor());
  }

  private void changeBackgroundColor() {
    Paint fill = backgroundColorPicker.getValue();
    currentBackgroundColor = fill.toString();
    BackgroundFill backgroundFill = new BackgroundFill(fill, new CornerRadii(10), new Insets(10));
    Background background = new Background(backgroundFill);
    turtleDisplay.setBackground(background);
    choicePane.getChildren().remove(backgroundColorPicker);
    choicePane.add(backgroundColorPickerButton, 0, 0);
  }

  private void createPenColorPicker() {
    penColorPickerButton = new Button();
    penColorPickerButton.setId(idsForTesting.getString("ChangePenButton"));
    ImageView icon = setIcon(PEN_ICON);
    penColorPickerButton.setGraphic(icon);
    penColorPickerButton.getStyleClass().add(ICON);
    choicePane.add(penColorPickerButton, 1, 0);
    penColorPickerButton.setOnAction(event -> changePenButton());
  }

  private void changePenButton() {
    choicePane.getChildren().remove(penColorPickerButton);
    penColorPicker = new ColorPicker(currentPenColor);
    penColorPicker.setId(idsForTesting.getString("ChangePenColorPicker"));
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

  public Color getPenColor() {
    return penColor;
  }

  private void createTurtleImageButton() {
    Button turtleImageButton = new Button();
    turtleImageButton.setId(idsForTesting.getString("TurtleIconButton"));
    ImageView turtleIcon = setIcon(TURTLE_ICON);
    turtleImageButton.setGraphic(turtleIcon);
    turtleImageButton.getStyleClass().add(ICON);
    choicePane.add(turtleImageButton, 2, 0);
    turtleImageButton.setOnAction(event -> uploadTurtleImage());
  }

  public void uploadTurtleImage() {
    FileChooser turtleImageChooser = new FileChooser();
    File file = turtleImageChooser.showOpenDialog(stage);
    String turtleImageFile = file.toURI().toString();
    Image turtleImage = new Image(turtleImageFile);
    turtleDisplay.setTurtleImage(turtleImage);
  }

  private void createNewWindowButton() {
    Button addNewScreen = new Button();
    addNewScreen.setId(idsForTesting.getString("NewWindowButton"));
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
    ResourceBundle languageOptions = ResourceBundle.getBundle(LANGUAGE_OPTIONS);
    Object[] allLanguages = languageOptions.keySet().toArray();
    List<String> displayLanguages = new ArrayList<>();
    for (Object allLanguage : allLanguages) {
      displayLanguages.add(languageOptions.getString(allLanguage.toString()));
    }
    languageComboBox = new ComboBox<String>();
    languageComboBox.getItems().addAll(displayLanguages);
    languageComboBox.setId(idsForTesting.getString("LanguageComboBox"));
    language = "English";
    languageComboBox.setValue(languageOptions.getString(language));
    choicePane.add(languageComboBox, 10, 0);
    languageComboBox.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int value = displayLanguages.indexOf(languageComboBox.getValue());
        language = allLanguages[value].toString();
        viewController.setLanguage(language);
      }
    });
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
