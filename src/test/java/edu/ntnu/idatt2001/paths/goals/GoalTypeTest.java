package edu.ntnu.idatt2001.paths.goals;

import edu.ntnu.idatt2001.paths.model.goals.GoalType;
import edu.ntnu.idatt2001.paths.model.goals.GoldGoal;
import edu.ntnu.idatt2001.paths.model.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.model.goals.InventoryGoal;
import edu.ntnu.idatt2001.paths.model.goals.ScoreGoal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the GoalType enum.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 15, 2023.
 */
class GoalTypeTest {

  @Test
  @DisplayName("Test get goal value description")
  void testGetGoalValueDescription() {
    assertEquals("minimumGold", GoalType.GOLD.getGoalValueDescription());
    assertEquals("minimumHealth", GoalType.HEALTH.getGoalValueDescription());
    assertEquals("mandatoryItems", GoalType.INVENTORY.getGoalValueDescription());
    assertEquals("minimumPoints", GoalType.SCORE.getGoalValueDescription());
  }

  @Test
  @DisplayName("Test get goal class")
  void testGetGoalClass() {
    assertEquals(GoldGoal.class, GoalType.GOLD.getGoalClass());
    assertEquals(HealthGoal.class, GoalType.HEALTH.getGoalClass());
    assertEquals(InventoryGoal.class, GoalType.INVENTORY.getGoalClass());
    assertEquals(ScoreGoal.class, GoalType.SCORE.getGoalClass());
  }
}