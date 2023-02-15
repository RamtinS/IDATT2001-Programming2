package edu.ntnu.idatt2001.paths;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Player class.
 *
 * @author ...
 * @version JDK 17
 */
class PlayerTest {

  Player player;
  @BeforeEach
  void setUp() {
    player = new Player("Name", 1, 2, 3);
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTests {
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
    @DisplayName("Test constructor with invalid input throws NullPointerException")
    void testConstructorWithInvalidInputThrowsNullPointerException(){
      String invalidName = null;
      int validHealth = 1;
      int validGold = 2;
      int validScore = 3;
      assertThrows(NullPointerException.class, () -> new Player(invalidName, validHealth, validScore, validGold));
    }

    @Test
    @DisplayName("Test constructor with invalid input throws IllegalArgumentException")
    void testConstructorWithInvalidInputThrowsIllegalArgumentException(){
      String validName = "testName";
      int validHealth = 1;
      int validGold = 2;
      int validScore = 3;
      int invalidHealth = -1;
      int invalidGold = -1;
      int invalidScore = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player(validName, invalidHealth, validGold, validScore));
      assertThrows(IllegalArgumentException.class, () -> new Player(validName, validHealth, invalidGold, validScore));
      assertThrows(IllegalArgumentException.class, () -> new Player(validName, validHealth, validGold, invalidScore));
    }
  }

  @Nested
  @DisplayName("Player information tests")
  class PlayerInformationTests {
    @Test
    @DisplayName("Should get name")
    void shouldGetName() {
      String expected = "Name";
      String actual = player.getName();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get health")
    void shouldGetHealth() {
      int expected = 1;
      int actual = player.getHealth();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get score")
    void shouldGetScore() {
      int expected = 2;
      int actual = player.getScore();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should get gold")
    void shouldGetGold() {
      int expected = 3;
      int actual = player.getGold();
      assertEquals(expected, actual);
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

  @Nested
  @DisplayName("Add to player information tests")
  class AddToPlayerInformationTests {
    @Test
    @DisplayName("Should add health")
    void addHealth() {
      player.addHealth(1);
      int expected = 2;
      int actual = player.getHealth();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should add score")
    void shouldAddScore() {
      player.addScore(1);
      int expected = 3;
      int actual = player.getScore();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should add gold")
    void shouldAddGold() {
      player.addGold(1);
      int expected = 4;
      int actual = player.getGold();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should add to inventory")
    void shouldAddToInventory() {
      player.addToInventory("Sword");
      assertFalse(player.getInventory().isEmpty());
    }
  }

  @Nested
  @DisplayName("Does not add to player information tests")
  class DoesNotAddToPlayerInformationTests {
    @Test
    @DisplayName("Should not add health throws IllegalArgumentException")
    void shouldNotAddHealthThrowIllegalArgumentException(){
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-1));
    }

    @Test
    @DisplayName("Should not add score throws IllegalArgumentException")
    void shouldNotAddScoreThrowsIllegalArgumentException(){
      int points = -1;
      assertThrows(IllegalArgumentException.class, () -> player.addScore(points));
    }

    @Test
    @DisplayName("Should not add gold throws IllegalArgumentException")
    void shouldNotAddGoldThrowsIllegalArgumentException(){
      int gold = -1;
      assertThrows(IllegalArgumentException.class, () -> player.addGold(gold));
    }

    @Test
    @DisplayName("Should not add to inventory throws IllegalArgumentException")
    void shouldNotAddToInventoryThrowsIllegalArgumentException(){
      String item = "";
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(item));
    }

    @Test
    @DisplayName("Should not add to inventory throws NullPointerException")
    void shouldNotAddToInventoryThrowsNullPointerException(){
      String item = null;
      assertThrows(NullPointerException.class, () -> player.addToInventory(item));
    }
  }
}