package edu.ntnu.idatt2001.paths.gui.listeners;

import edu.ntnu.idatt2001.paths.model.Game;

/**
 * Interface for setting actions to the buttons of a "Load stored games menu".
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 12, 2023.
 */
public interface StoredGamesListener {

  /**
   * The method handles the selected game action.
   */
  void onSelectedGameClicked(Game game);

  /**
   * The method handles the return action
   * and is called when the return button is clicked.
   */
  void onReturnClicked();

}
