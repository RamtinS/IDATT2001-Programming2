package edu.ntnu.idatt2001.paths.gui;


import edu.ntnu.idatt2001.paths.gui.listeners.MainMenuListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Represents the main menu of the application, with options for creating a new game, loading a
 * previously created game, or entering the tutorial.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class MainMenu extends Pane {

  Button newGame;
  Button loadGame;
  Button tutorial;
  Button createGame;
  private static final double BUTTON_WIDTH_PERCENTAGE = 0.2;
  private static final double BUTTON_BOX_HEIGHT_PERCENTAGE = 0.5;
  private static final double BUTTON_SPACING_PERCENTAGE = 0.05;
  private static final Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

  private final MainMenuListener mainMenuListener;

  /**
   * Constructor for a MainMenu object.
   *
   * @param width    The width of the menu.
   * @param height   The height of the menu.
   * @param listener The l
   */
  public MainMenu(double width, double height, MainMenuListener listener) {
    DimensionUtility.changeAllPaneWidths(this, width);
    DimensionUtility.changeAllPaneHeights(this, height);
    this.mainMenuListener = listener;

    setHeadLine();
    setOptionButtons();
    setBackground();
    addTroll();

  }

  /**
   * Sets the headline of the menu to "Main Menu".
   */
  private void setHeadLine() {

    HBox headLine = new HBox();

    Label text = new Label("    Paths    ");
    headLine.getChildren().add(text);
    text.setFont(Font.font("Arial", 45));
    text.setTextAlignment(TextAlignment.RIGHT);
    headLine.setAlignment(Pos.CENTER);
    headLine.layoutYProperty().bind(heightProperty().multiply(0.1));

    ChangeListener<Number> headLineHeightListener = (value, oldValue, newValue) -> {
      text.setFont(
          Font.font(Math.min(heightProperty().getValue() / 10, widthProperty().getValue() / 20)));
      headLine.setPrefWidth(widthProperty().get());

    };

    heightProperty().addListener(headLineHeightListener);
    widthProperty().addListener(headLineHeightListener);

    getChildren().add(headLine);
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
    newGame = new Button("New Game");
    loadGame = new Button("Load Game");
    tutorial = new Button("Tutorial");
    createGame = new Button("Create Story");

    widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
      DimensionUtility.changeAllPaneWidths(buttonBox,
          newSceneWidth.doubleValue() * BUTTON_WIDTH_PERCENTAGE);
      buttonBox.setLayoutX((getWidth() - buttonBox.getPrefWidth()) / 2);
    });

    heightProperty().addListener((observableValue, oldSceneWidth, newSceneHeight) -> {
      changeButtonBoxHeight(buttonBox, newSceneHeight);
    });

    newGame.setPrefWidth(Double.MAX_VALUE);
    loadGame.setPrefWidth(Double.MAX_VALUE);
    tutorial.setPrefWidth(Double.MAX_VALUE);
    createGame.setPrefWidth(Double.MAX_VALUE);

    newGame.setOnAction(event -> mainMenuListener.onNewGameClicked());
    loadGame.setOnAction(event -> mainMenuListener.onLoadGameClicked());
    tutorial.setOnAction(event -> mainMenuListener.onTutorialButtonClicked());
    createGame.setOnAction(event -> mainMenuListener.onCreateStoryMenuClicked());

    buttonBox.getChildren().addAll(newGame, loadGame, tutorial, createGame);
    getChildren().add(buttonBox);

  }

  /**
   * Changes the height of all the buttons to the given height.
   *
   * @param buttonBox The Vbox that contains the buttons.
   * @param newHeight The new height of the buttons.
   */
  private void changeButtonBoxHeight(VBox buttonBox, Number newHeight) {
    DimensionUtility.changeAllPaneHeights(buttonBox,
        newHeight.doubleValue() * BUTTON_BOX_HEIGHT_PERCENTAGE);
    buttonBox.setLayoutY((getHeight() - buttonBox.getPrefHeight()) / 2);
    double spacing = buttonBox.getPrefHeight() * BUTTON_SPACING_PERCENTAGE;
    buttonBox.setSpacing(spacing);
    double buttonHeight = (buttonBox.getPrefHeight() - spacing * 3) / 4;
    DimensionUtility.changeAllButtonHeights(newGame, buttonHeight);
    DimensionUtility.changeAllButtonHeights(loadGame, buttonHeight);
    DimensionUtility.changeAllButtonHeights(tutorial, buttonHeight);
    DimensionUtility.changeAllButtonHeights(createGame, buttonHeight);
  }

  /**
   * Sets the image background to a picture of a forest.
   */
  private void setBackground() {
    try {
      Image image = new Image("images/forestadventure/beginnings.png");

      BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
      Background background = new Background(backgroundImage);

      setBackground(background);
    } catch (Exception e) {
      LOGGER.log(Level.INFO, "Could not add background because" + e.getMessage());
    }

  }

  /**
   * Adds an image of a troll to the right side of the menu.
   */
  private void addTroll() {
    ImageView troll = new ImageView(new Image("images/troll_transparent.png"));
    ChangeListener<Number> heightListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue observableValue, Object o, Object t1) {
        troll.setFitHeight(heightProperty().get() / 2);
        troll.setLayoutY(heightProperty().get() - troll.fitHeightProperty().get());
      }
    };
    ChangeListener<Number> widthListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue observableValue, Object o, Object t1) {
        troll.setFitWidth(widthProperty().get() / 3);
        troll.setLayoutX(widthProperty().get() - troll.fitWidthProperty().get());
      }
    };
    heightProperty().addListener(heightListener);
    widthProperty().addListener(widthListener);
    troll.setPreserveRatio(true);

    getChildren().add(troll);
    TranslateTransition transition = new TranslateTransition(Duration.seconds(5), troll);
    transition.setByY(-100);
    transition.setCycleCount(TranslateTransition.INDEFINITE);
    transition.setAutoReverse(true);
    transition.play();
  }


}
