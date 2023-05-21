package edu.ntnu.idatt2001.paths.view.listeners;

/**
 * Interface for setting actions to the buttons of a "main menu" frame.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 20, 2023.
 */
public interface MainMenuListener {

  /**
   * The method handles the new game action and
   * is called when the new game button is clicked.
   */
  void onNewGameClicked();

  /**
   * The method handles the load game action and
   * is called when the load game button is clicked.
   */
  void onLoadGameClicked();

  /**
   * The method handles tutorial action and
   * is called when the tutorial button is clicked.
   */
  void onTutorialButtonClicked();

  /**
   * The method handles create story action and
   * is called when the create story button is clicked.
   */
  void onCreateStoryMenuClicked();

  /**
   * The method handles exit action and
   * is called when the exit button is clicked.
   */
  void onExitClicked();
}
