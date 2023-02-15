package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldActionTest {

  GoldAction goldAction;
  Player player;

  @BeforeEach
  void setUp() {
    goldAction = new GoldAction(10);
    player = new Player("Name", 20, 30, 40);
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput(){
    GoldAction goldActionTest = new GoldAction(10);
    int expected = 10;
    int actual = goldActionTest.getGold();

    assertEquals(expected, actual);
  }


  @Test
  @DisplayName("Should execute and add gold")
  void shouldExecuteAndAddGold() {
    goldAction.execute(player);
    int actual = player.getGold();
    int expected = 50;
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get gold")
  void shouldGetGold(){
    int actual = player.getGold();
    int expected = 40;

    assertEquals(expected, actual);
  }


}