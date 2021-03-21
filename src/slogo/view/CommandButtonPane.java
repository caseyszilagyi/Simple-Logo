package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import slogo.controller.FrontEndExternalAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class CommandButtonPane {
  private static final String BUTTON_PANE_ID = "CommandButtonPane";
  private static final String BUTTON = "regular-button";
  private static final String DEFAULT_COMMAND_BUTTONS = HistoryDisplayPane.class.getPackageName() + ".resources.buttons.";
  private static final String COMMAND_BUTTONS_ACTIONS = DEFAULT_COMMAND_BUTTONS + "CommandButtonsReflectionActions";
  private static final String COMMAND_BUTTON_LANGUAGE = DEFAULT_COMMAND_BUTTONS + "languages.CommandButtons";

  private ResourceBundle buttonLanguageResource;
  private ResourceBundle buttonReflectionAction;
  private FrontEndExternalAPI viewController;
  private VBox buttonBox;
  private String language;
  private Object[] buttons;

  public CommandButtonPane(FrontEndExternalAPI viewController, String lang) {
    buttonBox = new VBox();
    buttonBox.getStyleClass().add(BUTTON_PANE_ID);

    this.viewController = viewController;
    language = lang;
    buttonLanguageResource = ResourceBundle.getBundle(COMMAND_BUTTON_LANGUAGE + language);
    buttonReflectionAction = ResourceBundle.getBundle(COMMAND_BUTTONS_ACTIONS);

    buttons = buttonLanguageResource.keySet().toArray();

    makeButtons();
  }

  private void makeButtons() {
    for(Object o: buttons) {
      Button button = new Button();
      button.setText(buttonLanguageResource.getString(o.toString()));
      button.getStyleClass().add(BUTTON);
      button.setOnAction(handler -> {
        try {
          String methodName = buttonReflectionAction.getString(o.toString());
          Method m = CommandButtonPane.this.getClass().getDeclaredMethod(methodName);
          m.invoke(CommandButtonPane.this);
        }
        catch (Exception e) {
          System.out.println("Invalid method name");
        }
      });
      buttonBox.getChildren().add(button);
    }
  }

  private void forward() {
    viewController.processUserCommandInput("fd 50");
  }

  private void backward() {
    viewController.processUserCommandInput("bk 50");
  }

  private void right() {
    viewController.processUserCommandInput("rt 45");
  }

  private void left() {
    viewController.processUserCommandInput("lt 45");
  }

  private void penUp() {
    viewController.processUserCommandInput("penup");
  }

  private void penDown() {
    viewController.processUserCommandInput("pendown");
  }

  private void clearScreen() {
    viewController.processUserCommandInput("clearscreen");
  }

  private void home() {
    viewController.processUserCommandInput("home");
  }

  public VBox getBox() {
    return buttonBox;
  }
}
