package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

/**
 * The class represents a gold that, which adds or removes gold from a player's
 * inventory depending on the value of the gold field.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 24, 2023.
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
      player.addGold(getGold());
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
}
