package edu.ntnu.idatt2001.paths.view.uielements;

import edu.ntnu.idatt2001.paths.view.listeners.CheckListListener;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents a checklist containing a list of Strings and corresponding checkboxes.
 *
 * @author Tobias Oftedal
 * @author Ramtin Samavat
 * @version 1.0
 * @since May 16, 2023.
 */
public class CheckListView extends VBox {

  private List<String> listItems;
  private final ListView<HBox> listView;
  private final Button finishButton;
  CheckListListener checkListListener;

  /**
   * Initiates a new CheckListView object, and adds necessary nodes to the object.
   *
   * @param checkListListener Listener used to activate button activity in different classes..
   * @throws IllegalArgumentException If the {@link #checkListListener} is null.
   */
  public CheckListView(CheckListListener checkListListener) throws IllegalArgumentException {
    if (checkListListener == null) {
      throw new IllegalArgumentException("CheckListListener cannot be null");
    }
    this.checkListListener = checkListListener;
    listItems = new ArrayList<>();
    listView = new ListView<>();
    finishButton = new Button("Finished");
    getChildren().add(listView);
    getChildren().add(finishButton);
    setFinishButtonAction();
  }

  /**
   * Sets the finish button action to activate
   * {@link CheckListListener#onConfirmSelectionsClicked(List)}. Sending the currently selected
   * items through the listener.
   */
  private void setFinishButtonAction() {
    finishButton.setOnAction(event -> {
      checkListListener.onConfirmSelectionsClicked(getSelectedItems());
      Stage stage = (Stage) finishButton.getScene().getWindow();
      stage.close();
    });
  }

  /**
   * Updates all fields in {@link #listView} with the current {@link #listItems}.
   */
  public void updateFields() {
    listView.getItems().removeAll(listView.getItems());
    for (String item : listItems) {
      listView.getItems().add(new HBox(new CheckBox(), new Separator(), new Label(item)));
    }

  }

  /**
   * Gets the current {@link #listItems}.
   *
   * @return The current {@link #listItems}.
   */
  public List<String> getListItems() {
    return listItems;
  }

  /**
   * Creates a list of the currently selected items.
   *
   * @return A list of currently selected
   */
  public List<String> getSelectedItems() {
    List<String> selectedItems = new ArrayList<>();
    for (int i = 0; i < listView.getItems().size(); i++) {
      HBox currentBox = listView.getItems().get(i);

      for (Node node : currentBox.getChildren()) {
        if (node instanceof CheckBox checkBox && checkBox.isSelected()) {
          for (Node possibleLabel : currentBox.getChildren()) {
            if (possibleLabel instanceof Label label) {
              selectedItems.add(label.getText());
            }
          }
        }
      }
    }
    return selectedItems;
  }

  /**
   * Sets a new list of items to the object, and updates the fields.
   *
   * @param listItems The new list of items to be set to the list.
   */
  public void setListItems(List<String> listItems) {
    this.listItems = listItems;
    updateFields();
  }


}
