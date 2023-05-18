package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.model.Player;
import java.util.Objects;

/**
 * The class represents an action where a player's score is
 * changed by a certain amount.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
public class ScoreAction implements Action {
  private final int points;

  /**
   * Constructs a ScoreAction object with the specified number of points.
   *
   * @param points The amount of points that the player will gain from completing the action.
   */
  public ScoreAction(int points) {
    this.points = points;
  }

  /**
   * The method retrieves the number of points awarded for this action.
   *
   * @return the number of points awarded for this action.
   */
  public int getPoints() {
    return points;
  }

  /**
   * The method executes the score action on a player,
   * adding the points to the player's score.
   *
   * @param player The player performs the action.
   * @throws NullPointerException If the player object is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    if (getPoints() >= 0) {
      player.increaseScore(getPoints());
    } else {
      player.decreaseScore(getPoints());
    }
  }

  /**
   * The method return a string representation of the ScoreAction.
   *
   * @return string representation of the ScoreAction.
   */
  @Override
  public String toString() {
    return "{Score:" + getPoints() + "}";
  }

  /**
   * The method checks for equality between ScoreAction objects.
   *
   * @param o the object to which it is being compared.
   * @return a boolean value which indicate whether they are equal or not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScoreAction that = (ScoreAction) o;
    return getPoints() == that.getPoints();
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getPoints());
  }
}
