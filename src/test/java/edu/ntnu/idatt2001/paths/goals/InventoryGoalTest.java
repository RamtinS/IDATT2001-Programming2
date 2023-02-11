package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the InventoryGoal class.
 *
 * @author ...
 * @version JDK 17
 */
class InventoryGoalTest {

  InventoryGoal inventoryGoal;
  List<String> mandatoryItems;
  Player player;

  @BeforeEach
  void setUp() {
    mandatoryItems = new ArrayList<>();
    mandatoryItems.add("Sword");
    mandatoryItems.add("Hammer");
    inventoryGoal = new InventoryGoal(mandatoryItems);
    player = new Player("Test name", 100, 100, 50);
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput() {
    List<String> testList = new ArrayList<>();
    InventoryGoal testInventoryGoalConstructor = new InventoryGoal(testList);
    assertEquals(testList, testInventoryGoalConstructor.getMandatoryItems());
  }

  @Test
  @DisplayName("Test constructor invalid input throws NullPointerException")
  void testConstructorInvalidInputThrowsNullPointerException() {
    List<String> invalidList = null;
    assertThrows(NullPointerException.class, () -> new InventoryGoal(invalidList));
  }

  @Test
  @DisplayName("Goal is fulfilled")
  void goalIsFulfilled() {
    player.addToInventory("Sword");
    player.addToInventory("Hammer");
    assertTrue(inventoryGoal.isFulfilled(player));
  }

  @Test
  @DisplayName("Goal is not fulfilled")
  void goalIsNotFulfilled() {
    player.addToInventory("Pickaxe");
    assertFalse(inventoryGoal.isFulfilled(player));
  }

  @Test
  @DisplayName("Goal is not fulfilled throws NullPointerException")
  void goalIsNotFulfilledThrowsNullPointerException() {
    Player player = null;
    assertThrows(NullPointerException.class, () -> inventoryGoal.isFulfilled(player));
  }
}