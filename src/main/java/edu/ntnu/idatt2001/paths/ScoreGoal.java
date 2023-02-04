package edu.ntnu.idatt2001.paths;

public class ScoreGoal implements Goal{
  private final int minimumPoints;

  public ScoreGoal(int minimumPoints) {
    if (minimumPoints < 0) throw new IllegalArgumentException("\nMinimum points cannot be less than zero.");
    this.minimumPoints = minimumPoints;
  }

  public int getMinimumPoints() {
    return minimumPoints;
  }

  @Override
  public boolean isFulfilled(Player player) {
    return player.getScore() > getMinimumPoints();
  }
}
