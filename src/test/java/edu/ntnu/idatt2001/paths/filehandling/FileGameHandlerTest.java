package edu.ntnu.idatt2001.paths.filehandling;

import com.google.gson.JsonParseException;
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
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.GoldGoal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.goals.InventoryGoal;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
 * The class tests the FileGameHandler class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
class FileGameHandlerTest {

  private static final Logger logger = Logger.getLogger(FileGameHandlerTest.class.getName());
  private String pathToFile;
  private File gamesFile;
  private List<Game> games;

  @BeforeEach
  void setUp() {
    pathToFile = "src/test/resources/games/game_objects.json";
    gamesFile = new File(pathToFile);

    Player player1 = new Player.PlayerBuilder("Player1")
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

    Story story = new Story("Haunted House", openingPassage);
    story.addPassage(passage1);
    story.addPassage(passage2);

    List<Goal> goals1 = new ArrayList<>();
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

    Game game1 = new Game(player1, story, goals1);
    Game game2 = new Game(player2, story, goals2);

    games = new ArrayList<>();
    games.add(game1);
    games.add(game2);
  }

  @AfterEach
  void tearDown() {
    Path pathGames = Paths.get(gamesFile.getPath());
    try {
      Files.deleteIfExists(pathGames);
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error deleting file.", e);
    }
  }

  @Nested
  @DisplayName("Positive tests file handling")
  class positiveTestsFileHandling {
    @Test
    @DisplayName("Should write games to file")
    void shouldWriteGamesToFile() {
      try {
        FileGameHandler.writeGamesToFile(games, pathToFile);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          content.append(line).append("\n");
        }

        String expected = """
                [
                  {
                    "player": {
                      "name": "Player1",
                      "health": 50,
                      "score": 100,
                      "gold": 50,
                      "inventory": []
                    },
                    "story title": "Haunted House",
                    "story opening passage": {
                      "title": "Beginnings",
                      "content": "There is a door in front of you.",
                      "links": [
                        {
                          "text": "Try to open the door",
                          "reference": "Another room",
                          "actions": [
                            {
                              "item": "Sword"
                            }
                          ]
                        }
                      ]
                    },
                    "story passages": [
                      {
                        "title": "Another room",
                        "content": "The door opens to another room. You see a desk with a large, dusty book.",
                        "links": [
                          {
                            "text": "Open the book",
                            "reference": "The book of spells",
                            "actions": [
                              {
                                "points": 10
                              },
                              {
                                "health": 10
                              }
                            ]
                          },
                          {
                            "text": "Go back",
                            "reference": "Beginnings",
                            "actions": [
                              {
                                "points": -10
                              },
                              {
                                "health": -10
                              }
                            ]
                          }
                        ]
                      },
                      {
                        "title": "The book of spells",
                        "content": "You open the book and find the spell of teleportation.",
                        "links": [
                          {
                            "text": "Cast the spell",
                            "reference": "Forest",
                            "actions": [
                              {
                                "gold": 10
                              }
                            ]
                          },
                          {
                            "text": "Go back",
                            "reference": "Another room",
                            "actions": [
                              {
                                "gold": -10
                              }
                            ]
                          }
                        ]
                      }
                    ],
                    "goals": [
                      {
                        "minimumPoints": 10
                      },
                      {
                        "minimumHealth": 70
                      },
                      {
                        "minimumGold": 100
                      },
                      {
                        "mandatoryItems": [
                          "Sword"
                        ]
                      }
                    ]
                  },
                  {
                    "player": {
                      "name": "Player2",
                      "health": 60,
                      "score": 100,
                      "gold": 40,
                      "inventory": []
                    },
                    "story title": "Haunted House",
                    "story opening passage": {
                      "title": "Beginnings",
                      "content": "There is a door in front of you.",
                      "links": [
                        {
                          "text": "Try to open the door",
                          "reference": "Another room",
                          "actions": [
                            {
                              "item": "Sword"
                            }
                          ]
                        }
                      ]
                    },
                    "story passages": [
                      {
                        "title": "Another room",
                        "content": "The door opens to another room. You see a desk with a large, dusty book.",
                        "links": [
                          {
                            "text": "Open the book",
                            "reference": "The book of spells",
                            "actions": [
                              {
                                "points": 10
                              },
                              {
                                "health": 10
                              }
                            ]
                          },
                          {
                            "text": "Go back",
                            "reference": "Beginnings",
                            "actions": [
                              {
                                "points": -10
                              },
                              {
                                "health": -10
                              }
                            ]
                          }
                        ]
                      },
                      {
                        "title": "The book of spells",
                        "content": "You open the book and find the spell of teleportation.",
                        "links": [
                          {
                            "text": "Cast the spell",
                            "reference": "Forest",
                            "actions": [
                              {
                                "gold": 10
                              }
                            ]
                          },
                          {
                            "text": "Go back",
                            "reference": "Another room",
                            "actions": [
                              {
                                "gold": -10
                              }
                            ]
                          }
                        ]
                      }
                    ],
                    "goals": [
                      {
                        "minimumPoints": 100
                      },
                      {
                        "minimumHealth": 50
                      },
                      {
                        "minimumGold": 10
                      },
                      {
                        "mandatoryItems": [
                          "Sword"
                        ]
                      }
                    ]
                  }
                ]
                """;

        assertEquals(expected, content.toString());
      } catch (IOException e) {
        logger.log(Level.WARNING, "Error occurred while reading file: " + pathToFile, e);
      }
    }

