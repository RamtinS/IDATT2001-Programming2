package edu.ntnu.idatt2001.paths.model.goals;

import edu.ntnu.idatt2001.paths.model.Player;

/**
 * The interface represents a target value or a desired
 * result related to the player's condition.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version JDK 17
 */
public interface Goal {

  /**
   * The method checks if the goal has been achieved.
   *
   * @param player the player assigned to the goal.
   * @return true or false depending on whether the goal is achieved.
   */
  boolean isFulfilled(Player player);
}
