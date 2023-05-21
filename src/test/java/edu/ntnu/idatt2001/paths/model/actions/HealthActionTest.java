package edu.ntnu.idatt2001.paths.model.actions;

import edu.ntnu.idatt2001.paths.model.Player;
import edu.ntnu.idatt2001.paths.model.actions.HealthAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the HealthAction class
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
class HealthActionTest {

  private HealthAction healthAction;
  private Player player;

  @BeforeEach
  void setUp() {
    healthAction = new HealthAction(10);
    player = new Player.PlayerBuilder("Name")
            .health(20)
            .build();
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput(){
    HealthAction healthActionTest = new HealthAction(10);
    int expected  = 10;
    int actual = healthActionTest.getHealth();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get health")
  void shouldGetHealth() {
    int expectedHealth = 20;
    int actualHealth = player.getHealth();
    assertEquals(expectedHealth, actualHealth);
  }

  @Test
  @DisplayName("Should execute and add health")
  void shouldExecuteAndAddHealth() {
    healthAction.execute(player);
    int expectedAmount = 30;
    int actualAmount = player.getHealth();
    assertEquals(expectedAmount, actualAmount);
  }

  @Test
  @DisplayName("Should execute and decrease health")
  void shouldExecuteAndDecreaseHealth() {
    HealthAction negativeHealthAction = new HealthAction(-10);
    negativeHealthAction.execute(player);
    int expectedAmount = 10;
    int actualAmount = player.getHealth();
    assertEquals(expectedAmount, actualAmount);
  }

  @Test
  @DisplayName("Should not execute throws NullPointerException")
  void shouldNotExecuteThrowsNullPointerException() {
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> healthAction.execute(invalidPlayer));
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    String expected = "{Health:10}";
    String actual = healthAction.toString();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test equals method")
  void testEqualsMethod() {
    HealthAction healthActionEqual = new HealthAction(10);
    assertEquals(healthAction, healthActionEqual);
  }
}