    @Test
    @DisplayName("Should read existing games from file")
    void shouldReadExistingGamesFromFile() {
      try {
        FileGameHandler.writeGamesToFile(games, pathToFile);
      } catch (IOException e) {
        logger.log(Level.WARNING, "Error occurred while writing games to file: " + pathToFile, e);
      }
      List<Game> gamesReadFromFile = new ArrayList<>();
      try {
        gamesReadFromFile.addAll(FileGameHandler.readGamesFromFile(pathToFile));
      } catch (IOException e) {
        logger.log(Level.WARNING, "Error occurred while reading games to file: " + pathToFile, e);
      }

      assertEquals(games.size(), gamesReadFromFile.size());

      for (int i = 0; i < games.size(); i++) {
        Game originalGame = games.get(i);
        Game readGame = gamesReadFromFile.get(i);

        assertEquals(originalGame.getPlayer(), readGame.getPlayer());

        assertEquals(originalGame.getStory().getTitle(), readGame.getStory().getTitle());
        assertEquals(originalGame.getStory().getOpeningPassage(), readGame.getStory().getOpeningPassage());
        assertTrue(readGame.getStory().getPassages().containsAll(originalGame.getStory().getPassages()));

        assertEquals(originalGame.getGoals().size(), readGame.getGoals().size());
        assertTrue(readGame.getGoals().containsAll(originalGame.getGoals()));
      }
    }

    @Test
    @DisplayName("Should read empty list of games from file")
    void shouldReadEmptyListOfGamesFromFile() {
      List<Game> nonExistingGames = new ArrayList<>();
      try {
        FileGameHandler.writeGamesToFile(nonExistingGames, pathToFile);
      } catch (IOException e) {
        logger.log(Level.WARNING, "Error occurred while writing games to file: " + pathToFile, e);
      }
      List<Game> gamesReadFromFile = new ArrayList<>();
      try {
        gamesReadFromFile.addAll(FileGameHandler.readGamesFromFile(pathToFile));
      } catch (IOException e) {
        logger.log(Level.WARNING, "Error occurred while reading games to file: " + pathToFile, e);
      }
      assertTrue(gamesReadFromFile.isEmpty());
    }

    @Test
    @DisplayName("Should get file extension")
    void shouldGetFileExtension() {
      String expectedFileExtension = ".json";
      String actualFileExtension = FileGameHandler.getFileExtension();
      assertEquals(expectedFileExtension, actualFileExtension);
    }
  }

  @Nested
  @DisplayName("Negative tests file handling")
  class negativeTestsFileHandling {
    String invalidPathOfFileNull = null;
    String invalidPathOfFileExtension = "src/test/resources/games/game_objects.txt";
    String invalidPathOfFileBlank = "";

    @Test
    @DisplayName("Should not write games to file throws NullPointerException")
    void shouldNotWriteStoryToFileThrowsNullPointerException() {
      List<Game> invalidList = null;
      assertThrows(NullPointerException.class,
              () -> FileGameHandler.writeGamesToFile(invalidList, pathToFile));
      assertThrows(NullPointerException.class,
              () -> FileGameHandler.writeGamesToFile(games, invalidPathOfFileNull));
    }

    @Test
    @DisplayName("Should not write games to file throws IllegalArgumentException")
    void shouldNotWriteStoryToFileThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> FileGameHandler.writeGamesToFile(games, invalidPathOfFileExtension));
      assertThrows(IllegalArgumentException.class,
              () -> FileGameHandler.writeGamesToFile(games, invalidPathOfFileBlank));
    }

    @Test
    @DisplayName("Should not read games from file throws NullPointerException")
    void shouldNotReadStoryFromFileThrowsNullPointerException() {
      assertThrows(NullPointerException.class,
              () -> FileGameHandler.readGamesFromFile(invalidPathOfFileNull));
    }

    @Test
    @DisplayName("Should not read games from file throws IllegalArgumentException")
    void shouldNotReadStoryFromFileThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class,
              () -> FileGameHandler.readGamesFromFile(invalidPathOfFileExtension));
      assertThrows(IllegalArgumentException.class,
              () -> FileGameHandler.readGamesFromFile(invalidPathOfFileBlank));
    }

    @Test
    @DisplayName("Should not read games from file throws IOException")
    void shouldNotReadGamesFromFileThrowsIOException() {
      assertThrows(IOException.class,
              () -> FileGameHandler.readGamesFromFile("nonExistingFile.json"));
    }

    @Test
    @DisplayName("Should not read games from file invalid action throws JsonParseException")
    void shouldNotReadGamesFromFileInvalidActionThrowsJsonParseException() {
      assertThrows(JsonParseException.class,
              () -> FileGameHandler.readGamesFromFile(
                      "src/test/resources/games/invalid_action_game_objects.json"));
    }

    @Test
    @DisplayName("Should not read games from file invalid goal throws JsonParseException")
    void shouldNotReadGamesFromFileInvalidGoalThrowsJsonParseException() {
      assertThrows(JsonParseException.class,
              () -> FileGameHandler.readGamesFromFile(
                      "src/test/resources/games/invalid_goal_game_objects.json"));
    }
  }
}