package edu.ntnu.idatt2001.paths;

public class GoldAction {
    private final int gold;

    public GoldAction(int gold) throws IllegalArgumentException{
        if (gold <=0) throw new IllegalArgumentException("Gold amount has to be larger than 0");
        this.gold = gold;
    }

    public void execute(Player player){
        player.addGold(gold);
    }




}
