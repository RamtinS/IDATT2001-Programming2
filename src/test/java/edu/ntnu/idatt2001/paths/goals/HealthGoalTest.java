package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
  @DisplayName("Should check if goal is fulfilled")
  void shouldCheckIfGoalIsFulfilled() {
    Player player = new Player("Test name", 100, 100, 50);
    assertTrue(healthGoal.isFulfilled(player));
  }

  @Test
  @DisplayName("Goal is not fulfilled throws NullPointerException")
  void goalIsNotFulfilledThrowsNullPointerException() {
    Player player = null;
    assertThrows(NullPointerException.class, () -> healthGoal.isFulfilled(player));
  }
}