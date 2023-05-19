package edu.ntnu.idatt2001.paths.gui.menus;


import edu.ntnu.idatt2001.paths.gui.listeners.MainMenuListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Represents the main menu of the application, with options for creating a new game, loading a
 * previously created game, or entering the tutorial.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since April 26, 2023.
 */
public class MainMenu extends BorderPane {

  private static final Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

  private final MainMenuListener mainMenuListener;

  /**
   * Constructor for a MainMenu object.
   *
   * @param width    The width of the menu.
   * @param height   The height of the menu.
   * @param listener The listener of the main menu, used to activate button functionality.
   */
  public MainMenu(double width, double height, MainMenuListener listener) {
    if (listener == null) {
      throw new NullPointerException("Listener cannot be null");
    }
    this.mainMenuListener = listener;
    setPrefWidth(width);
    setPrefHeight(height);
    setHeadline("Paths");
    setOptionButtons();
    setBackgroundImage();
    addTroll();
    addGuardian();
  }

  /**
   * Sets the headline of the menu to "Main Menu".
   */
  private void setHeadline(String headline) {
    Label headLabel = new Label(headline);
    headLabel.setFont(new Font(40));
    headLabel.setId("headline-background");
    setTop(headLabel);
    setAlignment(headLabel, Pos.CENTER);
    headLabel.setTextAlignment(TextAlignment.CENTER);
    headLabel.setPadding(new Insets(10, 30, 10, 30));
  }

  /**
   * Adds buttons for all user options to the pane.
   * <li>"New game" button: triggers {@link MainMenuListener#onNewGameClicked()}</li>
   * <li>"Load game" button: triggers {@link MainMenuListener#onLoadGameClicked()} ()}</li>
   * <li>"Tutorial" button: triggers {@link MainMenuListener#onTutorialButtonClicked()} ()}</li>
   * <li>"Create game" button: triggers {@link MainMenuListener#onCreateStoryMenuClicked()}
   * ()}</li>
   */
  private void setOptionButtons() {
    final VBox buttonBox = new VBox();
    buttonBox.setMaxWidth(getPrefWidth() * 0.5);
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);
    Button newGame = new Button("New Game");
    Button loadGame = new Button("Load Game");
    Button tutorial = new Button("Tutorial");
    Button createGame = new Button("Create Story");

    newGame.setPrefWidth(Double.MAX_VALUE);
    loadGame.setPrefWidth(Double.MAX_VALUE);
    tutorial.setPrefWidth(Double.MAX_VALUE);
    createGame.setPrefWidth(Double.MAX_VALUE);

    newGame.setOnAction(event -> mainMenuListener.onNewGameClicked());
    loadGame.setOnAction(event -> mainMenuListener.onLoadGameClicked());
    tutorial.setOnAction(event -> mainMenuListener.onTutorialButtonClicked());
    createGame.setOnAction(event -> mainMenuListener.onCreateStoryMenuClicked());

    buttonBox.getChildren().addAll(newGame, loadGame, tutorial, createGame);
    setCenter(buttonBox);

  }


  /**
   * Sets the image background to a picture of a forest.
   */
  private void setBackgroundImage() {
    try {
      Image image = new Image("images/forestadventure/beginnings.png");

      BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
      Background background = new Background(backgroundImage);

      setBackground(background);
    } catch (Exception e) {
      LOGGER.log(Level.INFO, "Could not add background because" + e.getMessage(), e);
    }

  }

  /**
   * Adds an image of a troll to the right side of the menu.
   */
  private void addTroll() {
    ImageView troll = new ImageView(new Image("images/troll_transparent.png"));

    troll.setFitHeight(getPrefHeight() * 0.3);
    troll.setPreserveRatio(true);
    setAlignment(troll, Pos.BOTTOM_CENTER);

    setRight(troll);
    TranslateTransition transition = new TranslateTransition(Duration.seconds(5), troll);
    transition.setByY(-100);
    transition.setCycleCount(Animation.INDEFINITE);
    transition.setAutoReverse(true);
    transition.play();
  }

  private void addGuardian() {
    ImageView guardian = new ImageView(new Image("images/guardian_transparent.png"));

    guardian.setFitHeight(getPrefHeight() * 0.4);
    guardian.setPreserveRatio(true);
    setAlignment(guardian, Pos.BOTTOM_CENTER);

    TranslateTransition transition = new TranslateTransition(Duration.seconds(5), guardian);
    transition.setByY(-100);
    transition.setCycleCount(Animation.INDEFINITE);
    transition.setAutoReverse(true);
    transition.play();
    setLeft(guardian);
  }


}
