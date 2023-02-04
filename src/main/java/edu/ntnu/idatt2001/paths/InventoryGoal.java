package edu.ntnu.idatt2001.paths;

import java.util.List;
import java.util.Objects;

public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  public List<String> getMandatoryItems() {
    return mandatoryItems;
  }

  @Override
  public boolean isFulfilled(Player player) {
    Objects.requireNonNull(player, "\nPlayer cannot be null");
    return player.getInventory().equals(getMandatoryItems());
  }
}
