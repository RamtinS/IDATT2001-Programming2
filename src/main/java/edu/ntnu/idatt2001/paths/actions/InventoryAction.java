package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.model.Player;
import java.util.Objects;

/**
 * The class represents an action that adds an item to
 * the player's inventory.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 28, 2023.
 */
public class InventoryAction implements Action {
  private final String item;

  /**
   * Constructs a InventoryAction object.
   *
   * @param item The item that will be awarded to the player completing the action.
   * @throws NullPointerException If the item given is null.
   */
  public InventoryAction(String item) throws IllegalArgumentException, NullPointerException {
    if (item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be blank");
    }
    this.item = Objects.requireNonNull(item.trim(), "Item cannot be null");
  }

  /**
   * The method retrieves the item that the action will add to the players inventory.
   *
   * @return The item that will be added to the player's inventory by this action.
   */
  public String getItem() {
    return item;
  }

  /**
   * The method performs the inventory action, adding the item to the inventory of
   * the specified player.
   *
   * @param player The player that will receive the item.
   * @throws NullPointerException if the player object is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    player.addToInventory(this.item);
  }

  /**
   * The method return a string representation of the InventoryAction.
   *
   * @return string representation of the InventoryAction.
   */
  @Override
  public String toString() {
    return "{Inventory:" + getItem() + "}";
  }

  /**
   * The method checks for equality between InventoryAction objects.
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
    InventoryAction that = (InventoryAction) o;
    return getItem().equals(that.getItem());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getItem());
  }
}
