package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.model.Player;
import java.util.Objects;

/**
 * The class represents an action where a player's gold is
 * changed by a certain amount.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since April 23, 2023.
 */
public class GoldAction implements Action {
  private final int gold;

  /**
   * Constructs a GoldAction object with the given amount of gold.
   *
   * @param gold The gold given amount of gold to be awarded or removed.
   */
  public GoldAction(int gold) {
    this.gold = gold;
  }

  /**
   * The method retrieves the amount of gold awarded or removed by this action.
   *
   * @return The amount of gold awarded or removed.
   */
  public int getGold() {
    return gold;
  }

  /**
   * The method performs the gold action on the given player by adding or removing
   * gold from their inventory.
   *
   * @param player The player that the action will be performed on.
   * @throws NullPointerException If the player is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    if (getGold() >= 0) {
      player.increaseGold(getGold());
    } else {
      player.decreaseGold(getGold());
    }
  }

  /**
   * The method return a string representation of the GoldAction.
   *
   * @return string representation of the GoldAction.
   */
  @Override
  public String toString() {
    return "{Gold:" + getGold() + "}";
  }

  /**
   * The method checks for equality between GoldAction objects.
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
    GoldAction that = (GoldAction) o;
    return getGold() == that.getGold();
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getGold());
  }
}
