package edu.ntnu.idatt2001.paths.filehandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.Player;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.goals.Goal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The FileGameHandler class provides methods to write and read
 * a list of Game objects to/from a JSON file.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 6, 2023.
 */
public class FileGameHandler {

  private static final Logger logger = Logger.getLogger(FileStoryHandler.class.getName());
  private static final String FILE_EXTENSION = ".json";
  private static final String GAME_ID_KEY = "game ID";
  private static final String PLAYER_KEY = "player";
  private static final String STORY_TITLE_KEY = "story title";
  private static final String STORY_OPENING_PASSAGE_KEY = "story opening passage";
  private static final String STORY_CURRENT_PASSAGE_KEY = "story current passage";
  private static final String STORY_PASSAGES_KEY = "story passages";
  private static final String GOALS_KEY = "goals";

  /**
   * The method writes a list of Game objects to a JSON file.
   *
   * @param games the list of Game objects to write to the file.
   * @param pathOfFile the path to the file to write to.
   * @throws NullPointerException if games or pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error writing list of games to file
   */
  public static void writeGamesToFile(List<Game> games, String pathOfFile)
          throws NullPointerException, IllegalArgumentException, IOException {
    if (games == null) {
      throw new NullPointerException("The list of games cannot be null.");
    }
    FilePathValidator.validatePathOfFile(pathOfFile, FILE_EXTENSION);

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setLenient()
            .create();

    JsonArray jsonArray = new JsonArray();
    for (Game game : games) {
      JsonObject jsonObject = new JsonObject();

      jsonObject.addProperty(GAME_ID_KEY, game.getGameId());

      jsonObject.add(PLAYER_KEY, gson.toJsonTree(game.getPlayer()));

      jsonObject.addProperty(STORY_TITLE_KEY, game.getStory().getTitle());
      jsonObject.add(STORY_OPENING_PASSAGE_KEY, gson.toJsonTree(game.getStory().getOpeningPassage()));
      jsonObject.add(STORY_CURRENT_PASSAGE_KEY, gson.toJsonTree(game.getStory().getCurrentPassage()));
      List<Passage> passages = new ArrayList<>(game.getStory().getPassages());
      jsonObject.add(STORY_PASSAGES_KEY, gson.toJsonTree(passages));

      jsonObject.add(GOALS_KEY, gson.toJsonTree(game.getGoals()));

      jsonArray.add(jsonObject);
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathOfFile.toLowerCase().trim()))) {
      writer.write(gson.toJson(jsonArray));
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error writing list of games to file.", e);
      throw new IOException("Error writing list of games to file: " + e.getMessage());
    }
  }

  /**
   * The method reads a list of Game objects from a JSON file.
   *
   * @param pathOfFile the path ot the file to read from.
   * @return the list of Game objects read from the file.
   * @throws NullPointerException if the pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error reading list of games from file.
   * @throws JsonParseException unknown action or goal type under deserialization of JSON file.
   */
  public static List<Game> readGamesFromFile(String pathOfFile) throws NullPointerException,
          IllegalArgumentException, IOException, JsonParseException {
    FilePathValidator.validatePathOfFile(pathOfFile, FILE_EXTENSION);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Action.class, new ActionDeserializer())
            .registerTypeAdapter(Goal.class, new GoalDeserializer())
            .setPrettyPrinting()
            .setLenient()
            .create();

    List<Game> games = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(pathOfFile.toLowerCase().trim()))) {
      JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
      if (jsonArray != null) {
        for (JsonElement jsonElement : jsonArray) {
          JsonObject jsonObject = jsonElement.getAsJsonObject();

          String gameId = gson.fromJson(jsonObject.get(GAME_ID_KEY), String.class);

          Player player = gson.fromJson(jsonObject.get(PLAYER_KEY), Player.class);

          String storyTitle = gson.fromJson(jsonObject.get(STORY_TITLE_KEY), String.class);
          Passage openingPassage = gson.fromJson(jsonObject.get(STORY_OPENING_PASSAGE_KEY), Passage.class);
          Passage currentPassage = gson.fromJson(jsonObject.get(STORY_CURRENT_PASSAGE_KEY), Passage.class);
          Story story = new Story(storyTitle, openingPassage);
          story.setCurrentPassage(currentPassage);

          List<Passage> passages = gson.fromJson(jsonObject.get(STORY_PASSAGES_KEY),
                  new TypeToken<List<Passage>>(){}.getType());

          for (Passage passage : passages) {
            story.addPassage(passage);
          }

          List<Goal> goals = gson.fromJson(jsonObject.get(GOALS_KEY),
                  new TypeToken<List<Goal>>() {}.getType());

          Game game = new Game(gameId, player, story, goals);
          games.add(game);
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading list of games from file.", e);
      throw new IOException("Error reading list of games from file: " + e.getMessage());
    }
    return games;
  }

  /**
   * The method retrieves the file extension the class uses.
   *
   * @return the file extension.
   */
  public static String getFileExtension() {
    return FILE_EXTENSION;
  }
}
