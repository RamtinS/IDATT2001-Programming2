package edu.ntnu.idatt2001.paths.gui;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents an input field with a text area input and an image explaining the validity of the
 * input.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class InputField extends HBox {

  private final TextArea textArea;
  private final ImageView imageView;
  private boolean shouldBePositiveInteger;
  private boolean shouldHaveText;
  private final Image invalidImage;
  private final Image warningImage;
  private final Image validImage;

  /**
   * Constructor for an input field object.
   *
   * @param width                   The width of the input field.
   * @param height                  The height of the input field.
   * @param shouldBePositiveInteger boolean value explaining if the text should be a positive
   *                                integer.
   * @param shouldHaveText          boolean value explaining if the text should have text or not.
   */
  public InputField(double width, double height, boolean shouldBePositiveInteger,
                    boolean shouldHaveText) {

    textArea = new TextArea();
    textArea.setPrefRowCount(1);
    textArea.setWrapText(true);

    this.validImage = new Image("images/checkmark.png");
    this.invalidImage = new Image("images/red_x.png");
    this.warningImage = new Image("images/exclamation.png");

    imageView = new ImageView();
    imageView.setFitHeight(width);
    imageView.setFitWidth(height);
    imageView.setPreserveRatio(true);

    this.shouldHaveText = shouldHaveText;
    this.shouldBePositiveInteger = shouldBePositiveInteger;

    getChildren().addAll(textArea, imageView);
  }

  /**
   * Gets the text area of the text field value.
   *
   * @return The text area of the text field value.
   */
  public TextArea getTextArea() {
    return textArea;
  }

  /**
   * Gets the current image of the input field.
   *
   * @return The current image of the input field.
   */
  public Image getImage() {
    return imageView.getImage();
  }

  /**
   * Sets the image to the specified image.
   *
   * @param image New image to be set as the current image.
   */
  public void setImage(Image image) {
    imageView.setImage(image);
  }

  /**
   * Sets the image to the valid input image.
   */
  public void setImageValid() {
    setImage(validImage);
  }

  /**
   * Sets the image to the invalid input image.
   */
  public void setImageInvalid() {
    setImage(invalidImage);
  }

  /**
   * Sets the image to the warning input image.
   */
  public void setImageWarning() {
    setImage(warningImage);
  }

  /**
   * Checks if the current text is a positive integer.
   *
   * @return true if the text is a positive integer, false if not.
   */
  public boolean isPositiveInteger() {
    try {
      Integer.parseInt(textArea.getText());
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Checks if the current text is not empty.
   *
   * @return true text is present, false if not.
   */
  public boolean hasText() {
    return !textArea.getText().isEmpty();
  }

  /**
   * Checks if the current text is a valid.
   *
   * @return true if the text is a valid, false if not.
   */
  public boolean hasValidInput() {
    return (!shouldBePositiveInteger || isPositiveInteger()) && (!shouldHaveText || hasText());
  }

  /**
   * Sets the picture according to the state of the input text.
   */
  public void setPrefferedPicture() {
    if (hasValidInput()) {
      setImageValid();
    } else if (!hasText()) {
      setImageWarning();
    } else {
      setImageInvalid();
    }

  }

  /**
   * Sets a hint to the input field with information about what should be written there.
   *
   * @param hint The hint to be set to the text area.
   */
  public void setHint(String hint) {
    textArea.setPromptText(hint);
  }


}
