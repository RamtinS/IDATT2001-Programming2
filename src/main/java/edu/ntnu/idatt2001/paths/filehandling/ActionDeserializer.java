package edu.ntnu.idatt2001.paths.filehandling;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.ActionType;
import java.lang.reflect.Type;

/**
 * The ActionDeserializer class is a custom deserializer for the Action interface, that deserializes
 * JSON elements into their corresponding Action objects.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 15, 2023.
 */
public class ActionDeserializer implements JsonDeserializer<Action> {

  /**
   * The method deserializes a JSON element into its corresponding Action object. The method only
   * supports Action objects determined by the ActionType enum.
   *
   * @param jsonElement the JSON element to be deserialized.
   * @param type        the type of the object to be deserialized to.
   * @param context     the context of the deserialization.
   * @return the corresponding Action object.
   * @throws JsonParseException if JsonElement cannot be deserialized because of unknown action
   *                            type.
   */
  @Override
  public Action deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
      throws JsonParseException {

    JsonObject jsonObject = jsonElement.getAsJsonObject();
    for (ActionType actionType : ActionType.values()) {
      if (jsonObject.has(actionType.getActionValueDescription())) {
        Class<? extends Action> actionClass = actionType.getActionClass();
        return context.deserialize(jsonElement, actionClass);
      }
    }
    throw new JsonParseException("Unknown action type: " + jsonElement + ".");
  }
}
