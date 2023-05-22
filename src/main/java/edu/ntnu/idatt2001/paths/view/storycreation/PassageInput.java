package edu.ntnu.idatt2001.paths.view.storycreation;

import edu.ntnu.idatt2001.paths.model.Passage;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Represents a passage input. Contains a headline, passage text
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 12, 2023.
 */
public class PassageInput extends VBox {


  private final TextArea titleField;
  private final TextArea passageText;

  /**
   * Constructor for a PassageInputObject. Contains input fields for the passage title and text.
   */
  public PassageInput() {
    setPadding(new Insets(3));
    setStyle("-fx-background-color: #000000");
    setOnMouseEntered(event -> setCursor(Cursor.HAND));

    titleField = createTitleField();
    passageText = createPassageText();

    getChildren().add(titleField);
    getChildren().add(passageText);
  }

  /**
   * Creates a new {@link TextArea} with the prompt "Passage text".
   *
   * @return A new {@link TextArea} with the prompt "Passage text".
   */
  private TextArea createPassageText() {
    TextArea textArea = new TextArea();
    textArea.setPromptText("Passage text");
    return textArea;
  }

  /**
   * Creates an input field for the title of the passage.
   *
   * @return An input field for the title of the passage.
   */
  private TextArea createTitleField() {
    TextArea inputField = new TextArea();
    inputField.setPromptText("Title");
    return inputField;
  }

  /**
   * Creates a passage from the given input values, with the input title as the title, the connected
   * links, and the input text area as the text.
   *
   * @return A passage with the input title and text.
   */
  public Passage getPassage() {
    return new Passage(titleField.getText(), passageText.getText());
  }


}
