package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the HealthGoal class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
class HealthGoalTest {
  private HealthGoal healthGoal;

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
    Player validPlayer  = new Player.PlayerBuilder("Test name")
            .health(100)
            .build();
    assertTrue(healthGoal.isFulfilled(validPlayer));
  }

  @Test
  @DisplayName("Goal is not fulfilled")
  void goalIsNotFulfilled() {
    Player invalidPlayer  = new Player.PlayerBuilder("Test name")
            .health(5)
            .build();
    assertFalse(healthGoal.isFulfilled(invalidPlayer));
  }

  @Test
  @DisplayName("Goal is not fulfilled throws NullPointerException")
  void goalIsNotFulfilledThrowsNullPointerException() {
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> healthGoal.isFulfilled(invalidPlayer));
  }

  @Test
  @DisplayName("Test equals method")
  void testEqualsMethod() {
    HealthGoal healthGoalEqual = new HealthGoal(10);
    assertEquals(healthGoal, healthGoalEqual);
  }
}