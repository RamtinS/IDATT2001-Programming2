package edu.ntnu.idatt2001.paths.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The ActionFactory class is responsible for creating
 * action objects based on action descriptions and values.
 * The class supports only the creation of action objects
 * determined by the ActionType enum.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 15, 2023.
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
    ActionType actionType = ActionType.getActionTypeFromActionDescription(actionDescription);
    Class<? extends Action> actionClass = actionType.getActionClass();
    try {
      if (actionValue.matches("^[0-9+-]+$")) {
        Constructor<? extends Action> constructor = actionClass.getConstructor(Integer.TYPE);
        return constructor.newInstance(Integer.parseInt(actionValue));
      } else {
        Constructor<? extends Action> constructor = actionClass.getConstructor(String.class);
        return constructor.newInstance(actionValue);
      }
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
             | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalArgumentException("Invalid action value for " + actionDescription + ": " + actionValue);
    }
  }
}
