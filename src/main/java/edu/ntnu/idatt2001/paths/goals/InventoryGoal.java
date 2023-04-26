package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents an inventory goal for the player.
 * To achieve the goal, the player must obtain all the
 * mandatoryItems.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  /**
   * Constructor to create an object of the type InventoryGoal.
   *
   * @param mandatoryItems mandatory items for the player.
   */
  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = new ArrayList<>();
    this.mandatoryItems.addAll(Objects.requireNonNull(mandatoryItems,
            "Mandatory items cannot be null."));
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
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    return player.getInventory().containsAll(getMandatoryItems());
  }

  /**
   * The method checks for equality between InventoryGoal objects.
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
    InventoryGoal that = (InventoryGoal) o;
    return Objects.equals(getMandatoryItems(), that.getMandatoryItems());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getMandatoryItems());
  }
}
