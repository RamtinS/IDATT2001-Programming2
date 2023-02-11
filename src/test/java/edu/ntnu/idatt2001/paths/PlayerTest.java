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
@DisplayName("Test constructor with invalid input throws IllegalArgumentException")
  void testConstructorWithInvalidInputThrowsIllegalArgumentException(){
    String validName = "testName";
    int validHealth = 1;
    int validGold = 2;
    int validScore = 3;

    int inValidHealth = -1;
    int inValidGold = -1;
    int inValidScore = -1;

    assertThrows(IllegalArgumentException.class, () -> new Player(validName, inValidHealth, validGold, validScore));
    assertThrows(IllegalArgumentException.class, () -> new Player(validName, validHealth, inValidGold, validScore));
    assertThrows(IllegalArgumentException.class, () -> new Player(validName, validHealth, validGold, inValidScore));
}

@Test
@DisplayName("Test constructor with invalid input throws NullPointerException")
  void testConstructorWithInvalidInputThrowsNullPointerException(){
  String inValidName = null;
  int validHealth = 1;
  int validGold = 2;
  int validScore = 3;
  assertThrows(NullPointerException.class, () -> new Player(inValidName, validHealth, validScore, validGold));
}

@Test
@DisplayName("Test constructor with valid input")
  void testConstructorWithValidInput(){
    String validName = "testName";
    int validHealth = 1;
    int validGold = 2;
    int validScore = 3;

    Player player = new Player(validName, validHealth, validScore, validGold);
    assertEquals(validHealth, player.getHealth());
    assertEquals(validGold, player.getGold());
    assertEquals(validScore, player.getScore());
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
    String item = "";
    assertThrows(IllegalArgumentException.class, () -> player.addToInventory(item));
  }

  @Test
  @DisplayName("Should not add to inventory, throws NullPointerException")
  void shouldNotAddToInventoryThrowsNullPointerException(){
    String item = null;
    assertThrows(NullPointerException.class, () -> player.addToInventory(item));
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