package edu.ntnu.idatt2001.paths;

import java.util.Objects;

public class ScoreGoal implements Goal{
  private final int minimumPoints;

  public ScoreGoal(int minimumPoints) throws IllegalArgumentException {
    if (minimumPoints < 0) throw new IllegalArgumentException("\nMinimum points cannot be less than zero.");
    this.minimumPoints = minimumPoints;
  }

  public int getMinimumPoints() {
    return minimumPoints;
  }

  @Override
  public boolean isFulfilled(Player player) throws NullPointerException {
    Objects.requireNonNull(player, "\nPlayer cannot be null.");
    return player.getScore() > getMinimumPoints();
  }
}
