package edu.ntnu.idatt2001.paths;

import java.util.Objects;

public class InventoryAction {
    private final String item;

    public InventoryAction(String item) throws NullPointerException{
        Objects.requireNonNull(item);
        this.item = item;
    }

    public void execute(Player player){
        player.addToInventory(item);
    }
}
