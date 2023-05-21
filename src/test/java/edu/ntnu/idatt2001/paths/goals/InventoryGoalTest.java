package edu.ntnu.idatt2001.paths.goals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2001.paths.model.Player;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2001.paths.model.goals.InventoryGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The class tests the InventoryGoal class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
class InventoryGoalTest {

  private InventoryGoal inventoryGoal;
  private Player player;
  private List<String> mandatoryItems;

  @BeforeEach
  void setUp() {
    mandatoryItems = new ArrayList<>();
    mandatoryItems.add("Sword");
    mandatoryItems.add("Hammer");
    inventoryGoal = new InventoryGoal(mandatoryItems);
    player = new Player.PlayerBuilder("Test name").build();
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
  @DisplayName("Should get list of mandatory items")
  void shouldGetListMandatoryItems() {
    List<String> expectedMandatoryItems = new ArrayList<>();
    expectedMandatoryItems.add("Sword");
    expectedMandatoryItems.add("Hammer");
    List<String> actualMandatoryItems = inventoryGoal.getMandatoryItems();
    assertEquals(expectedMandatoryItems, actualMandatoryItems);
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
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> inventoryGoal.isFulfilled(invalidPlayer));
  }

  @Test
  @DisplayName("Test equals method")
  void testEqualsMethod() {
    InventoryGoal inventoryGoalEqual = new InventoryGoal(mandatoryItems);
    assertEquals(inventoryGoal, inventoryGoalEqual);
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    String expected = """
        Inventory goal:\s
        Sword
        Hammer""";
    String actual = inventoryGoal.toString();
    assertEquals(expected, actual);
  }
}