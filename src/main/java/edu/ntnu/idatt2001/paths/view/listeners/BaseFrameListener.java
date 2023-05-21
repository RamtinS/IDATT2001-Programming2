package edu.ntnu.idatt2001.paths.view.listeners;

import edu.ntnu.idatt2001.paths.model.Link;

/**
 * Interface for setting actions to the buttons of a base frame.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public interface BaseFrameListener {

  /**
   * The method handles the restart action and
   * is called when the restart button is clicked.
   */
  void onRestartClicked();

  /**
   * The method handles the exit action and is
   * called when the exit button is clicked.
   *
   * @param shouldSaveGame true if the game should be saved before exiting, false otherwise.
   */
  void onExitClicked(boolean shouldSaveGame);

  /**
   * The method handles the option button action
   * and is called when the option button is clicked.
   *
   * @param link the link associated with the option button.
   */
  void onOptionButtonClicked(Link link);
}
