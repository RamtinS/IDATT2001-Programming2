package edu.ntnu.idatt2001.paths.model.actions;

import edu.ntnu.idatt2001.paths.model.Player;
import edu.ntnu.idatt2001.paths.model.actions.InventoryAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the InventoryAction class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 29, 2023.
 */
class InventoryActionTest {
  private InventoryAction inventoryAction;
  private Player player;

  @BeforeEach
  void setUp() {
    inventoryAction = new InventoryAction("Test Item");
    player = new Player.PlayerBuilder("Name")
            .build();
  }

  @Test
  @DisplayName("Test constructor valid input")
  void testConstructorValidInput() {
    InventoryAction inventoryActionTest = new InventoryAction("item");
    String expected = "item";
    String actual = inventoryActionTest.getItem();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test constructor invalid input throws IllegalArgumentException")
  void testConstructorInvalidInputThrowsIllegalArgumentException() {
    String invalidItem = "";
    assertThrows(IllegalArgumentException.class, () -> new InventoryAction(invalidItem));
  }

  @Test
  @DisplayName("Test constructor invalid input throws NullPointerException")
  void testConstructorInvalidInputThrowsNullPointerException() {
    String invalidItem = null;
    assertThrows(NullPointerException.class, () -> new InventoryAction(invalidItem));
  }

  @Test
  @DisplayName("Should get item")
  void shouldGetItem() {
    String actual = inventoryAction.getItem();
    String expected = "Test Item";
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should execute and add to inventory")
  void shouldExecuteAndAddToInventory() {
    inventoryAction.execute(player);
    String expected = "Test Item";
    String actual = player.getInventory().get(0);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should not execute throws NullPointerException")
  void shouldNotExecuteThrowsNullPointerException() {
    Player invalidPlayer = null;
    assertThrows(NullPointerException.class, () -> inventoryAction.execute(invalidPlayer));
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    String expected = "{Inventory:Test Item}";
    String actual = inventoryAction.toString();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test equals method")
  void testEqualsMethod() {
    InventoryAction inventoryActionEqual = new InventoryAction("Test Item");
    assertEquals(inventoryAction, inventoryActionEqual);
  }
}