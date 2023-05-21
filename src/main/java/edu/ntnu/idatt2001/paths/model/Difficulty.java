package edu.ntnu.idatt2001.paths.model;

import java.util.Arrays;

/**
 * Enum representing the difficulties that a game can contain.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public enum Difficulty {

  EASY(100), MEDIUM(75), HARD(50);

  private final int health;

  /**
   * Difficulty constructor.
   *
   * @param health The start health level of the difficulty
   */
  Difficulty(int health) {
    this.health = health;
  }

  /**
   * Gets the health level of the selected difficulty.
   *
   * @return The health level of the selected difficulty.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Creates a difficulty from a String with the same text as the difficulty name.
   *
   * @param difficultyAsString The String representing the difficulty name.
   * @return A difficult that corresponds to the difficultyAsString parameter
   * @throws IllegalArgumentException if there are no difficulties that match the string.
   */
  public static Difficulty parseToDifficulty(String difficultyAsString)
      throws IllegalArgumentException {
    return Arrays.stream(Difficulty.values())
        .filter(difficulty -> difficulty.toString().equalsIgnoreCase(difficultyAsString))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid difficulty."));
  }

  @Override
  public String toString() {
    return name().charAt(0) + name().substring(1).toLowerCase();
  }
}