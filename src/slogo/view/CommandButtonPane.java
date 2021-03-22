package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import slogo.controller.FrontEndExternalAPI;

public class CommandButtonPane {

  private static final String BUTTON_PANE_ID = "CommandButtonPane";
  private static final String BUTTON = "regular-button";
  private static final String DEFAULT_COMMAND_BUTTONS =
      HistoryDisplayPane.class.getPackageName() + ".resources.buttons.";
  private static final String COMMAND_BUTTON_LANGUAGE =
      DEFAULT_COMMAND_BUTTONS + "languages.CommandButtons";

  private ResourceBundle buttonLanguageResource;
  private FrontEndExternalAPI viewController;
  private VBox buttonBox;
  private Object[] buttons;
  private ResourceBundle idsForTesting;

  public CommandButtonPane(FrontEndExternalAPI viewController, ResourceBundle idResources,
      String lang) {
    buttonBox = new VBox();
    buttonBox.getStyleClass().add(BUTTON_PANE_ID);

    this.viewController = viewController;
    buttonLanguageResource = ResourceBundle.getBundle(COMMAND_BUTTON_LANGUAGE + lang);
    idsForTesting = idResources;

    buttons = buttonLanguageResource.keySet().toArray();

    makeButtons();
  }

  private void makeButtons() {
    for (Object o : buttons) {
      Button button = new Button();
      button.setText(buttonLanguageResource.getString(o.toString()));
      button.getStyleClass().add(BUTTON);
      button.setId(idsForTesting.getString(o.toString()));
      button.setOnAction(event -> doButtonCommand(o.toString()));
      buttonBox.getChildren().add(button);
    }
  }

  private void doButtonCommand(String command) {
    viewController.processUserCommandInput(buttonLanguageResource.getString(command));
  }

  public VBox getBox() {
    return buttonBox;
  }

  public void updateLanguage(String lang) {
    buttonLanguageResource = ResourceBundle.getBundle(COMMAND_BUTTON_LANGUAGE + lang);
    buttonBox.getChildren().clear();
    makeButtons();
  }
}
