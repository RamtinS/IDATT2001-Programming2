package edu.ntnu.idatt2001.paths.model;

import edu.ntnu.idatt2001.paths.model.Difficulty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Difficulty enum.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 12, 2023.
 */
class DifficultyTest {

  @Test
  @DisplayName("Test get health")
  void testGetHealth() {
    assertEquals(100, Difficulty.EASY.getHealth());
    assertEquals(75, Difficulty.MEDIUM.getHealth());
    assertEquals(50, Difficulty.HARD.getHealth());
  }

  @Test
  @DisplayName("Test parse to difficulty valid input")
  void testParseToDifficultyValidInput() {
    assertEquals(Difficulty.EASY, Difficulty.parseToDifficulty("EASY"));
    assertEquals(Difficulty.MEDIUM, Difficulty.parseToDifficulty("MEDIUM"));
    assertEquals(Difficulty.HARD, Difficulty.parseToDifficulty("HARD"));
  }

  @Test
  @DisplayName("Test parse to difficulty invalid input throws IllegalArgumentException")
  void testParseToDifficultyInvalidInputIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> Difficulty.parseToDifficulty("INVALID"));
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    assertEquals("Easy", Difficulty.EASY.toString());
    assertEquals("Medium", Difficulty.MEDIUM.toString());
    assertEquals("Hard", Difficulty.HARD.toString());
  }
}