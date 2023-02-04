package edu.ntnu.idatt2001.paths;

import java.util.Objects;

/**
 * The class represents a health action.
 */
public class HealthAction implements Action {
    private final int health;

    /**
     * Constructor used to create an object of HealthAction.
     * @param health The health change that is given to the player.
     */
    public HealthAction(int health){

        this.health = health;
    }

    /**
     * Performs the health action on the specified player. The action changes the
     * player`s health.
     * @param player The player that the action is performed on.
     */
    @Override
    public void execute(Player player) throws NullPointerException {
        Objects.requireNonNull(player, "Player cannot be null");
        player.addHealth(getHealth());
    }

    /**
     * Gets the change in health given by the task.
     * @return The change in health given by the task.
     */
    public int getHealth(){
        return health;
    }




}
