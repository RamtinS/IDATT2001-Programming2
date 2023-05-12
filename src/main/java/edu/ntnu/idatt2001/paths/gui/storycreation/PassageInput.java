package edu.ntnu.idatt2001.paths.gui.storycreation;

import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.gui.InputField;
import java.util.ArrayList;
import java.util.List;
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


  private final InputField titleField;
  private final TextArea passageText;
  private final List<LinkLine> linkLines;

  /**
   * Constructor for a PassageInputObject. Contains input fields for the passage title and text.
   */
  public PassageInput() {
    titleField = createTitleField();
    passageText = new TextArea();
    linkLines = new ArrayList<>();

    getChildren().add(titleField);
    getChildren().add(passageText);
  }

  /**
   * Creates an input field for the title of the passage.
   *
   * @return An input field for the title of the passage.
   */
  private InputField createTitleField() {
    InputField inputField = new InputField(getWidth(), getHeight() / 5, false, true);
    inputField.setHint("Enter passage title");
    return inputField;
  }

  /**
   * Adds a LinkLine to the list of connected link lines.
   *
   * @param linkLine The LinkLine to be added to the list of link lines.
   */
  public void addLinkLine(LinkLine linkLine) {
    linkLines.add(linkLine);
  }

  /**
   * Gets the list of all connected link lines.
   *
   * @return The list of alle connected link lines.
   */
  public List<LinkLine> getLinkLines() {
    return linkLines;
  }

  /**
   * Creates a passage from the given input values, with the input title as the title, the connected
   * links, and the input text area as the text.
   *
   * @return A passage with the input title and text.
   */
  public Passage getPassage() {
    Passage passage = new Passage(titleField.getTextArea().getText(), passageText.getText());
    for (LinkLine linkLine : getLinkLines()) {
      passage.addLink(linkLine.getLink());
    }
    return passage;
  }


}
