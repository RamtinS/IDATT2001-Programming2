package edu.ntnu.idatt2001.paths.gui.listeners;

import edu.ntnu.idatt2001.paths.Difficulty;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.goals.Goal;
import java.util.List;

/**
 * Interface for setting actions to the buttons of a "create game" frame.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public interface CreateGameListener {

  void onReturnClicked();

  void onCreateClicked(List<Goal> chosenGoals, String playerName, Difficulty chosenDifficulty,
                       Story selectedStory);
}
