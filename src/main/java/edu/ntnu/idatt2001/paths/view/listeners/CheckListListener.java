package edu.ntnu.idatt2001.paths.view.listeners;

import java.util.List;

/**
 * Interface activating button activity in a
 * {@link edu.ntnu.idatt2001.paths.view.uielements.CheckListView CheckListView} object.
 *
 * @author Tobias Oftedal
 * @author Ramtin Samavat
 * @version 1.0
 * @since May 16, 2023.
 */
public interface CheckListListener {

  /**
   * Handles the confirming of selected goals.
   *
   * @param selectedGoals The selected goals.
   */
  void onConfirmSelectionsClicked(List<String> selectedGoals);

}

