package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.model.actions.ActionFactory;
import edu.ntnu.idatt2001.paths.model.actions.GoldAction;
import edu.ntnu.idatt2001.paths.model.actions.HealthAction;
import edu.ntnu.idatt2001.paths.model.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.model.actions.ScoreAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the ActionFactory class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 19, 2023.
 */
class ActionFactoryTest {

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Test create score action")
    void testCreateScoreAction() {
      String actionDescription = "score";
      String actionValue = "100";
      Action action = ActionFactory.createAction(actionDescription, actionValue);

      assertNotNull(action);
      assertTrue(action instanceof ScoreAction);
      assertEquals(100, ((ScoreAction) action).getPoints());
    }

    @Test
    @DisplayName("Test create health action")
    void testCreateHealthAction() {
      String actionDescription = "health";
      String actionValue = "100";
      Action action = ActionFactory.createAction(actionDescription, actionValue);

      assertNotNull(action);
      assertTrue(action instanceof HealthAction);
      assertEquals(100, ((HealthAction) action).getHealth());
    }

    @Test
    @DisplayName("Test create gold action")
    void testCreateGoldAction() {
      String actionDescription = "gold";
      String actionValue = "100";
      Action action = ActionFactory.createAction(actionDescription, actionValue);

      assertNotNull(action);
      assertTrue(action instanceof GoldAction);
      assertEquals(100, ((GoldAction) action).getGold());
    }

    @Test
    @DisplayName("Test create inventory action")
    void testCreateInventoryAction() {
      String actionDescription = "inventory";
      String actionValue = "sword";
      Action action = ActionFactory.createAction(actionDescription, actionValue);

      assertNotNull(action);
      assertTrue(action instanceof InventoryAction);
      assertEquals("sword", ((InventoryAction) action).getItem());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Test create action with invalid input throws NumberFormatException")
    void testCreateActionWithInvalidInputThrowsNumberFormatException() {
      String actionDescription = "score";
      String invalidActionValue = "10kl";

      assertThrows(NumberFormatException.class,
              () -> ActionFactory.createAction(actionDescription, invalidActionValue));
    }

    @Test
    @DisplayName("Test create action with invalid input throws IllegalArgumentException")
    void testCreateActionWithInvalidInputThrowsIlleArgumentException() {
      String nonExistingAction = "nonExistingAction";
      String actionDescription = "inventory";
      String invalidActionValue = " ";

      assertThrows(IllegalArgumentException.class,
              () -> ActionFactory.createAction(actionDescription, invalidActionValue));
      assertThrows(IllegalArgumentException.class,
              () -> ActionFactory.createAction(nonExistingAction, "10"));
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
}




