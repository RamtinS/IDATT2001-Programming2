package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;

import java.util.ArrayList;
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
    Objects.requireNonNull(mandatoryItems, "\nMandatory items cannot be null.");
    this.mandatoryItems = new ArrayList<>();
    this.mandatoryItems.addAll(mandatoryItems);
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
    return player.getInventory().containsAll(mandatoryItems);
  }
}
