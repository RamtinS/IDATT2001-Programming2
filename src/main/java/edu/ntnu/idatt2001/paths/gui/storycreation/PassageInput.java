package edu.ntnu.idatt2001.paths.gui.storycreation;

import edu.ntnu.idatt2001.paths.model.Passage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Represents a passage input. Contains a headline, passage text
 *
 * @author Ramtin Samavat and Tobias Oftedal.
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
    titleField = createTitleField();
    passageText = new TextArea();

    getChildren().add(titleField);
    getChildren().add(passageText);
  }

  /**
   * Creates an input field for the title of the passage.
   *
   * @return An input field for the title of the passage.
   */
  private TextArea createTitleField() {
    TextArea inputField = new TextArea();
    inputField.setPromptText("Enter passage title");
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
