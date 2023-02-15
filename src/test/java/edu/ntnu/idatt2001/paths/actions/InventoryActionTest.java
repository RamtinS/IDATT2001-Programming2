package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the InventoryAction class.
 *
 * @author ...
 * @version JDK 17
 */
class InventoryActionTest {


  InventoryAction inventoryAction;
  Player player;

  @BeforeEach
  void setUp() {
    inventoryAction = new InventoryAction("Test Item");
    player = new Player("Name", 10, 20, 30);
  }

  @Test
  @DisplayName("Should get item")
  void shouldGetItem() {
    String actual = inventoryAction.getItem();
    String expected = "Test Item";

    assertEquals(actual, expected);
  }

  @Test
  @DisplayName("Should execute and add to inventory")
  void shouldExecute() {
    inventoryAction.execute(player);
    String expected = "Test Item";
    String actual = player.getInventory().get(0);

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test constructor with valid input")
  void testConstructorValidInput() {
    InventoryAction inventoryActionTest = new InventoryAction("item");
    String expected = "item";
    String actual = inventoryActionTest.getItem();

    assertEquals(actual, expected);
  }

  @Test
  @DisplayName("Test constructor with invalid input throws NullPointerException")
  void testConstructorWithInvalidInput() {
    assertThrows(NullPointerException.class, () -> new InventoryAction(null));
  }
}