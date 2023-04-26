package edu.ntnu.idatt2001.paths.gui;

/**
 * Interface for setting actions to the buttons of a "main menu" frame.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public interface MainMenuListener {

  void onNewGameClicked();

  void onLoadGameClicked();

  void onTutorialButtonClicked();
}
