package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the HealthAction class
 *
 * @author ...
 * @version JDK 17
 */
class HealthActionTest {

  HealthAction healthAction;
  Player player;

  @BeforeEach
  void setUp() {
    healthAction = new HealthAction(10);
    player = new Player("Name", 20, 30, 40);
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
    int actual = player.getHealth();
    int expected = 20;
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should execute and add health")
  void shouldExecuteAndAddHealth() {
    healthAction.execute(player);
    int actual = player.getHealth();
    int expected = 30;
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should not execute throws NullPointerException")
  void shouldNotExecuteThrowsNullPointerException() {
    Player notValidPlayer = null;
    assertThrows(NullPointerException.class, () -> healthAction.execute(notValidPlayer));
  }
}