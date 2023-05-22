package edu.ntnu.idatt2001.paths.view.utility;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * The GuiUtils class is a utility class for GUI elements in the JavaFX application. The class
 * provides utility methods for setting background image and headline for a BorderPane.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 20, 2023.
 */
public class GuiUtils {

  private static final Logger logger = Logger.getLogger(GuiUtils.class.getName());

  /**
   * Private constructor to hide the implicit public one.
   *
   * @throws IllegalStateException If the constructor is used.
   */
  private GuiUtils() throws IllegalStateException {
    throw new IllegalStateException("Cannot create instance of utility class");
  }

  /**
   * The method sets the background image of a BorderPane.
   *
   * @param pane       The pane to set the background image for.
   * @param pathOfFile The file path of the background image.
   */
  public static void setBackgroundImage(Pane pane, String pathOfFile) {

    try {
      if (pathOfFile == null) {
        throw new NullPointerException("The file path for the background image is null.");
      }

      Image image = new Image(pathOfFile);

      BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);

      BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

      Background background = new Background(backgroundImage);

      pane.setBackground(background);
    } catch (Exception e) {
      String errorMessage = "Failed to load background image: " + e.getMessage();
      logger.log(Level.WARNING, errorMessage, e);
      Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage);
      alert.showAndWait();
    }
  }

  /**
   * The method sets the headline label in a BorderPane.
   *
   * @param borderPane  The BorderPane to set the headline label in.
   * @param headline    The text for the headline label.
   * @param fontSize    The font size for the headline label.
   * @param topInset    The top inset for the headline label's padding.
   * @param rightInset  The right inset for the headline label's padding.
   * @param bottomInset The bottom inset for the headline label's padding.
   * @param leftInset   The left inset for the headline label's padding.
   */
  public static void setHeadline(BorderPane borderPane, String headline, int fontSize, int topInset,
                                 int rightInset, int bottomInset, int leftInset) {
    Label headLabel = new Label(headline);
    headLabel.setFont(new Font(fontSize));
    headLabel.setId("headline-background");
    borderPane.setTop(headLabel);
    BorderPane.setAlignment(headLabel, Pos.CENTER);
    headLabel.setTextAlignment(TextAlignment.CENTER);
    headLabel.setPadding(new Insets(topInset, rightInset, bottomInset, leftInset));
  }

  /**
   * The method creates a return button and adds it to the specified BorderPane.
   *
   * @param boarderPane The BorderPane to which the return button will be added.
   * @param label       The label text for the return button.
   * @param action      The action to be performed when the return button is clicked
   * @param position    The position of the return button.
   */
  public static void createReturnButton(BorderPane boarderPane, String label, Runnable action,
                                        Pos position) {
    Button button = new Button(label);
    button.setOnAction(event -> action.run());
    button.setId("return-button");
    VBox buttonPane = new VBox(button);
    buttonPane.setAlignment(position);
    buttonPane.setPadding(new Insets(0, 40, 0, 0));
    boarderPane.setLeft(buttonPane);
  }
}
