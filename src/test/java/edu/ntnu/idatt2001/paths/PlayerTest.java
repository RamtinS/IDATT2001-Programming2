package edu.ntnu.idatt2001.paths;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Player class.
 */
class PlayerTest {

  Player player;
  @BeforeEach
  void setUp() {
    player = new Player("Name", 1, 2, 3);
  }

  @Test
  @DisplayName("Should get name")
  void shouldGetName() {
    String expected = "Name";
    String actual = player.getName();

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should add health")
  void addHealth() {
    player.addHealth(1);
    int expected = 2;
    int actual = player.getHealth();
    assertEquals(expected,actual);
  }

  @Test
  @DisplayName("Should not add health, throws IllegalArgumentException")
  void shouldNotAddHealthAndThrowIllegalArgumentException(){
    assertThrows(IllegalArgumentException.class, () -> player.addHealth(-1));

  }

  @Test
  @DisplayName("Should get health")
  void shouldGetHealth() {
    int expected = 1;
    int actual = player.getHealth();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should add score")
  void shouldAddScore() {
    player.addScore(1);
    int expected = 3;
    int actual = player.getScore();
    assertEquals(expected,actual);
  }

  @Test
  @DisplayName("Should not add score and throws IllegalArgumentException")
  void shouldNotAddScoreAndThrowsIllegalArgumentException(){
    assertThrows(IllegalArgumentException.class, () -> player.addScore(-1));
  }

  @Test
  @DisplayName("Should get score")
  void shouldGetScore() {
    int expected = 2;
    int actual = player.getScore();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should add gold")
  void shouldAddGold() {
    player.addGold(1);
    int expected = 4;
    int actual = player.getGold();
    assertEquals(expected,actual);

  }

  @Test
  @DisplayName("Should not add gold, throws IllegalArgumentException")
  void shouldNotAddGoldAndThrowsIllegalArgumentException(){
    assertThrows(IllegalArgumentException.class, () -> player.addGold(-1));
  }

  @Test
  @DisplayName("Should get gold")
  void shouldGetGold() {
    int expected = 3;
    int actual = player.getGold();
    assertEquals(expected,actual);
  }

  @Test
  @DisplayName("Should add to inventory")
  void shouldAddToInventory() {
    player.addToInventory("Sword");
    assertFalse(player.getInventory().isEmpty());
  }

  @Test
  @DisplayName("Should not add to inventory, throws IllegalArgumentException")
  void shouldNotAddToInventoryThrowsIllegalArgumentException(){
    assertThrows(IllegalArgumentException.class, () -> player.addToInventory(""));


  }
  @Test
  @DisplayName("Should not add to inventory, throws NullPointerException")
  void shouldNotAddToInventoryThrowsNullPointerException(){
    assertThrows(NullPointerException.class, () -> player.addToInventory(null));

  }

  @Test
  @DisplayName("Should get inventory")
  void shouldGetInventory() {
    List<String> expected = new ArrayList<>();
    expected.add("Sword");

    player.addToInventory("Sword");
    List<String> actual = player.getInventory();

    assertEquals(expected,actual);

  }
}