package edu.ntnu.idatt2001.paths;

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
  public InventoryAction(String item) throws NullPointerException {
    Objects.requireNonNull(item);
    this.item = item;
  }

  /**
   * Performs the action, adding the item to the inventory of
   * the specified player.
   *
   * @param player The player that will receive the item.
   */
  @Override
  public void execute(Player player) {
    player.addToInventory(getItem());
  }

  /**
   * Gets the item given by the InventoryAction.
   *
   * @return The item that will be awarded for completing the action.
   */
  public String getItem() {
    return item;
  }
}
