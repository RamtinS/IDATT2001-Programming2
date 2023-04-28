package edu.ntnu.idatt2001.paths.gui;

import edu.ntnu.idatt2001.paths.Link;

/**
 * Interface for setting actions to the buttons of a base frame.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public interface BaseFrameListener {

  void onRestartClicked();

  void onExitClicked();

  void onOptionButtonClicked(Link link);
}
