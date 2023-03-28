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
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 28, 2023.
 */
class PlayerTest {
  private Player player;

  @BeforeEach
  void setUp() {
    player = new Player("Name", 10, 2, 3);
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
      int expectedHealth = 10;
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
  @DisplayName("Change player information tests")
  class AddToPlayerInformationTests {
    @Test
    @DisplayName("Should increase health")
    void shouldIncreaseHealth() {
      player.increaseHealth(5);
      int expectedHealth = 15;
      int actualHealth = player.getHealth();
      assertEquals(expectedHealth, actualHealth);
    }

    @Test
    @DisplayName("Should decrease health")
    void shouldDecreaseHealth() {
      player.decreaseHealth(-5);
      int expectedHealth = 5;
      int actualHealth = player.getHealth();
      assertEquals(expectedHealth, actualHealth);
    }

    @Test
    @DisplayName("Should increase score")
    void shouldIncreaseScore() {
      player.increaseScore(1);
      int expectedScore = 3;
      int actualScore = player.getScore();
      assertEquals(expectedScore, actualScore);
    }

    @Test
    @DisplayName("Should decrease score")
    void shouldDecreaseScore() {
      player.decreaseScore(-1);
      int expectedScore = 1;
      int actualScore = player.getScore();
      assertEquals(expectedScore, actualScore);
    }

    @Test
    @DisplayName("Should increase gold")
    void shouldIncreaseGold() {
      player.increaseGold(1);
      int expectedGold = 4;
      int actualGold = player.getGold();
      assertEquals(expectedGold, actualGold);
    }

    @Test
    @DisplayName("Should decrease gold")
    void shouldDecreaseGold() {
      player.decreaseGold(-1);
      int expectedGold = 2;
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
  @DisplayName("Should not change player information tests")
  class DoesNotAddToPlayerInformationTests {
    @Test
    @DisplayName("Should not increase health throws IllegalArgumentException")
    void shouldNotIncreaseHealthThrowIllegalArgumentException(){
      int invalidHealth = -1;
      assertThrows(IllegalArgumentException.class, () -> player.increaseHealth(invalidHealth));
    }

    @Test
    @DisplayName("Should not decrease health throws IllegalArgumentException")
    void shouldNotDecreaseHealthThrowIllegalArgumentException(){
      int invalidHealth = 1;
      assertThrows(IllegalArgumentException.class, () -> player.decreaseHealth(invalidHealth));
    }

    @Test
    @DisplayName("Should not increase score throws IllegalArgumentException")
    void shouldNotIncreaseScoreThrowsIllegalArgumentException(){
      int invalidPoints = -1;
      assertThrows(IllegalArgumentException.class, () -> player.increaseScore(invalidPoints));
    }

    @Test
    @DisplayName("Should not decrease score throws IllegalArgumentException")
    void shouldNotDecreaseScoreThrowsIllegalArgumentException(){
      int invalidPoints = 1;
      assertThrows(IllegalArgumentException.class, () -> player.decreaseScore(invalidPoints));
    }

    @Test
    @DisplayName("Should not increase gold throws IllegalArgumentException")
    void shouldNotAddGoldThrowsIllegalArgumentException(){
      int invalidGold = -1;
      assertThrows(IllegalArgumentException.class, () -> player.increaseGold(invalidGold));
    }

    @Test
    @DisplayName("Should not decrease gold throws IllegalArgumentException")
    void shouldNotDecreaseGoldThrowsIllegalArgumentException(){
      int invalidGold = 1;
      assertThrows(IllegalArgumentException.class, () -> player.decreaseGold(invalidGold));
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