package edu.ntnu.idatt2001.paths.gui.menus;

import edu.ntnu.idatt2001.paths.gui.listeners.BaseFrameListener;
import edu.ntnu.idatt2001.paths.gui.uielements.InventoryPane;
import edu.ntnu.idatt2001.paths.gui.utility.GuiUtils;
import edu.ntnu.idatt2001.paths.model.Link;
import edu.ntnu.idatt2001.paths.model.Passage;
import edu.ntnu.idatt2001.paths.model.Player;
import edu.ntnu.idatt2001.paths.tts.TextToSpeech;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The BaseFrame class represents a frame for the game. It shows the passage text of the current
 * passage and has buttons for the links that the player can follow.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class BaseFrame extends AnchorPane {

  private static final Logger logger = Logger.getLogger(BaseFrame.class.getName());
  private final double width;
  private final double height;
  private final Player player;
  private final BaseFrameListener listener;
  private final Passage passage;

  /**
   * Constructor for a BaseFrame object.
   *
   * @param storyTitle The title of the story.
   * @param passage    Passage to present to the user.
   * @param player     The player used to represent player values.
   * @param width      The width of the frame.
   * @param height     The height of the frame.
   * @param listener   The listener used to send out button activations from the frame.
   */
  public BaseFrame(String storyTitle, Passage passage, Player player, double width, double height,
                   BaseFrameListener listener) throws NullPointerException {

    Objects.requireNonNull(storyTitle, "Story title cannot be null");
    this.passage = Objects.requireNonNull(passage, "Passage cannot be null");
    this.player = Objects.requireNonNull(player, "Player cannot be null");
    this.listener = Objects.requireNonNull(listener, "Listener cannot be null");
    this.width = width;
    this.height = height;

    setPrefHeight(height);
    setPrefWidth(width);
    setHealthBar();
    addButtons();
    showGoldAndPoints();
    showPassageText();
    createInventory();
    applyBackground(storyTitle);
    speakPassageInfo(passage);
  }

  /**
   * Uses text to speech to simulate a voice explaining passage info.
   * <li>Says the passage text</li>
   * <li>Says all possible links</li>
   *
   * @param passage The passage used for reading text
   */
  private void speakPassageInfo(Passage passage) {
    List<Link> links = passage.getLinks();
    String speakText = passage.getContent();
    speakText = speakText.concat("You have" + links.size() + "options.");
    for (int i = 0; i < links.size(); i++) {
      int options = i + 1;
      speakText = speakText.concat("Option" + options + passage.getLinks().get(i).getText() + ".");
    }
    if (links.size() > 1) {
      speakText = speakText.concat("Please choose wisely.");
    }
    TextToSpeech.getInstance().speech(speakText);
  }

  /**
   * The method applies a background image to the current scene.
   *
   * @param storyTitle The title of the story for which images are to be retrieved.
   */
  private void applyBackground(String storyTitle) {
    try {
      String fileName = passage.getTitle().toLowerCase().replace(" ", "_");
      String folderName = storyTitle.toLowerCase().replace(" ", "");
      String imageFolderPath = "src/main/resources/images/" + folderName;
      String imageFolderPathRelative = "images/" + folderName + "/";

      String[] imageFiles = new File(imageFolderPath).list();

      String imageExtension = null;
      if (imageFiles != null) {
        for (String file : imageFiles) {
          if (file.startsWith(fileName)) {
            if (file.endsWith(".png")) {
              imageExtension = ".png";
              break;
            } else if (file.endsWith(".jpg") || file.endsWith(".jpeg")) {
              imageExtension = ".jpg";
              break;
            }
          }
        }
      }

      if (imageExtension == null) {
        throw new FileNotFoundException("Image not found.");
      }

      String pathOfFile = imageFolderPathRelative + fileName + imageExtension;

      GuiUtils.setBackgroundImage(this, pathOfFile);

    } catch (FileNotFoundException e) {
      String errorMessage = "Failed to load background image: " + e.getMessage();
      logger.log(Level.WARNING, errorMessage, e);
      Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage);
      alert.showAndWait();
    }
  }

  /**
   * Sets a health bar in the top left corner, showing how much health the user has left.
   */
  private void setHealthBar() {
    ProgressBar healthBar = new ProgressBar((double) player.getHealth() / 100);

    healthBar.prefWidthProperty().bind(widthProperty().divide(5));
    healthBar.prefHeightProperty().bind(heightProperty().divide(15));

    healthBar.setStyle("-fx-accent: #00ff00; -fx-background-color: gray;");

    getChildren().add(healthBar);
    setTopAnchor(healthBar, 0.0);
    setRightAnchor(healthBar, 0.0);
  }

  /**
   * Adds the restart, exit and link buttons to the frame.
   */
  private void addButtons() {
    addRestartButton();
    addExitButton();
    addLinkButtons();
  }

  /**
   * Adds an exit button to the frame, and adds the listener action to it.
   */
  private void addExitButton() {
    Button exitButton = new Button("Exit");

    exitButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save the game?");
      ButtonType yesButton = new ButtonType("Yes");
      ButtonType noButton = new ButtonType("No");
      alert.getButtonTypes().setAll(yesButton, noButton);
      Optional<ButtonType> result = alert.showAndWait();
      boolean shouldSaveGame = result.isPresent() && result.get() == yesButton;
      listener.onExitClicked(shouldSaveGame);
    });
    exitButton.setLayoutX(0);
    exitButton.setLayoutY(0);
    setTopAnchor(exitButton, 0.0);
    setLeftAnchor(exitButton, 0.0);
    getChildren().add(exitButton);
  }

  /**
   * Adds a restart button to the frame and adds the listener action to it.
   */
  private void addRestartButton() {
    Button restartButton = new Button("Restart");
    restartButton.setStyle("-fx-wrap-text: false");
    restartButton.setOnAction(event -> listener.onRestartClicked());

    restartButton.setLayoutX(100);

    getChildren().add(restartButton);

  }

  /**
   * Adds the link buttons for the frame and adds the listener action to it.
   */
  private void addLinkButtons() {
    VBox choiceButtons = new VBox();
    choiceButtons.setSpacing(10);

    for (Link link : passage.getLinks()) {
      Button button = new Button(link.getText());
      button.setOnAction(event -> listener.onOptionButtonClicked(link));
      choiceButtons.getChildren().add(button);
    }

    setRightAnchor(choiceButtons, width / 5);
    setBottomAnchor(choiceButtons, height / 5);
    getChildren().add(choiceButtons);
  }

  /**
   * Adds a text field of the amount of gold and points the user has.
   */
  private void showGoldAndPoints() {
    int gold = player.getGold();
    int points = player.getScore();
    Text healthText = new Text("Gold: " + gold);
    Text pointsText = new Text("Points:" + points);

    VBox attributes = new VBox();
    attributes.getChildren().addAll(healthText, pointsText);
    attributes.setLayoutX(0);
    attributes.setLayoutY(height / 3);
    attributes.setStyle("-fx-background-color: #add8e6");
    getChildren().add(attributes);
  }

  /**
   * Adds the passage text to the frame.
   */
  private void showPassageText() {
    Text text = new Text(passage.getContent());
    text.setWrappingWidth(width / 10);
    VBox textPane = new VBox();
    textPane.setStyle("-fx-background-color: #FFFFFF;");

    textPane.setLayoutY(height / 5);
    textPane.getChildren().add(text);
    textPane.setStyle("-fx-background-color: #add8e6");

    setRightAnchor(textPane, 100.0);
    getChildren().add(textPane);
  }

  /**
   * Adds a resizable inventory to the frame.
   */
  private void createInventory() {
    int inventoryHeight = 70;

    InventoryPane inventoryPane = new InventoryPane(60, inventoryHeight, player.getInventory());
    getChildren().add(inventoryPane);
    inventoryPane.setLayoutY(getPrefHeight() - 70);
    setBottomAnchor(inventoryPane, 10.0);
    setLeftAnchor(inventoryPane, 10.0);
  }
}

