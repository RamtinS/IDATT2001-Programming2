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
      String validName = "Test name";
      int validHealth = 1;
      int validGold = 2;
      int validScore = 3;
      Player player = new Player(validName, validHealth, validScore, validGold);
      assertEquals(validName, player.getName());
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
      String validName = "Test name";
      String invalidName = "";
      int validHealth = 1;
      int validGold = 2;
      int validScore = 3;
      int invalidHealth = -1;
      int invalidGold = -1;
      int invalidScore = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player(invalidName, validHealth, validGold, validScore));
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
      String expectedName = "Name";
      String actualName = player.getName();
      assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Should get health")
    void shouldGetHealth() {
      int expectedHealth = 1;
      int actualHealth = player.getHealth();
      assertEquals(expectedHealth, actualHealth);
    }

    @Test
    @DisplayName("Should get score")
    void shouldGetScore() {
      int expectedScore = 2;
      int actualScore = player.getScore();
      assertEquals(expectedScore, actualScore);
    }

    @Test
    @DisplayName("Should get gold")
    void shouldGetGold() {
      int expectedGold = 3;
      int actualGold = player.getGold();
      assertEquals(expectedGold, actualGold);
    }

    @Test
    @DisplayName("Should get inventory")
    void shouldGetInventory() {
      List<String> expectedInventory = new ArrayList<>();
      expectedInventory.add("Sword");
      player.addToInventory("Sword");
      List<String> actualInventory = player.getInventory();
      assertEquals(expectedInventory,actualInventory);
    }
  }

  @Nested
  @DisplayName("Add to player information tests")
  class AddToPlayerInformationTests {
    @Test
    @DisplayName("Should add health")
    void addHealth() {
      player.addHealth(1);
      int expectedHealth = 2;
      int actualHealth = player.getHealth();
      assertEquals(expectedHealth, actualHealth);
    }

    @Test
    @DisplayName("Should add score")
    void shouldAddScore() {
      player.addScore(1);
      int expectedScore = 3;
      int actualScore = player.getScore();
      assertEquals(expectedScore, actualScore);
    }

    @Test
    @DisplayName("Should add gold")
    void shouldAddGold() {
      player.addGold(1);
      int expectedGold = 4;
      int actualGold = player.getGold();
      assertEquals(expectedGold, actualGold);
    }

    @Test
    @DisplayName("Should add to inventory")
    void shouldAddToInventory() {
      player.addToInventory("Sword");
      assertFalse(player.getInventory().isEmpty());
    }
  }

  @Nested
  @DisplayName("Should not add to player information tests")
  class DoesNotAddToPlayerInformationTests {
    @Test
    @DisplayName("Should not add health throws IllegalArgumentException")
    void shouldNotAddHealthThrowIllegalArgumentException(){
      int invalidHealth = -1;
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(invalidHealth));
    }

    @Test
    @DisplayName("Should not add score throws IllegalArgumentException")
    void shouldNotAddScoreThrowsIllegalArgumentException(){
      int invalidPoints = -1;
      assertThrows(IllegalArgumentException.class, () -> player.addScore(invalidPoints));
    }

    @Test
    @DisplayName("Should not add gold throws IllegalArgumentException")
    void shouldNotAddGoldThrowsIllegalArgumentException(){
      int invalidGold = -1;
      assertThrows(IllegalArgumentException.class, () -> player.addGold(invalidGold));
    }

    @Test
    @DisplayName("Should not add to inventory throws IllegalArgumentException")
    void shouldNotAddToInventoryThrowsIllegalArgumentException(){
      String invalidItem = "";
      assertThrows(IllegalArgumentException.class, () -> player.addToInventory(invalidItem));
    }

    @Test
    @DisplayName("Should not add to inventory throws NullPointerException")
    void shouldNotAddToInventoryThrowsNullPointerException(){
      String invalidItem = null;
      assertThrows(NullPointerException.class, () -> player.addToInventory(invalidItem));
    }
  }
}