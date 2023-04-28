package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;
import java.util.Objects;

/**
 * The class represents a health goal for the player. To
 * achieve the goal, the player must keep a minimum
 * amount of health.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
public class HealthGoal implements Goal {
  private final int minimumHealth;

  /**
   * Constructor to create an object of the type HealthGoal.
   *
   * @param minimumHealth minimum health value.
   * @throws IllegalArgumentException if minimumHealth is less than zero.
   */
  public HealthGoal(int minimumHealth) throws IllegalArgumentException {
    if (minimumHealth < 0) {
      throw new IllegalArgumentException("\nMinimum health cannot be less than zero.");
    }
    this.minimumHealth = minimumHealth;
  }

  /**
   * The method retrieves the minimum health value.
   *
   * @return the minimum health value.
   */
  public int getMinimumHealth() {
    return minimumHealth;
  }

  /**
   * The method checks if the minimum health value is achieved.
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
    return player.getHealth() > getMinimumHealth();
  }

  /**
   * The method checks for equality between HealthGoal objects.
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
    HealthGoal that = (HealthGoal) o;
    return getMinimumHealth() == that.getMinimumHealth();
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getMinimumHealth());
  }
}
