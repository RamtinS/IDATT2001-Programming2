package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

import java.util.Objects;

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
   * Executes the gold action on the given player.
   *
   * @param player The player that the action will be performed on.
   * @throws NullPointerException If the player is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    Objects.requireNonNull(player, "Player cannot be null");
    player.addGold(this.gold);
  }
}
