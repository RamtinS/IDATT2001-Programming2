package edu.ntnu.idatt2001.paths.model.goals;

import edu.ntnu.idatt2001.paths.model.Player;
import java.util.Objects;

/**
 * The class represents a score goal for the player. To
 * achieve the goal, the player must obtain a minimum
 * amount of points.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since April 23, 2023.
 */
public class ScoreGoal implements Goal {
  private final int minimumPoints;

  /**
   * Constructor to create an object of the type ScoreGoal.
   *
   * @param minimumPoints minimum score value.
   * @throws IllegalArgumentException if the minimumPoints is less than zero.
   */
  public ScoreGoal(int minimumPoints) throws IllegalArgumentException {
    if (minimumPoints < 0) {
      throw new IllegalArgumentException("\nMinimum points cannot be less than zero.");
    }
    this.minimumPoints = minimumPoints;
  }

  /**
   * The method retrieves the minimum points.
   *
   * @return the minimum points.
   */
  public int getMinimumPoints() {
    return minimumPoints;
  }

  /**
   * The method checks if the minimum score value is achieved.
   *
   * @param player the player assigned to the goal.
   * @return true or false depending on whether the goal is achieved.
   * @throws NullPointerException if the player is null.
   */
  @Override
  public boolean isFulfilled(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    return player.getScore() > getMinimumPoints();
  }

  /**
   * The method checks for equality between ScoreGoal objects.
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
    ScoreGoal scoreGoal = (ScoreGoal) o;
    return getMinimumPoints() == scoreGoal.getMinimumPoints();
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getMinimumPoints());
  }

  /**
   * Creates a string containing the minimum score of the goal.
   *
   * @return a string containing the minimum score of the goal.
   */
  @Override
  public String toString() {
    return "Score goal:" + getMinimumPoints();
  }
}
