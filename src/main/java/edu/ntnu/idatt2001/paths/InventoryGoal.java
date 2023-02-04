package edu.ntnu.idatt2001.paths;

import java.util.List;
import java.util.Objects;

/**
 * The class represents an expected inventory of items.
 *
 * @author ...
 * @version JDK 17
 */
public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  /**
   * Constructor to create an object of the type InventoryGoal.
   *
   * @param mandatoryItems mandatory items for the player.
   */
  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  /**
   * The method retrieves a list of mandatory items.
   *
   * @return list of mandatory items.
   */
  public List<String> getMandatoryItems() {
    return mandatoryItems;
  }

  /**
   * The method checks if the player has the mandatory items.
   *
   * @param player the player assigned to the goal.
   * @return true or false depending on whether the goal is achieved.
   * @throws NullPointerException if the player is null.
   */
  @Override
  public boolean isFulfilled(Player player) throws NullPointerException {
    Objects.requireNonNull(player, "\nPlayer cannot be null");
    return player.getInventory().equals(getMandatoryItems());
  }
}
