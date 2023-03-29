package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a player in the game. It contains methods to manage
 * the player's attributes such as health, score, gold and inventory.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
public class Player {
  private final String name;
  private int health;
  private int score;
  private int gold;
  private final List<String> inventory;

  /**
   * Private constructor that constructs a Player object with PlayerBuilder.
   *
   * @param builder PlayerBuilder object with the values to be set for the Player object.
   */
  private Player(PlayerBuilder builder) {
    this.name = builder.name;
    this.health = builder.health;
    this.score = builder.score;
    this.gold = builder.gold;
    this.inventory = new ArrayList<>();
  }

  /**
   * The method retrieves the name of the player and returns it.
   *
   * @return The name of the player.
   */
  public String getName() {
    return this.name;
  }

  /**
   * The method increases the health of the player.
   *
   * @param health The amount of health to be added to the player.
   * @throws IllegalArgumentException If the change in health is not greater than zero.
   */
  public void increaseHealth(int health) throws IllegalArgumentException {
    if (health < 0) {
      throw new IllegalArgumentException("Health increase has to be a positive number");
    }
    if (getHealth() + health > 100) {
      this.health = 100;
    } else {
      this.health += health;
    }
  }

  /**
   * The method decreases the health of the player.
   *
   * @param health The amount of health to be decreased from the player.
   * @throws IllegalArgumentException if the change is greater than zero.
   */
  public void decreaseHealth(int health) throws IllegalArgumentException {
    if (health > 0) {
      throw new IllegalArgumentException("Health decrease has to be a negative number ");
    }
    this.health += health;
  }

  /**
   * The method retrieves the health of the player and returns it.
   *
   * @return The health of the player.
   */
  public int getHealth() {
    return health;
  }

  /**
   * The method increases the amount of points the player has.
   *
   * @param points The amount of points to be increased.
   * @throws IllegalArgumentException If the point increase is not a positive number.
   */
  public void increaseScore(int points) throws IllegalArgumentException {
    if (points < 0) {
      throw new IllegalArgumentException("Point increase has to be positive.");
    }
    this.score += points;
  }

  /**
   * The method decreases the amount of points the player has.
   *
   * @param points The amount of points to be decreased.
   * @throws IllegalArgumentException if the points or the result of the decrease is less than zero.
   */
  public void decreaseScore(int points) throws IllegalArgumentException {
    if (points > 0) {
      throw new IllegalArgumentException("Point decrease has to be negative");
    }
    if (getScore() + points < 0) {
      throw new IllegalArgumentException("Score cannot be less than zero.");
    }
    this.score += points;
  }

  /**
   * The method retrieves the score of the player and returns it.
   *
   * @return The current score of the player.
   */
  public int getScore() {
    return score;
  }

  /**
   * The method increases the amount of gold the player has.
   *
   * @param gold The amount of gold to be increased.
   * @throws IllegalArgumentException If the increase of gold is not a positive number.
   */
  public void increaseGold(int gold) throws IllegalArgumentException {
    if (gold < 0) {
      throw new IllegalArgumentException("\nGold increase has to be positive.");
    }
    this.gold += gold;
  }

  /**
   * The method decreases the amount of gold the player has.
   *
   * @param gold The amount of gold to be decreased.
   * @throws IllegalArgumentException if the gold or the result of the decrease is less than zero.
   */
  public void decreaseGold(int gold) throws IllegalArgumentException {
    if (gold > 0) {
      throw new IllegalArgumentException("Gold decrease has to be negative.");
    }
    if (getGold() + gold < 0) {
      throw new IllegalArgumentException("Gold cannot be less than zero");
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
   * The method adds an item to the inventory of the player.
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
   * The method retrieves the inventory of a player.
   *
   * @return The inventory of a player in the form of a list.
   */
  public List<String> getInventory() {
    return inventory;
  }

  /**
   * Builder class for the Player class. The class provides a way
   * to construct a Player object with optional parameters.
   */
  public static class PlayerBuilder {
    private final String name;
    private int health = 100;
    private int score = 0;
    private int gold = 0;

    /**
     * Constructs a PlayerBuilder object with the given name.
     *
     * @param name the name of the player to be created.
     */
    public PlayerBuilder(String name) {
      this.name = name;
    }

    /**
     * The method sets the health of the player.
     *
     * @param health the value of the health.
     * @return this builder object.
     */
    public PlayerBuilder health(int health) {
      this.health = health;
      return this;
    }

    /**
     * The method sets the score of the player.
     *
     * @param score the value of the score.
     * @return this builder object.
     */
    public PlayerBuilder score(int score) {
      this.score = score;
      return this;
    }

    /**
     * The method sets the amount of gold the player has.
     *
     * @param gold the amount of gold.
     * @return this builder object.
     */
    public PlayerBuilder gold(int gold) {
      this.gold = gold;
      return this;
    }

    /**
     * Constructs a Player object with the set values.
     *
     * @return a player object.
     * @throws NullPointerException if the name is null.
     * @throws IllegalArgumentException if the attributes do not meet the requirements.
     */
    public Player build() throws NullPointerException, IllegalArgumentException {
      validate();
      return new Player(this);
    }

    /**
     * The method validates the values of the attributes of the Player
     * object built by the builder.
     *
     * @throws NullPointerException if name is null.
     * @throws IllegalArgumentException if the attributes do not meet the requirements.
     */
    private void validate() throws NullPointerException, IllegalArgumentException {
      if (name == null) {
        throw new NullPointerException("Name cannot be null");
      }
      if (name.isBlank()) {
        throw new IllegalArgumentException("Name cannot be blank");
      }
      if (health <= 0 || health > 100) {
        throw new IllegalArgumentException("Health has to be greater than zero and"
                + " less than or equal to one hundred.");
      }
      if (score < 0) {
        throw new IllegalArgumentException("Score cannot be a negative number");
      }
      if (gold < 0) {
        throw new IllegalArgumentException("Gold cannot be a negative number");
      }
    }
  }
}
