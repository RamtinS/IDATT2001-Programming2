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

  private static GameManager instance = null;
  private static final int MAX_GAMES = 6;
  private final String pathOfFile;
  private final List<Game> games;

  /**
   * Constructor for the GameManager class.
   *
   * @param pathOfFile the path to the file for reading and writing Game objects.
   * @throws NullPointerException if the pathOfFile or FILE_EXTENSION is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error reading the list of games form the file.
   */
  private GameManager(String pathOfFile) throws NullPointerException,
          IllegalArgumentException, IOException {
    FilePathValidator.validatePathOfFile(pathOfFile, FileGameHandler.getFileExtension());
    this.pathOfFile = pathOfFile;
    this.games = new ArrayList<>();
    this.games.addAll(FileGameHandler.readGamesFromFile(pathOfFile));
  }

  /**
   * The method initializes the GameManager with the given path of file.
   * This method can only be called once to ensure that GameManager is a singleton instance.
   *
   * @param pathOfFile the path to the file for reading and writing Game objects.
   * @return the initialized GameManager instance.
   * @throws NullPointerException if the pathOfFile or FILE_EXTENSION is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error reading the list of games from the file.
   * @throws IllegalStateException if the GameManager has already been initialized.
   */
  public static GameManager initialize(String pathOfFile) throws NullPointerException,
          IllegalArgumentException, IOException, IllegalStateException{
    if (instance != null) {
      throw new IllegalStateException("GameManager has already been initialized.");
    }
    instance = new GameManager(pathOfFile);
    return instance;
  }

  /**
   * Returns an instance of the GameManager class. The GameManager must be initialized
   * using the initialize() method before calling this method.
   *
   * @return the instance of the GameManager.
   * @throws IllegalStateException if the GameManager has not been initialized.
   */
  public static GameManager getInstance() throws IllegalStateException {
    if (instance == null) {
      throw new IllegalStateException("GameManager has not been initialized.");
    }
    return instance;
  }

  /**
   * The method creates a new Game object with the given player, story, and goals.
   *
   * @param player the player of the new game.
   * @param story the story for the new game.
   * @param goals the goals for the new game.
   * @return the new created game.
   * @throws IllegalStateException if the maximum number of games is reached.
   * @throws IllegalArgumentException if a game with the same ID already exists.
   * @throws NullPointerException if the gameId, player, story, or goals is null.
   */
  public Game createGame(String gameId, Player player, Story story, List<Goal> goals)
          throws IllegalStateException, IllegalArgumentException, NullPointerException {
    if (games.size() == MAX_GAMES) {
      throw new IllegalStateException("Maximum number of games reached: " + MAX_GAMES
              + " Cannot create more games.");
    }
    if (gameId == null) {
      throw new NullPointerException("Game ID cannot be null.");
    }
    if (games.stream().anyMatch(game -> game.getGameId().equals(gameId.trim()))) {
      throw new IllegalArgumentException("A game with the same ID already exists.");
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
    Game game = new Game(gameId,player, story, goals);
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
  public void deleteGame(Game game) throws NullPointerException, IOException {
    validateGame(game);
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
  public void saveGame(Game game) throws NullPointerException, IOException {
    validateGame(game);
    if (games.contains(game)) {
      int index = games.indexOf(game);
      games.set(index, game);
    } else {
      games.add(game);
    }
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

  /**
   * The method validates the given game object.
   *
   * @throws NullPointerException if the game is null.
   */
  private void validateGame(Game game) throws NullPointerException {
    if (game == null) {
      throw new NullPointerException("Game cannot be null.");
    }
  }
}
