package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the ScoreGoal class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
class ScoreGoalTest {

  private ScoreGoal scoreGoal;

  @BeforeEach
  void setUp() {
    scoreGoal = new ScoreGoal(50);
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput() {
    int minimumPoints = 80;
    ScoreGoal testScoreGoalConstructor = new ScoreGoal(minimumPoints);
    assertEquals(minimumPoints, testScoreGoalConstructor.getMinimumPoints());
  }

  @Test
  @DisplayName("Test constructor invalid input throws IllegalArgumentException")
  void testConstructorInvalidInputThrowsIllegalArgumentException() {
    int invalidMinimumPoints = -80;
    assertThrows(IllegalArgumentException.class, () -> new ScoreGoal(invalidMinimumPoints));
  }

  @Test
  @DisplayName("Should get minimum points")
  void shouldGetMinimumPoints() {
    int expectedScore = 50;
    int actualScore = scoreGoal.getMinimumPoints();
    assertEquals(expectedScore, actualScore);
  }

  @Test
  @DisplayName("Goal is fulfilled")
  void goalIsFulfilled() {
    Player validPlayer = new Player.PlayerBuilder("Test name")
            .score(60)
            .build();
    assertTrue(scoreGoal.isFulfilled(validPlayer));
  }

  @Test
  @DisplayName("Goal is not fulfilled")
  void goalIsNotFulfilled() {
    Player invalidPlayer = new Player.PlayerBuilder("Test name")
            .score(40)
            .build();
    assertFalse(scoreGoal.isFulfilled(invalidPlayer));
  }

  @Test
  @DisplayName("Goal is not fulfilled throws NullPointerException")
  void goalIsNotFulfilledThrowsNullPointerException() {
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> scoreGoal.isFulfilled(invalidPlayer));
  }

  @Test
  @DisplayName("Test equals method")
  void testEqualsMethod() {
    ScoreGoal scoreGoalEqual = new ScoreGoal(50);
    assertEquals(scoreGoal, scoreGoalEqual);
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    String expected = "Score goal:50";
    String actual = scoreGoal.toString();
    assertEquals(expected, actual);
  }
}