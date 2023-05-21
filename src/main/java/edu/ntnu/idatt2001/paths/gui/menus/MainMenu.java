package edu.ntnu.idatt2001.paths.gui.menus;

import edu.ntnu.idatt2001.paths.gui.listeners.MainMenuListener;
import edu.ntnu.idatt2001.paths.gui.utility.GuiUtils;
import edu.ntnu.idatt2001.paths.model.tts.TextToSpeech;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.ToggleSwitch;

/**
 * This class represents the main menu of the application,
 * providing various options for the user. The options
 * include creating a new game, loading a previously saved
 * game, entering the tutorial, creating a new story, exiting the
 * application, or using the settings.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 20, 2023.
 */
public class MainMenu extends BorderPane {

  private static final Logger logger = Logger.getLogger(MainMenu.class.getName());
  private final MainMenuListener mainMenuListener;
  private final Stage settingsStage;

  /**
   * Constructor for a MainMenu object.
   *
   * @param width    The width of the menu.
   * @param height   The height of the menu.
   * @param listener The listener of the main menu, used to activate button functionality.
   * @throws NullPointerException if the listener is null.
   */
  public MainMenu(double width, double height, MainMenuListener listener)
          throws NullPointerException {
    this.mainMenuListener = Objects.requireNonNull(listener, "MainMenuListener cannot be null.");
    ListView<HBox> settingsView = createSettingsView();
    settingsStage = new Stage();
    settingsStage.setScene(new Scene(settingsView));

    GuiUtils.setBackgroundImage(this, "images/forestadventure/beginnings.png");
    GuiUtils.setHeadline(this, "Paths", 40, 10, 30, 10, 30);

    setPrefWidth(width);
    setPrefHeight(height);
    setOptionButtons();
    addMovingImage("images/troll_transparent.png", getPrefHeight() * 0.3, Pos.BOTTOM_RIGHT);
    addMovingImage("images/guardian_transparent.png", getPrefHeight() * 0.4, Pos.BOTTOM_LEFT);
  }

  /**
   * Adds buttons for all user options to the pane.
   *
   * <p>Options available:</p>
   * <ul>
   *   <li>"New Game" button: triggers {@link MainMenuListener#onNewGameClicked()}</li>
   *   <li>"Load Game" button: triggers {@link MainMenuListener#onLoadGameClicked()}</li>
   *   <li>"Tutorial" button: triggers {@link MainMenuListener#onTutorialButtonClicked()}</li>
   *   <li>"Create Story" button: triggers {@link MainMenuListener#onCreateStoryMenuClicked()}</li>
   *   <li>"Exit" button: triggers {@link MainMenuListener#onExitClicked()}</li>
   * </ul>
   */
  private void setOptionButtons() {
    final VBox buttonBox = new VBox();
    buttonBox.setMaxWidth(getPrefWidth() * 0.2);
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);

    Button newGame = createButton("New Game", mainMenuListener::onNewGameClicked);
    Button loadGame = createButton("Load Game", mainMenuListener::onLoadGameClicked);
    Button tutorial = createButton("Tutorial", mainMenuListener::onTutorialButtonClicked);
    Button createGame = createButton("Create Story", mainMenuListener::onCreateStoryMenuClicked);
    Button exit = createButton("Exit", mainMenuListener::onExitClicked);
    Button settingsButton = createButton("Settings", settingsStage::show);

    buttonBox.getChildren().addAll(newGame, loadGame, tutorial, createGame, settingsButton, exit);
    setCenter(buttonBox);
  }

  /**
   * The method creates a button with the given label and action handler.
   *
   * @param label  The label text of the button.
   * @param action The action handler for the button.
   * @return The created button.
   */
  private Button createButton(String label, Runnable action) {
    Button button = new Button(label);
    button.setPrefWidth(Double.MAX_VALUE);
    button.setOnAction(event -> action.run());
    return button;
  }

  /**
   * Creates a mute switch that mutes the {@link TextToSpeech} instance when selected.
   *
   * @return a mute switch that mutes the {@link TextToSpeech} instance when selected.
   */
  private ToggleSwitch muteSwitch() {
    ToggleSwitch toggleSwitch = new ToggleSwitch();
    TextToSpeech textToSpeech = TextToSpeech.getInstance();
    toggleSwitch.setSelected(textToSpeech.isSpeechEnabled());
    toggleSwitch.selectedProperty().addListener(
        (observable, oldValue, newValue) -> TextToSpeech.getInstance().setSpeechEnabled(newValue));
    return toggleSwitch;
  }

  /**
   * Creates and returns a ListView containing settings for the application.
   *
   * @return A ListView object containing all settings for the application
   */
  private ListView<HBox> createSettingsView() {
    ListView<HBox> settings = new ListView<>();
    settings.getItems().add(new HBox(new Label("Text to speech"), muteSwitch()));
    return settings;
  }

  /**
   * The method adds a moving image to the menu.
   *
   * @param imagePath The path to the image file.
   * @param fitHeight The preferred height of the image.
   * @param alignment The alignment of the image within the menu.
   */
  private void addMovingImage(String imagePath, double fitHeight, Pos alignment) {
    try {
      if (imagePath == null) {
        throw new NullPointerException("The file path for the image is null.");
      }
      Image image = new Image(imagePath);
      ImageView imageView = new ImageView(image);
      imageView.setFitHeight(fitHeight);
      imageView.setPreserveRatio(true);
      setAlignment(imageView, alignment);

      TranslateTransition transition = new TranslateTransition(Duration.seconds(5), imageView);
      transition.setByY(-100);
      transition.setCycleCount(Animation.INDEFINITE);
      transition.setAutoReverse(true);
      transition.play();

      if (alignment == Pos.BOTTOM_RIGHT) {
        setRight(imageView);
      } else if (alignment == Pos.BOTTOM_LEFT) {
        setLeft(imageView);
      }
    } catch (Exception e) {
      String errorMessage = "Could not load image: " + e.getMessage();
      logger.log(Level.WARNING, errorMessage, e);
      Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage);
      alert.showAndWait();
    }
  }
}
