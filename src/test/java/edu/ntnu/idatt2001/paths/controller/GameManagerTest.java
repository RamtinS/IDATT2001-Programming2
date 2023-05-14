package edu.ntnu.idatt2001.paths.controller;

import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.Player;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.actions.ScoreAction;
import edu.ntnu.idatt2001.paths.filehandling.FileGameHandler;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.GoldGoal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.goals.InventoryGoal;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the GameManager class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 6, 2023.
 */
class GameManagerTest {

  private static final Logger logger = Logger.getLogger(GameManagerTest.class.getName());
  private static String pathOfFile;
  private static File testFile;
  private static GameManager gameManager;
  private static String gameId1;
  private static Player player1;
  private static Story story;
  private static List<Goal> goals1;
  private static Game game1;
  private static Game game2;

  @BeforeAll
  static void setUpAll() {
    pathOfFile = "src/test/resources/games/games_test.json";
    testFile = new File(pathOfFile);

    gameId1 = "Test ID 1";
    String gameId2 = "Test ID 2";

    player1 = new Player.PlayerBuilder("Player1")
            .health(50)
            .score(100)
            .gold(50)
            .build();

    Player player2 = new Player.PlayerBuilder("Player2")
            .health(60)
            .score(100)
            .gold(40)
            .build();

    Action actionOpeningPassage = new InventoryAction("Sword");
    Link linkOpeningPassage = new Link("Try to open the door", "Another room");
    Passage openingPassage = new Passage("Beginnings", "There is a door in front of you.");
    linkOpeningPassage.addAction(actionOpeningPassage);
    openingPassage.addLink(linkOpeningPassage);

    Action action1Link1Passage1 = new ScoreAction(10);
    Action action2Link1Passage1 = new HealthAction(10);
    Action action1Link2Passage1 = new ScoreAction(-10);
    Action action2Link2Passage1 = new HealthAction(-10);
    Link link1Passage1 = new Link("Open the book", "The book of spells");
    Link link2Passage1 = new Link("Go back", "Beginnings");
    Passage passage1 = new Passage("Another room",
            "The door opens to another room. You see a desk with a large, dusty book.");
    link1Passage1.addAction(action1Link1Passage1);
    link1Passage1.addAction(action2Link1Passage1);
    link2Passage1.addAction(action1Link2Passage1);
    link2Passage1.addAction(action2Link2Passage1);
    passage1.addLink(link1Passage1);
    passage1.addLink(link2Passage1);

    Action action1Link1Passage2 = new GoldAction(10);
    Action action2Link2Passage2 = new GoldAction(-10);
    Link link1Passage2 = new Link("Cast the spell", "Forest");
    Link link2Passage2 = new Link("Go back", "Another room");
    Passage passage2 = new Passage("The book of spells",
            "You open the book and find the spell of teleportation.");
    link1Passage2.addAction(action1Link1Passage2);
    link2Passage2.addAction(action2Link2Passage2);
    passage2.addLink(link1Passage2);
    passage2.addLink(link2Passage2);

    story = new Story("Haunted House", openingPassage);
    story.addPassage(passage1);
    story.addPassage(passage2);

    goals1 = new ArrayList<>();
    goals1.add(new ScoreGoal(10));
    goals1.add(new HealthGoal(70));
    goals1.add(new GoldGoal(100));
    List<String> inventoryGoal1 = new ArrayList<>();
    inventoryGoal1.add("Sword");
    goals1.add(new InventoryGoal(inventoryGoal1));

    List<Goal> goals2 = new ArrayList<>();
    goals2.add(new ScoreGoal(100));
    goals2.add(new HealthGoal(50));
    goals2.add(new GoldGoal(10));
    List<String> inventoryGoal2 = new ArrayList<>();
    inventoryGoal2.add("Sword");
    goals2.add(new InventoryGoal(inventoryGoal2));

    game1 = new Game(gameId1, player1, story, goals1);
    game2 = new Game(gameId2, player2, story, goals2);

    List<Game> games = new ArrayList<>();
    games.add(game1);
    games.add(game2);

    try {
      FileGameHandler.writeGamesToFile(games, pathOfFile);
      gameManager = GameManager.initialize(pathOfFile);
    } catch (IOException e) {
      logger.log(Level.WARNING, e.getMessage(), e);
    }
  }

