package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Game class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 6, 2023.
 */
class GameTest {
  private String gameId;
  private Player player;
  private Passage openingPassage;
  private Story story;
  private List<Goal> goals;
  private Game game;

  @BeforeEach
  void setUp() {
    gameId = "Test ID";
    player = new Player.PlayerBuilder("Test name")
            .health(100)
            .score(100)
            .gold(50)
            .build();
    openingPassage = new Passage("Test title", "Test content");
    story = new Story("Test title", openingPassage);
    goals = new ArrayList<>();
    game = new Game(gameId, player, story, goals);
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTest {
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      Game testGameConstructor = new Game(gameId, player, story, goals);
      assertEquals(gameId, testGameConstructor.getGameId());
      assertEquals(player, testGameConstructor.getPlayer());
      assertEquals(story, testGameConstructor.getStory());
      assertEquals(goals, testGameConstructor.getGoals());
    }

    @Test
    @DisplayName("Test constructor invalid input throws IllegalArgumentException")
    void testConstructorInvalidInputThrowsIllegalArgumentException() {
      String invalidGameIdBlank = " ";
      assertThrows(IllegalArgumentException.class, () -> new Game(invalidGameIdBlank, player, story, goals));
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException() {
      String invalidGameId = null;
      Player invalidPlayer = null;
      Story invalidStory = null;
      List<Goal> invalidGoals = null;
      assertThrows(NullPointerException.class, () -> new Game(invalidGameId, player, story, goals));
      assertThrows(NullPointerException.class, () -> new Game(gameId, invalidPlayer, story, goals));
      assertThrows(NullPointerException.class, () -> new Game(gameId, player, invalidStory, goals));
      assertThrows(NullPointerException.class, () -> new Game(gameId, player, story, invalidGoals));
    }
  }

  @Nested
  @DisplayName("Game information tests")
  class GameInformationTests {
    @Test
    @DisplayName("Should get player")
    void shouldGetPlayer() {
      Player actualPlayer = game.getPlayer();
      assertEquals(player, actualPlayer);
    }

    @Test
    @DisplayName("Should get story")
    void shouldGetStory() {
      Story actualStory = game.getStory();
      assertEquals(story, actualStory);
    }

    @Test
    @DisplayName("Should get list of goals")
    void shouldGetListGoals() {
      List<Goal> expectedGoals = new ArrayList<>();
      expectedGoals.add(new HealthGoal(100));
      goals.add(new HealthGoal(100));
      List<Goal> actualGoals = game.getGoals();
      assertTrue(expectedGoals.containsAll(actualGoals));
    }
  }

  @Nested
  @DisplayName("Game functionality tests")
  class GameFunctionalityTests {
    @Test
    @DisplayName("Should begin the game")
    void shouldBeginTheGame() {
      Passage gameOpiningPassage = game.begin();
      assertEquals(openingPassage, gameOpiningPassage);
    }

    @Test
    @DisplayName("Should go")
    void shouldGo() {
      Passage expectedPassage = new Passage("Test title", "Test content");
      story.addPassage(expectedPassage);
      Link link = new Link("Test text", expectedPassage.getTitle());
      Passage actualPassage = game.go(link);
      assertEquals(expectedPassage, actualPassage);
    }

    @Test
    @DisplayName("Should not go throws NullPointerException")
    void shouldNotGoThrowsNullPointerException() {
      Link invalidLink = null;
      assertThrows(NullPointerException.class, () -> game.go(invalidLink));
    }
  }
}