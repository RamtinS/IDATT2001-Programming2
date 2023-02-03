package edu.ntnu.idatt2001.paths;

public class HealthAction {
    private final int health;

    public HealthAction(int health){
        if (health <= 0) throw new IllegalArgumentException("\nHealth has to be a positive number");
        this.health = health;
    }

    public void execute(Player player){
        player.addHealth(health);
    }




}
