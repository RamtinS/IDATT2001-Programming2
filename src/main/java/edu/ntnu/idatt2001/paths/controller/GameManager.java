package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Player;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.filehandling.FileGameHandler;
import edu.ntnu.idatt2001.paths.filehandling.FilePathValidator;
import edu.ntnu.idatt2001.paths.goals.Goal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameManager class is responsible for managing the
 * creation, deletion, and saving of Game objects.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class GameManager {

  private final String pathOfFile;
  private final List<Game> games;


  /**
   * Constructor for the GameManager class.
   *
   * @param pathOfFile the path of the file to read and write Game objects to.
   * @throws NullPointerException if the pathOfFile or FILE_EXTENSION is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error reading list of games form file.
   */
  public GameManager(String pathOfFile) throws NullPointerException,
          IllegalArgumentException, IOException {
    FilePathValidator.validatePathOfFile(pathOfFile, FileGameHandler.getFileExtension());
    this.pathOfFile = pathOfFile;
    this.games = new ArrayList<>();
    this.games.addAll(FileGameHandler.readGamesFromFile(pathOfFile));
  }

  /**
   * The method creates a new Game object with the given player, story, and goals.
   *
   * @param player the player of the new game.
   * @param story the story for the new game.
   * @param goals the goals for the new game.
   * @return the new created game.
   * @throws IllegalStateException if the maximum number of games is reached.
   * @throws NullPointerException if the player, story, or goals is null.
   */
  public Game createGame(Player player, Story story, List<Goal> goals)
          throws IllegalStateException, NullPointerException {
    if (games.size() == 4) {
      throw new IllegalStateException("Maximum number of games reached. Cannot create more games.");
    }
    if (player == null) {
      throw new NullPointerException("Player cannot be null.");
    }
    if (story == null) {
      throw new NullPointerException("Story cannot be null.");
    }
    if (goals == null) {
      throw new NullPointerException("Goals cannot be null.");
    }
    Game game = new Game(player, story, goals);
    games.add(game);
    return game;
  }

  /**
   * The method deletes the given game from the list of games and
   * writes the updated list to the file.
   *
   * @param game the game to delete.
   * @throws IOException if there is an error writing list of games to file.
   */
  public void deleteGame(Game game) throws IOException {
    if (game == null) {
      throw new NullPointerException("Game cannot be null.");
    }
    if (games.remove(game)) {
      FileGameHandler.writeGamesToFile(games, pathOfFile);
    }
  }

  /**
   * The method saves the given game to the list of games and
   * writes the updated list to the file.
   *
   * @param game the game to save.
   * @throws IOException if there is an error writing list of games to file.
   */
  public void saveGames(Game game) throws IOException {
    if (game == null) {
      throw new NullPointerException("Game cannot be null.");
    }
    games.add(game);
    FileGameHandler.writeGamesToFile(games, pathOfFile);
  }

  /**
   * The method retrieves the list of games.
   *
   * @return the list og games.
   */
  public List<Game> getGames() {
    return games;
  }
}
