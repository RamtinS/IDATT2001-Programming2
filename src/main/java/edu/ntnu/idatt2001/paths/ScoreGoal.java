package edu.ntnu.idatt2001.paths;

import java.util.Objects;

/**
 * The class represents an expected minimum score.
 *
 * @author ...
 * @version JDK 17
 */
public class ScoreGoal implements Goal{
  private final int minimumPoints;

  /**
   * Constructor to create an object of the type ScoreGoal.
   *
   * @param minimumPoints minimum score value.
   * @throws IllegalArgumentException if the minimumPoints is less than zero.
   */
  public ScoreGoal(int minimumPoints) throws IllegalArgumentException {
    if (minimumPoints < 0) throw new IllegalArgumentException("\nMinimum points cannot be less than zero.");
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
    Objects.requireNonNull(player, "\nPlayer cannot be null.");
    return player.getScore() > getMinimumPoints();
  }
}
