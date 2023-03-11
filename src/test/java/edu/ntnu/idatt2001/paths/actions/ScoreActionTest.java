package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the ScoreAction class.
 *
 * @author ...
 * @version JDK 17
 */
class ScoreActionTest {
  private ScoreAction scoreAction;
  private Player player;

  @BeforeEach
  void setUp() {
    scoreAction = new ScoreAction(10);
    player = new Player("Name", 10, 20, 30);
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput() {
    ScoreAction scoreActionTest = new ScoreAction(10);
    int expected = 10;
    int actual = scoreActionTest.getPoints();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test constructor invalid input throws IllegalArgumentException")
  void testConstructorInvalidInput() {
    int invalidPoints = -10;
    assertThrows(IllegalArgumentException.class, () -> new ScoreAction(invalidPoints));
  }

  @Test
  @DisplayName("Should get points")
  void shouldGetPoints() {
    int expectedPoints = 10;
    int actualPoints = scoreAction.getPoints();
    assertEquals(expectedPoints, actualPoints);
  }

  @Test
  @DisplayName("Should execute and add score")
  void shouldExecuteAndAddScore() {
    scoreAction.execute(player);
    int expectedScore = 30;
    int actualScore = player.getScore();
    assertEquals(expectedScore, actualScore);
  }

  @Test
  @DisplayName("Should not execute throws NullPointerException")
  void shouldNotExecuteThrowsNullPointerException() {
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> scoreAction.execute(invalidPlayer));
  }
}