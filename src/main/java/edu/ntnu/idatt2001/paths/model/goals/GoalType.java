package edu.ntnu.idatt2001.paths.model.goals;

/**
 * Enum representing different types of goals.
 * Each goal type has a corresponding description and goal class.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 15, 2023.
 */
public enum GoalType {
  GOLD("minimumGold", GoldGoal.class),
  HEALTH("minimumHealth", HealthGoal.class),
  INVENTORY("mandatoryItems", InventoryGoal.class),
  SCORE("minimumPoints", ScoreGoal.class);

  private final String goalValueDescription;
  private final Class<? extends Goal> goalClass;

  /**
   * Constructs a GoalType with the given goal value description
   * and goal class.
   *
   * @param goalValueDescription the description of the goal value.
   * @param goalClass the class representing the goal.
   */
  GoalType(String goalValueDescription, Class<? extends Goal> goalClass) {
    this.goalValueDescription = goalValueDescription;
    this.goalClass = goalClass;
  }

  /**
   * The method retrieves the description of the goal value.
   *
   * @return the description of the goal value.
   */
  public String getGoalValueDescription() {
    return goalValueDescription;
  }

  /**
   * The method retrieves the class representing the goal.
   *
   * @return the class representing the goal.
   */
  public Class<? extends Goal> getGoalClass() {
    return goalClass;
  }
}

