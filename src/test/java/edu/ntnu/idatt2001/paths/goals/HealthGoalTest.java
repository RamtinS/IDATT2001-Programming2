package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the HealthGoal class.
 *
 * @author ...
 * @version JDK 17
 */
class HealthGoalTest {

  HealthGoal healthGoal;

  @BeforeEach
  void setUp() {
    healthGoal = new HealthGoal(10);
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput() {
    int minimumHealth = 5;
    HealthGoal testHealthGoalConstructor = new HealthGoal(minimumHealth);
    assertEquals(minimumHealth, testHealthGoalConstructor.getMinimumHealth());
  }

  @Test
  @DisplayName("Test constructor invalid input throws IllegalArgumentException")
  void testConstructorInvalidInputThrowsIllegalArgumentException() {
    int invalidMinimumHealth = -5;
    assertThrows(IllegalArgumentException.class, () -> new HealthGoal(invalidMinimumHealth));
  }

  @Test
  @DisplayName("Should get minimum health")
  void shouldGetMinimumHealth() {
    int expectedHealth = 10;
    int actualHealth = healthGoal.getMinimumHealth();
    assertEquals(expectedHealth, actualHealth);
  }

  @Test
  @DisplayName("Goal is fulfilled")
  void goalIsFulfilled() {
    Player player = new Player("Test name", 100, 100, 50);
    assertTrue(healthGoal.isFulfilled(player));
  }

  @Test
  @DisplayName("Goal is not fulfilled")
  void goalIsNotFulfilled() {
    Player player = new Player("Test name", 5, 100, 50);
    assertFalse(healthGoal.isFulfilled(player));
  }

  @Test
  @DisplayName("Goal is not fulfilled throws NullPointerException")
  void goalIsNotFulfilledThrowsNullPointerException() {
    Player player = null;
    assertThrows(NullPointerException.class, () -> healthGoal.isFulfilled(player));
  }
}