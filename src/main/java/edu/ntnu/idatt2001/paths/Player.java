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


  /**
   * Constructor for an object of the Player class.
   *
   * @param name The name of the player.
   * @param health The starting health of the player.
   * @param score The starting score of the player.
   * @param gold The amount of gold the player starts with.
   * @throws NullPointerException If the name of the player is null.
   * @throws IllegalArgumentException If the health is not greater than zero, if the score
   *        is a negative number or if the amount of gold is a negative number.
   */
  public Player(String name, int health, int score, int gold)
          throws IllegalArgumentException, NullPointerException {
    if (name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be blank");
    }
    if (health <= 0) {
      throw new IllegalArgumentException("Health has to be greater than 0");
    }
    if (score < 0) {
      throw new IllegalArgumentException("Score cannot be a negative number");
    }
    if (gold < 0) {
      throw new IllegalArgumentException("Gold cannot be a negative number");
    }
    this.name = Objects.requireNonNull(name, "Name cannot be null");
    this.health = health;
    this.score = score;
    this.gold = gold;
    this.inventory = new ArrayList<>();
  }

  /**
   * Retrieves the name of the player and returns it.
   *
   * @return The name of the player.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Adds health to the player.
   *
   * @param health The amount of health to be added to the player.
   * @throws IllegalArgumentException If the change in health is not greater
   *        than 0.
   */
  public void addHealth(int health) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException("Health increase has"
              + " to be a positive number");
    }
    this.health += health;
  }

  /**
   * Retrieves the health of the player and returns it.
   *
   * @return The health of the player.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Adds points to the score of the player.
   *
   * @param points The amount of points to be added.
   * @throws IllegalArgumentException If the point increase is not a positive number.
   */
  public void addScore(int points) throws IllegalArgumentException {
    if (points <= 0) {
      throw new IllegalArgumentException("Point increase has to be positive");
    }
    this.score += points;
  }

  /**
   * Retrieves the score of the player and returns it.
   *
   * @return The current score of the player.
   */
  public int getScore() {
    return score;
  }

  /**
   * Adds gold to the amount of gold the player has.
   *
   * @param gold The amount of gold to be added.
   * @throws IllegalArgumentException If the increase of gold is not a positive number.
   */
  public void addGold(int gold) throws IllegalArgumentException {
    if (gold <= 0) {
      throw new IllegalArgumentException("\nGold increase has to be positive.");
    }
    this.gold += gold;
  }

  /**
   * Retrieves the amount of gold the player has and returns it.
   *
   * @return The amount of gold that the player has.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Adds an item to the inventory of the player.
   *
   * @param item The item to be added to the inventory.
   * @throws NullPointerException If the item is null.
   * @throws IllegalArgumentException If the String describing the item is blank.
   */
  public void addToInventory(String item) throws NullPointerException, IllegalArgumentException {
    if (item == null) {
      throw new NullPointerException("Item cannot be null");
    }
    if (item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be blank");
    }
    inventory.add(item);
  }

  /**
   * Retrieves the inventory of a player.
   *
   * @return The inventory of a player in the form of a list.
   */
  public List<String> getInventory() {
    return inventory;
  }




}
