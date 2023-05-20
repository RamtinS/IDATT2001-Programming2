package edu.ntnu.idatt2001.paths.gui.utility;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * The GuiUtils class is a utility class for GUI elements in
 * the JavaFX application. The class provides utility methods for setting
 * background image and headline for a BorderPane.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 20, 2023.
 */
public class GuiUtils {

  private static final Logger logger = Logger.getLogger(GuiUtils.class.getName());

  /**
   * The method sets the background image of a BorderPane.
   *
   * @param borderPane The BorderPane to set the background image for.
   * @param pathOfFile The file path of the background image.
   */
  public static void setBackgroundImage(BorderPane borderPane, String pathOfFile) {
    try {
      if (pathOfFile == null) {
        throw new NullPointerException("The file path for the background image is null.");
      }
      Image image = new Image(pathOfFile);
      BackgroundImage backgroundImage = new BackgroundImage(image, null,
              null, null, null);
      Background background = new Background(backgroundImage);
      borderPane.setBackground(background);
    } catch (Exception e) {
      String errorMessage = "Could not add background because: " + e.getMessage();
      logger.log(Level.WARNING, errorMessage, e);
      Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage);
      alert.showAndWait();
    }
  }

  /**
   * The method sets the headline label in a BorderPane.
   *
   * @param borderPane The BorderPane to set the headline label in.
   * @param headline The text for the headline label.
   * @param fontSize The font size for the headline label.
   * @param topInset The top inset for the headline label's padding.
   * @param rightInset The right inset for the headline label's padding.
   * @param bottomInset The bottom inset for the headline label's padding.
   * @param leftInset The left inset for the headline label's padding.
   */
  public static void setHeadline(BorderPane borderPane, String headline,
                                  int fontSize, int topInset, int rightInset,
                                  int bottomInset, int leftInset) {
    Label headLabel = new Label(headline);
    headLabel.setFont(new Font(fontSize));
    headLabel.setId("headline-background");
    borderPane.setTop(headLabel);
    BorderPane.setAlignment(headLabel, Pos.CENTER);
    headLabel.setTextAlignment(TextAlignment.CENTER);
    headLabel.setPadding(new Insets(topInset, rightInset, bottomInset, leftInset));
  }
}
