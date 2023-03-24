package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

/**
 * The class represents a gold action.
 *
 * @author ...
 * @version JDK 17
 */
public class GoldAction implements Action {
  private final int gold;

  /**
   * Constructor to create an object of GoldAction.
   *
   * @param gold The gold that will be awarded for completing the task.
   */
  public GoldAction(int gold) {
    this.gold = gold;
  }

  /**
   * Gets the amount of gold awarded by the action, and returns it.
   *
   * @return The amount of gold awarded by the action.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Executes the gold action on the given player. Either removing
   * or adding gold to the player.
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

  @Override
  public String toString() {
    return "{Gold:" + getGold() + "}";
  }
}
