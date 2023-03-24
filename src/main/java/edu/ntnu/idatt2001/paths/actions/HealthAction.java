package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

/**
 * The class represents an action where a player's health is
 * changed by a certain amount.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 24, 2023.
 */
public class HealthAction implements Action {
  private final int health;

  /**
   * Constructs a HealthAction object with the specified amount of health.
   *
   * @param health the amount of health that the player will gain or lose.
   */
  public HealthAction(int health) {
    this.health = health;
  }

  /**
   * The method retrieves the amount of health for this action.
   *
   * @return the amount of health
   */
  public int getHealth() {
    return health;
  }

  /**
   * The method performs the health action on the specified player.
   * The action changes the player`s health.
   *
   * @param player the player on which the action is performed.
   * @throws NullPointerException if the player object is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    if (getHealth() >= 0) {
      player.addHealth(getHealth());
    } else {
      player.decreaseHealth(getHealth());
    }
  }

  /**
   * The method return a string representation of the HealthAction.
   *
   * @return string representation of the HealthAction.
   */
  @Override
  public String toString() {
    return "{Health:" + getHealth() + "}";
  }
}
