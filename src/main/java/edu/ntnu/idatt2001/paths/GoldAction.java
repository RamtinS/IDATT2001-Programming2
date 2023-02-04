package edu.ntnu.idatt2001.paths;

/**
 * The class represents a gold action.
 */
public class GoldAction implements Action{
    private final int gold;

    /**
     * Constructor to create an object of GoldAction
     *
     * @param gold The gold that will be awarded for completing the task
     */
    public GoldAction(int gold){
        this.gold = gold;
    }

    /**
     * Executes the gold action on the given player.
     *
     * @param player The player that the action will be performed on.
     */
    @Override
    public void execute(Player player) {
        player.addGold(getGold());
    }

    /**
     * Gets the amount of gold given by completing the task.
     * @return The amount of gold given by completing the task.
     */
    public int getGold(){
        return gold;
    }




}
