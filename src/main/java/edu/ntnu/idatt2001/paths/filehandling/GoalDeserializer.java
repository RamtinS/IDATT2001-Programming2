package edu.ntnu.idatt2001.paths.filehandling;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.GoalType;
import java.lang.reflect.Type;

/**
 * The GoalDeserializer class is a custom deserializer for
 * the Goal interface, that deserializes JSON elements into
 * their corresponding Goal objects.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 15, 2023.
 */
public class GoalDeserializer implements JsonDeserializer<Goal> {

  /**
   * The method deserializes a JSON element into its corresponding Goal object.
   * The method only supports Goal objects determined by the GoalType enum.
   *
   * @param jsonElement the JSON element to be deserialized.
   * @param type the type of the object to be deserialized to.
   * @param context the context of the deserialization.
   * @return the corresponding Goal object.
   * @throws JsonParseException if JSON element cannot be deserialized because of unknown goal type.
   */
  @Override
  public Goal deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
          throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    for (GoalType goalType : GoalType.values()) {
      if (jsonObject.has(goalType.getGoalValueDescription())) {
        Class<? extends Goal> goalClass = goalType.getGoalClass();
        return context.deserialize(jsonElement, goalClass);
      }
    }
    throw new JsonParseException("Unknown goal type: " + jsonElement + ".");
  }
}