  @AfterAll
  static void tearDownAll() {
    try {
      gameManager.deleteGame(game1);
      gameManager.deleteGame(game2);
    } catch (IOException e) {
      logger.log(Level.WARNING, e.getMessage(), e);
    }

    Path path = Paths.get(testFile.getPath());
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error deleting file.", e);
    }
    System.out.println(gameManager.getGames());
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Test get instance")
    void testGetInstance() {
      GameManager testGameManager = GameManager.getInstance();
      assertNotNull(testGameManager);
      assertSame(gameManager, testGameManager);
    }

    @Test
    @DisplayName("Should create game")
    void shouldCreateGame() {
      Game game = gameManager.createGame("Test ID 3", player1, story, goals1);

      assertEquals("Test ID 3", game.getGameId());
      assertEquals(player1, game.getPlayer());
      assertEquals(story, game.getStory());
      assertEquals(goals1, game.getGoals());
    }

    @Test
    @DisplayName("Should delete game")
    void shouldDeleteGame() {
      Game game = gameManager.createGame("Test ID 3", player1, story, goals1);
      try {
        gameManager.saveGame(game);
        gameManager.deleteGame(game);
      } catch (IOException e) {
        logger.log(Level.WARNING, e.getMessage(), e);
      }

      assertFalse(gameManager.getGames().contains(game));
    }

    @Test
    @DisplayName("Should sava existing game")
    void shouldSaveExistingGames() {
      game1.getPlayer().decreaseHealth(-40);

      try {
        gameManager.saveGame(game1);
      } catch (IOException e) {
        logger.log(Level.WARNING, e.getMessage(), e);
      }

      assertEquals(2, gameManager.getGames().size());
      assertEquals(10, gameManager.getGames().get(0).getPlayer().getHealth());

      game1.getPlayer().increaseHealth(40);
    }

    @Test
    @DisplayName("Should sava new game")
    void shouldSaveNewGames() {
      Game game = new Game("Test ID 3", player1, story, goals1);

      try {
        gameManager.saveGame(game);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      assertEquals(3, gameManager.getGames().size());
      assertEquals("Test ID 3", gameManager.getGames().get(2).getGameId());

      try {
        gameManager.deleteGame(game);
      } catch (IOException e) {
        logger.log(Level.WARNING, e.getMessage(), e);
      }
    }

    @Test
    @DisplayName("Should get games")
    void shouldGetGames() {
      List<Game> expectedGames = new ArrayList<>();
      expectedGames.add(game1);
      expectedGames.add(game2);
      List<Game> actualGames = gameManager.getGames();
      assertEquals(expectedGames, actualGames);
      for (int i = 0; i < actualGames.size(); i++) {
        assertEquals(expectedGames.get(i).getGameId(), actualGames.get(i).getGameId());
        assertEquals(expectedGames.get(i).getPlayer(), actualGames.get(i).getPlayer());
        assertEquals(expectedGames.get(i).getStory().getTitle(), actualGames.get(i).getStory().getTitle());
        assertEquals(expectedGames.get(i).getStory().getOpeningPassage(), actualGames.get(i).getStory().getOpeningPassage());
        assertTrue(expectedGames.get(i).getStory().getPassages().containsAll(actualGames.get(i).getStory().getPassages()));
        assertTrue(expectedGames.get(i).getGoals().containsAll(actualGames.get(i).getGoals()));
      }
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Should not initialize GameManager throws IllegalSateException")
    void shouldNotInitializeGameManagerThrowsIllegalSateException() {
      assertThrows(IllegalStateException.class, () -> GameManager.initialize(pathOfFile));
    }

    @Test
    @DisplayName("Should not create game throws IllegalArgumentException")
    void shouldNotCreateGameThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> gameManager.createGame(gameId1, player1, story, goals1));
    }

    @Test
    @DisplayName("Should not create game throws NullPointerException")
    void shouldNotCreateGameThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> gameManager.createGame(null,player1, story, goals1));
      assertThrows(NullPointerException.class, () -> gameManager.createGame("Test ID 3",null, story, goals1));
      assertThrows(NullPointerException.class, () -> gameManager.createGame("Test ID 3", player1, null, goals1));
      assertThrows(NullPointerException.class, () -> gameManager.createGame("Test ID 3", player1, story, null));
    }

    @Test
    @DisplayName("Should not delete game throws NullPointerException")
    void shouldNotDeleteGameThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> gameManager.deleteGame(null));
    }

    @Test
    @DisplayName("Should not sava existing game throws NullPointerException")
    void shouldNotSaveExistingGamesThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> gameManager.saveGame(null));
    }
  }
}