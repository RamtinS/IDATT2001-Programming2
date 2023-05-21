package edu.ntnu.idatt2001.paths.gui.listeners;

import edu.ntnu.idatt2001.paths.model.Difficulty;
import edu.ntnu.idatt2001.paths.model.goals.Goal;
import edu.ntnu.idatt2001.paths.model.Story;
import java.util.List;

/**
 * Interface for setting actions to the buttons of a "create game" frame.
 *
 * @author Tobias Oftedal.
 * @author Ramtin Samavat.
 * @version 1.0
 * @since April 26, 2023.
 */
public interface CreateGameListener {

  /**
   * The method handles the return action
   * and is called when the return button is clicked.
   */
  void onReturnClicked();

  /**
   * The method handles the creation action
   * and is called when the create button is clicked.
   *
   * @param pathOfStoryFile the path of the story file.
   * @param chosenGoals the list of chosen goals for the game.
   * @param gameId the ID of the game.
   * @param playerName the name of the player.
   * @param chosenDifficulty the chosen difficulty level for the game.
   * @param selectedStory the selected story for the game.
   */
  void onCreateClicked(String pathOfStoryFile, List<Goal> chosenGoals, String gameId,
                       String playerName, Difficulty chosenDifficulty, Story selectedStory);
}
