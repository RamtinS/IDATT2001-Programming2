package edu.ntnu.idatt2001.paths.gui;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Represents the main menu of the application, with options for creating a new game, loading a
 * previously created game, or entering the tutorial.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class MainMenu extends Pane {

  private final double width;
  private final double height;
  private final MainMenuListener mainMenuListener;

  /**
   * Constructor for a MainMenu object.
   *
   * @param width    The width of the menu.
   * @param height   The height of the menu.
   * @param listener The l
   */
  public MainMenu(int width, int height, MainMenuListener listener) {
    this.width = width;
    this.height = height;
    this.mainMenuListener = listener;

    setPrefHeight(height);
    setPrefWidth(width);

    setHeadLine();
    setOptionButtons();

  }

  /**
   * Sets the headline of the menu to "Main Menu".
   */
  private void setHeadLine() {

    Text headLine = new Text("Main menu");
    headLine.setFont(Font.font("Arial", 30));

    headLine.setWrappingWidth(width / 2);
    headLine.setTextOrigin(VPos.CENTER);

    headLine.xProperty().bind(widthProperty().subtract(headLine.getWrappingWidth()).divide(2));
    headLine.yProperty().bind(heightProperty().multiply(0.1));

    ChangeListener<Number> headLineHeightListener = (value, oldValue, newValue) -> {
      headLine.setFont(
          Font.font(Math.min(heightProperty().getValue() / 10, widthProperty().getValue() / 20)));

    };
    heightProperty().addListener(headLineHeightListener);
    widthProperty().addListener(headLineHeightListener);

    headLine.setTextAlignment(TextAlignment.CENTER);

    getChildren().add(headLine);
  }

  /**
   * Adds buttons for all user options.
   */
  private void setOptionButtons() {
    VBox buttonBox = new VBox();
    Button newGame = new Button();
    Button loadgame = new Button();
    Button tutorial = new Button();

    List<Button> buttonsToAdd = new ArrayList<>();
    buttonsToAdd.add(newGame);
    buttonsToAdd.add(loadgame);
    buttonsToAdd.add(tutorial);

    newGame.setOnAction(event -> mainMenuListener.onNewGameClicked());
    loadgame.setOnAction(event -> mainMenuListener.onLoadGameClicked());
    tutorial.setOnAction(event -> mainMenuListener.onTutorialButtonClicked());

    newGame.setText("New game");
    loadgame.setText("Load game");
    tutorial.setText("Tutorial");

    ChangeListener<Number> widthListener = getButtonWidthChangeListener(buttonsToAdd);
    ChangeListener<Number> heightListener = getButtonHeightChangeListener(buttonsToAdd);

    widthProperty().addListener(widthListener);
    heightProperty().addListener(heightListener);
    buttonBox.getChildren().addAll(newGame, loadgame, tutorial);
    buttonBox.layoutXProperty().bind(widthProperty().subtract(newGame.widthProperty()).divide(2));
    buttonBox.layoutYProperty().bind(heightProperty().subtract(newGame.heightProperty()).divide(2));

    getChildren().add(buttonBox);

  }

  /**
   * Changes the button size for the added buttons when the change listener detects a change.
   *
   * @param buttonsToAdd The buttons change size for when the listener detects a change.
   * @return The ChangeListener created, that will change button sizes when detecting a change.
   */
  private ChangeListener<Number> getButtonWidthChangeListener(List<Button> buttonsToAdd) {
    return (value, oldValue, newValue) -> {
      for (Button button : buttonsToAdd) {
        double newWidth = widthProperty().getValue() / 7;
        button.setPrefWidth(newWidth);
        button.setFont(Font.font(newWidth / 8));
      }
    };
  }

  /**
   * Changes the button size for the added buttons when the change listener detects a change.
   *
   * @param buttonsToAdd The buttons change size for when the listener detects a change.
   * @return The ChangeListener created, that will change button sizes when detecting a change.
   */
  private ChangeListener<Number> getButtonHeightChangeListener(List<Button> buttonsToAdd) {
    return (value, oldValue, newValue) -> {
      for (Button button : buttonsToAdd) {
        double newHeight = heightProperty().getValue() / 14;
        button.setPrefHeight(newHeight);
        button.setMaxHeight(newHeight);
        button.setMinHeight(newHeight);
        button.setFont(Font.font(newHeight / 2));
      }
    };
  }

}
