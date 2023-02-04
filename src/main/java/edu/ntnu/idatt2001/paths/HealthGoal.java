package edu.ntnu.idatt2001.paths;

public class HealthGoal implements Goal{
  private final int minimumHealth;

  public HealthGoal(int minimumHealth) throws IllegalArgumentException{
    if (minimumHealth < 0) throw new IllegalArgumentException("\nMinimum health cannot be less than zero.");
    this.minimumHealth = minimumHealth;
  }

  public int getMinimumHealth() {
    return minimumHealth;
  }

  @Override
  public boolean isFulfilled(Player player) {
    return player.getHealth() > getMinimumHealth();
  }
}
