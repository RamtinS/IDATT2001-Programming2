package edu.ntnu.idatt2001.paths.actions;

/**
 * The ActionFactory class is responsible for creating
 * action objects based on action descriptions and values.
 * The class supports only the creation of action objects
 * determined by the ActionType enum.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 19, 2023.
 */
public class ActionFactory {

  /**
   * The method creates an action based on the given action
   * description and action value.
   *
   * @param actionDescription the action description.
   * @param actionValue the action value.
   * @return the created action object.
   * @throws IllegalArgumentException if action description or value is invalid.
   * @throws NullPointerException if actionDescription or actionValue is null.
   */
  public static Action createAction(String actionDescription, String actionValue)
          throws IllegalArgumentException, NullPointerException {
    if (actionDescription == null) {
      throw new NullPointerException("Action description cannot be null.");
    }
    if (actionValue == null) {
      throw new NullPointerException("Action value cannot be null.");
    }

    ActionType actionType = ActionType.getActionType(actionDescription);

    Action action;
    try {
      switch (actionType) {
        case GOLD -> action = new GoldAction(Integer.parseInt(actionValue));
        case HEALTH -> action = new HealthAction(Integer.parseInt(actionValue));
        case INVENTORY -> action = new InventoryAction(actionValue);
        case SCORE -> action = new ScoreAction(Integer.parseInt(actionValue));
        default -> action = null;
      }
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid action value for " + actionDescription + ": "
              + actionValue + ". " + actionDescription
              + "action expects a numeric value in integer format.");
    } catch (IllegalArgumentException  e) {
      throw new IllegalArgumentException("Invalid action value for " + actionDescription + ": "
              + actionValue + ". " + e.getMessage());
    }
    return action;
  }
}
