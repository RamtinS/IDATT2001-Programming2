package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.model.actions.ActionType;
import edu.ntnu.idatt2001.paths.model.actions.GoldAction;
import edu.ntnu.idatt2001.paths.model.actions.HealthAction;
import edu.ntnu.idatt2001.paths.model.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.model.actions.ScoreAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActionTypeTest {

  @Test
  @DisplayName("Test get action value description")
  void testGetActionValueDescription() {
    assertEquals("gold", ActionType.GOLD.getActionValueDescription());
    assertEquals("health", ActionType.HEALTH.getActionValueDescription());
    assertEquals("item", ActionType.INVENTORY.getActionValueDescription());
    assertEquals("points", ActionType.SCORE.getActionValueDescription());
  }

  @Test
  @DisplayName("Test get action class")
  void testGetActionClass() {
    assertEquals(GoldAction.class, ActionType.GOLD.getActionClass());
    assertEquals(HealthAction.class, ActionType.HEALTH.getActionClass());
    assertEquals(InventoryAction.class, ActionType.INVENTORY.getActionClass());
    assertEquals(ScoreAction.class, ActionType.SCORE.getActionClass());
  }

  @Test
  @DisplayName("Should get action type from action description")
  void shouldGetActionTypeFromActionDescription() {
    ActionType goldActionType = ActionType.getActionType("gold");
    assertEquals(ActionType.GOLD, goldActionType);
    ActionType healthActionType = ActionType.getActionType("health");
    assertEquals(ActionType.HEALTH, healthActionType);
    ActionType inventoryAction = ActionType.getActionType("inventory");
    assertEquals(ActionType.INVENTORY, inventoryAction);
    ActionType scoreAction = ActionType.getActionType("score");
    assertEquals(ActionType.SCORE, scoreAction);
  }

  @Test
  @DisplayName("Should not get action type from action description throws IllegalArgumentException")
  void shouldNotGetActionTypeFromActionDescriptionThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> ActionType.getActionType("invalid"));
    assertThrows(IllegalArgumentException.class, () -> ActionType.getActionType(null));
  }
}

