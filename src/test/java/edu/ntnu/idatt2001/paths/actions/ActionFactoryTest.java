package edu.ntnu.idatt2001.paths.actions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the ActionFactory class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 15, 2023.
 */
class ActionFactoryTest {

  @Test
  @DisplayName("Test create action with integer value")
  void testCreateActionWithIntegerValue() {
    String actionDescription = "gold";
    String actionValue = "100";
    Action action = ActionFactory.createAction(actionDescription, actionValue);

    assertNotNull(action);
    assertTrue(action instanceof GoldAction);
    assertEquals(100, ((GoldAction) action).getGold());
  }

  @Test
  @DisplayName("Test create action with string value")
  void testCreateActionWithStringValue() {
    String actionDescription = "inventory";
    String actionValue = "sword";
    Action action = ActionFactory.createAction(actionDescription, actionValue);

    assertNotNull(action);
    assertTrue(action instanceof InventoryAction);
    assertEquals("sword", ((InventoryAction) action).getItem());
  }

  @Test
  @DisplayName("Test create action with invalid input throws IllegalArgumentException")
  void testCreateActionWithInvalidInputThrowsIlleArgumentException() {
    String actionDescription = "score";
    String invalidActionValue = "10kl";

    assertThrows(IllegalArgumentException.class,
            () -> ActionFactory.createAction(actionDescription, invalidActionValue));
  }

  @Test
  @DisplayName("Test create action with invalid input throws NullPointerException")
  void testCreateActionWithInvalidInputThrowsNullPointerException() {
    assertThrows(NullPointerException.class,
            () -> ActionFactory.createAction("gold", null));
    assertThrows(NullPointerException.class,
            () -> ActionFactory.createAction(null, "100"));
  }
}