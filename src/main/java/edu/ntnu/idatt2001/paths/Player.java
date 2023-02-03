package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The class represents a Player.
 *
 * @author ...
 * @version JDK 17
 */
public class Player {
    private final String name;
    private int health;
    private int score;
    private int gold;
    private final List<String> inventory;




    public Player(String name, int health, int score, int gold) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(name, "\nName cannot be null");

        if (health <=0) throw new IllegalArgumentException("\nHealth has to be greater than 0");
        if (score < 0) throw new IllegalArgumentException("\nScore cannot be a negative number");
        if (gold < 0) throw new IllegalArgumentException("\n gold cannot be a negative number");

        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
        this.inventory = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public void addHealth(int health) throws IllegalArgumentException{
        if (health <= 0) throw new IllegalArgumentException("Health increase has to be a positive number");
        this.health += health;
    }

    public int getHealth(){
        return health;
    }

    public void addScore(int points) throws IllegalArgumentException{
        if (points <= 0) throw new IllegalArgumentException("Point increase has to be positive");
        this.score += points;
    }

    public int getScore(){
        return score;
    }

    public void addGold(int gold) throws IllegalArgumentException{
        if (gold <= 0) throw new IllegalArgumentException("\nGold increase has to be positive.");
        this.gold += gold;
    }

    public int getGold(){
        return gold;
    }

    public void addToInventory(String item) throws NullPointerException, IllegalArgumentException{
        Objects.requireNonNull(item, "Item cannot be null");
        if (item.isBlank()) throw new IllegalArgumentException("Item cannot be blank");

        inventory.add(item);
    }

    public List<String> getInventory(){
        return inventory;
    }




}
