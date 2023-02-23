package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

/**
 * The class represents a health action.
 *
 * @author ...
 * @version JDK 17
 */
public class HealthAction implements Action {
  private final int health;

  /**
   * Constructor used to create an object of HealthAction.
   *
   * @param health The health change that is given to the player.
   */
  public HealthAction(int health) {

    this.health = health;
  }

  /**
   * Gets the health that will be awarded by this action.
   *
   * @return The health awarded by this action.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Performs the health action on the specified player. The action changes the
   * player`s health.
   *
   * @param player The player that the action is performed on.
   * @throws NullPointerException if the player is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    player.addHealth(this.health);
  }
}
