package edu.ntnu.idatt2001.paths.filehandling;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.actions.ScoreAction;
import java.lang.reflect.Type;

/**
 * The ActionDeserializer class is a custom deserializer for
 * the Action interface, that deserializes JSON elements into
 * their corresponding Action objects.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
public class ActionDeserializer implements JsonDeserializer<Action> {

  /**
   * The method deserializes a JSON element into its corresponding Action object.
   *
   * @param jsonElement the JSON element to be deserialized.
   * @param type the type of the object to be deserialized to.
   * @param context the context of the deserialization.
   * @return the corresponding Action object.
   * @throws JsonParseException if JSON element cannot be deserialized because of unknown action type.
   */
  @Override
  public Action deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
          throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    if (jsonObject.has("gold")) {
      return context.deserialize(jsonElement, GoldAction.class);
    } else if (jsonObject.has("health")) {
      return context.deserialize(jsonElement, HealthAction.class);
    } else if (jsonObject.has("item" )) {
      return context.deserialize(jsonElement, InventoryAction.class);
    } else if (jsonObject.has("points")) {
      return context.deserialize(jsonElement, ScoreAction.class);
    }
    throw new JsonParseException("Unknown action type: " + jsonElement);
  }
}
