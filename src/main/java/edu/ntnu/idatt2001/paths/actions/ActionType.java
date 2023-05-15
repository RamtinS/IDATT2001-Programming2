package edu.ntnu.idatt2001.paths.actions;

/**
 * Enum representing different types of cations in the application.
 * Each action type has a corresponding value description and action class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 15, 2023.
 */
public enum ActionType {
  GOLD("gold",  GoldAction.class),
  HEALTH("health", HealthAction.class),
  INVENTORY( "item", InventoryAction.class),
  SCORE( "points", ScoreAction.class);

  private final String actionValueDescription;
  private final Class<? extends Action> actionClass;

  /**
   * Constructs an ActionType with the given action
   * value description and action class.
   *
   * @param actionValueDescription the description of the action value.
   * @param actionClass the class representing the action.
   */
  ActionType(String actionValueDescription, Class<? extends Action> actionClass) {
    this.actionValueDescription = actionValueDescription;
    this.actionClass = actionClass;
  }

  /**
   * The method retrieves the description of the action value.
   *
   * @return the description of the action value
   */
  public String getActionValueDescription() {
    return actionValueDescription;
  }

  /**
   * The method retrieves the class representing the action.
   *
   * @return the class representing the action.
   */
  public Class<? extends Action> getActionClass() {
    return actionClass;
  }

  /**
   * The method returns the ActionType corresponding to the given action description.
   *
   * @param description the action description.
   * @return the ActionType corresponding to the action description.
   * @throws IllegalArgumentException if the ActionType does not exist.
   * @throws NullPointerException if the action description is null.
   */
  public static ActionType getActionTypeFromActionDescription(String description)
          throws IllegalArgumentException {
    for (ActionType actionType : ActionType.values()) {
      if (actionType.toString().equalsIgnoreCase(description)) {
        return actionType;
      }
    }
    throw new IllegalArgumentException("Invalid action type: " + description);
  }
}

