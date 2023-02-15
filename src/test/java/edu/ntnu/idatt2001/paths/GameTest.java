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
 * @author ...
 * @version JDK 17
 */
class GameTest {

  Player player;
  Story story;
  List<Goal> goals;
  Game game;

  @BeforeEach
  void setUp() {
    player = new Player("Test name", 100, 100, 50);
    story = new Story("Test title", new Passage("Test title", "Test content"));
    goals = new ArrayList<>();
    game = new Game(player, story, goals);
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTest {
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      Game testGameConstructor = new Game(player, story, goals);
      assertEquals(player, testGameConstructor.getPlayer());
      assertEquals(story, testGameConstructor.getStory());
      assertEquals(goals, testGameConstructor.getGoals());
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException() {
      Player invalidPlayer = null;
      Story invalidStory = null;
      List<Goal> invalidGoals = null;
      assertThrows(NullPointerException.class, () -> new Game(invalidPlayer, story, goals));
      assertThrows(NullPointerException.class, () -> new Game(player, invalidStory, goals));
      assertThrows(NullPointerException.class, () -> new Game(player, story, invalidGoals));
    }
  }

  @Nested
  @DisplayName("Game information tests")
  class GameInformationTests {
    @Test
    @DisplayName("Should get player")
    void shouldGetPlayer() {
      Player actualPlayer = game.getPlayer();
      assertEquals(Player.class, actualPlayer.getClass());
    }

    @Test
    @DisplayName("Should get story")
    void shouldGetStory() {
      Story actualStory = game.getStory();
      assertEquals(Story.class, actualStory.getClass());
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
      Passage actualOpiningPassage = game.begin();
      assertEquals(Passage.class, actualOpiningPassage.getClass());
    }

    @Test
    @DisplayName("Should go")
    void shouldGo() {
      Passage expectedPassage = new Passage("Test title", "Test content");
      story.addPassage(expectedPassage);
      Link link = new Link(expectedPassage.getTitle(), expectedPassage.getTitle());
      Passage actualPassage = game.go(link);
      assertEquals(expectedPassage, actualPassage);
    }

    @Test
    @DisplayName("Should not go throws NullPointerException")
    void shouldNotGoThrowsNullPointerException() {
      Link link = null;
      assertThrows(NullPointerException.class, () -> game.go(link));
    }
  }
}