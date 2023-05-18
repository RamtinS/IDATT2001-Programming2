package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the GoldAction class
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
class GoldActionTest {
  private GoldAction goldAction;
  private Player player;

  @BeforeEach
  void setUp() {
    goldAction = new GoldAction(10);
    player = new Player.PlayerBuilder("Name")
            .gold(40)
            .build();
  }

  @Test
  @DisplayName("Test constructor")
  void testConstructor(){
    GoldAction testGoldActionConstructor = new GoldAction(10);
    int expected = 10;
    int actual = testGoldActionConstructor.getGold();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get gold")
  void shouldGetGold(){
    int expectedGold = 40;
    int actualGold = player.getGold();
    assertEquals(expectedGold, actualGold);
  }

  @Test
  @DisplayName("Should execute and add gold")
  void shouldExecuteAndAddGold() {
    goldAction.execute(player);
    int expectedAmount = 50;
    int actualAmount = player.getGold();
    assertEquals(expectedAmount, actualAmount);
  }

  @Test
  @DisplayName("Should execute and decrease gold")
  void shouldExecuteAndDecreaseGold() {
    GoldAction negativeGoldAction = new GoldAction(-10);
    negativeGoldAction.execute(player);
    int expectedAmount = 30;
    int actualAmount = player.getGold();
    assertEquals(expectedAmount, actualAmount);
  }

  @Test
  @DisplayName("Should not execute throws NullPointerException")
  void shouldNotExecuteThrowsNullPointerException() {
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> goldAction.execute(invalidPlayer));
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    String expected = "{Gold:10}";
    String actual = goldAction.toString();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test equals method")
  void testEqualsMethod() {
    GoldAction goldActionEqual = new GoldAction(10);
    assertEquals(goldAction, goldActionEqual);
  }
}