package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

import java.util.Objects;

/**
 * The class represents an inventory action.
 *
 * @author ...
 * @version JDK 17
 */
public class InventoryAction implements Action {
  private final String item;

  /**
   * Constructor for an InventoryAction object.
   *
   * @param item The item that will be awarded to the player completing the action.
   * @throws NullPointerException If the item given is null.
   */
  public InventoryAction(String item) throws IllegalArgumentException, NullPointerException {
    if (item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be blank");
    }
    this.item = Objects.requireNonNull(item, "Item cannot be null");
  }

  /**
   * Gets the item that the action will add to the players inventory.
   *
   * @return The item that will be added to the player's inventory by this action.
   */
  public String getItem() {
    return item;
  }

  /**
   * Performs the action, adding the item to the inventory of
   * the specified player.
   *
   * @param player The player that will receive the item.
   * @throws NullPointerException if the player is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    player.addToInventory(this.item);
  }
}
