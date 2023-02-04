package edu.ntnu.idatt2001.paths;

import java.util.Objects;

public class GoldGoal implements Goal{
  private final int minimumGold;

  public GoldGoal(int minimumGold) throws IllegalArgumentException {
    if (minimumGold < 0) throw new IllegalArgumentException("\nMinimum gold cannot be less than zero.");
    this.minimumGold = minimumGold;
  }

  public int getMinimumGold() {
    return minimumGold;
  }

  @Override
  public boolean isFulfilled(Player player) throws NullPointerException {
    Objects.requireNonNull(player, "\nPlayer cannot be null.");
    return player.getGold() > getMinimumGold();
  }
}
