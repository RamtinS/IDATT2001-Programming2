package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.model.Player;
import java.util.Objects;

/**
 * The class represents a gold goal for the player. To achieve the goal, the player must obtain a
 * minimum amount of gold.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since April 23, 2023.
 */
public class GoldGoal implements Goal {

  private final int minimumGold;

  /**
   * Constructor to create an object of the type GoldGoal.
   *
   * @param minimumGold a minimum gold holding.
   * @throws IllegalArgumentException if minimumGold is less than zero.
   */
  public GoldGoal(int minimumGold) throws IllegalArgumentException {
    if (minimumGold < 0) {
      throw new IllegalArgumentException("\nMinimum gold cannot be less than zero.");
    }
    this.minimumGold = minimumGold;
  }

  /**
   * The method retrieves the minimum amount of gold.
   *
   * @return the minimum amount of gold.
   */
  public int getMinimumGold() {
    return minimumGold;
  }

  /**
   * The method checks if the minimum gold goal is achieved.
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
    return player.getGold() > getMinimumGold();
  }

  /**
   * The method checks for equality between GoldGoal objects.
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
    GoldGoal goldGoal = (GoldGoal) o;
    return getMinimumGold() == goldGoal.getMinimumGold();
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getMinimumGold());
  }

  /**
   * Creates a string containing the minimum gold of the goal.
   *
   * @return a string containing the minimum gold of the goal.
   */
  @Override
  public String toString() {
    return "Gold goal:" + getMinimumGold();
  }
}
