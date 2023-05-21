package edu.ntnu.idatt2001.paths.gui.storycreation;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.actions.ActionFactory;
import edu.ntnu.idatt2001.paths.model.actions.ActionType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Represents a list of {@link HBox HBoxes} containing a {@link ChoiceBox} and a {@link TextField},
 * used to make the user create a list of different {@link Action actions}.
 * <p>
 * The class contains method for retrieving the data. It also automatically updates the amount of
 * entries in the list if the list has no empty entries.
 * </p>
 */
public class ActionCreator extends ListView<HBox> {

  private static final Logger LOGGER = Logger.getLogger(ActionCreator.class.getName());
  private static final int CHOICE_BOX_INDEX = 0;
  private static final int TEXT_INDEX = 1;

  /**
   * Constructor for the {@link ActionCreator} object. Adds an empty box for the first input.
   */
  public ActionCreator() {
    addEmptyBox();
  }

  /**
   * Adds a new {@link HBox} to the {@link ActionCreator}, consisting of a {@link ChoiceBox} and a
   * {@link TextField}.
   */
  public void addEmptyBox() {
    HBox listItem = new HBox();
    listItem.getChildren().addAll(createActionTypeChoiceBox(), createTextField("Action value"));
    getItems().add(listItem);
  }

  /**
   * Creates an empty {@link TextField} with the given text as a prompt.
   *
   * @param promptText The text to set as prompt text for the field.
   * @return An empty {@link TextField} with a given prompt text.
   */
  private TextField createTextField(String promptText) {
    TextField textArea = new TextField();
    textArea.setPromptText(promptText);

    textArea.setOnKeyPressed(event -> {
      if (allTextFieldsFilled()) {
        addEmptyBox();
      }
    });
    return textArea;
  }

  /**
   * Creates a {@link ChoiceBox} and fills it with all the different
   * {@link ActionType action types}. values.
   *
   * @return A {@link ChoiceBox} containing all possible {@link ActionType action types}.
   */
  private ChoiceBox<ActionType> createActionTypeChoiceBox() {
    ChoiceBox<ActionType> choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll(
        Arrays.asList(ActionType.GOLD, ActionType.HEALTH, ActionType.INVENTORY, ActionType.SCORE));
    return choiceBox;
  }

  /**
   * Creates a {@link List} of {@link Action actions} based on the user input. If any input is not
   * parseable to an action, the input will not be included in the list.
   *
   * @return A {@link List} of all {@link Action actions} created by the user that are valid.
   */
  public List<Action> retrieveActions() {
    List<Action> actions = new ArrayList<>();

    for (HBox box : getItems()) {
      try {
        if (fieldsAreFilled(box)) {
          actions.add(parseAction(box));
        }
      } catch (Exception e) {
        LOGGER.log(Level.INFO, e.getMessage(), e);
      }

    }
    return actions;
  }

  /**
   * Parses an action from a given {@link HBox} entry.
   *
   * @param box The {@link HBox} containing an action to be parsed.
   * @return The parsed action of the given {@link HBox}.
   * @throws IllegalArgumentException If the action description or value is invalid.
   * @throws NullPointerException     If actionDescription or actionValue is null.
   * @throws ClassCastException       If the {@link HBox} does not contain a {@link ChoiceBox} at
   *                                  index: {@link  #CHOICE_BOX_INDEX} and a {@link TextField} at
   *                                  index: {@link  #TEXT_INDEX}.
   */
  private Action parseAction(HBox box)
      throws IllegalArgumentException, NullPointerException, ClassCastException {
    String actionType = ((ChoiceBox<ActionType>) box.getChildren().get(CHOICE_BOX_INDEX)).getValue()
        .toString();
    String actionValue = ((TextField) box.getChildren().get(TEXT_INDEX)).getText();

    return ActionFactory.createAction(actionType, actionValue);
  }

  /**
   * Checks that all {@link TextField textfields} in the class are filled with text, and returns the
   * state of the check.
   *
   * @return {@code true} if all inputs are filled, if not, {@code false} will be returned.
   */
  private boolean allTextFieldsFilled() {
    for (HBox box : getItems()) {
      if (!fieldsAreFilled(box)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks that a given {@link TextField} is filled with text, and returns state of the check.
   *
   * @param box The {@link HBox} used to check for filled fields.
   * @return {@code true} if the inputs are filled, if not, {@code false} will be returned.
   */
  private boolean fieldsAreFilled(HBox box) {
    TextField textField = (TextField) box.getChildren().get(TEXT_INDEX);
    ChoiceBox<ActionType> choiceBox = (ChoiceBox<ActionType>) box.getChildren()
        .get(CHOICE_BOX_INDEX);
    return !textField.getText().isEmpty() && choiceBox.getValue() != null;
  }

}
