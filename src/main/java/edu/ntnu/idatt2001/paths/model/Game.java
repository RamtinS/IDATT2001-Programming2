package edu.ntnu.idatt2001.paths.model;

import edu.ntnu.idatt2001.paths.goals.Goal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Game class represents a game, which is played by a player
 * and contains a story and a list of goals.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 19, 2023.
 */
public class Game {
  private final String gameId;
  private final Player player;
  private Story story;
  private final List<Goal> goals;

  /**
   * Constructor to create an object of the type Game.
   *
   * @param player the player playing the game.
   * @param story the story of the game.
   * @param goals list of goals that indicate desired outcomes in a game.
   * @throws IllegalArgumentException if the gameId is blank.
   * @throws NullPointerException if the gameId, player or story is null.
   */
  public Game(String gameId, Player player, Story story, List<Goal> goals)
          throws IllegalArgumentException, NullPointerException {
    if (gameId.isBlank()) {
      throw new IllegalArgumentException("Game ID cannot be blank.");
    }
    this.gameId = Objects.requireNonNull(gameId.trim(), "Game ID cannot be null.");
    this.player = Objects.requireNonNull(player, "Player cannot be null.");
    this.story = Objects.requireNonNull(story, "Story cannot be null.");
    this.goals = new ArrayList<>();
    this.goals.addAll(Objects.requireNonNull(goals, "Goals cannot be null."));
  }

  /**
   * The method sets a new story to the game.
   *
   * @param story the new story.
   * @throws NullPointerException if the story object is null.
   */
  private void setStory(Story story) throws NullPointerException {
    this.story = Objects.requireNonNull(story, "Story cannot be null.");
  }

  /**
   * The method retrieves the game ID.
   *
   * @return the game ID.
   */
  public String getGameId() {
    return gameId;
  }

  /**
   * The method retrieves the player playing the game.
   *
   * @return the player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * The method retrieves the story of the game.
   *
   * @return the story of the game.
   */
  public Story getStory() {
    return story;
  }

  /**
   * The method retrieves a list of goals.
   *
   * @return the list goals.
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * The method returns the start passage of the game.
   *
   * @return the start passage of the game.
   */
  public Passage begin() {
    return story.getCurrentPassage();
  }

  /**
   * The method resets the game by resetting the
   * player and returning the opening passage of the story.
   *
   * @param originalStory new instance of the original story of the game.
   * @return the opening passage of the story.
   * @throws NullPointerException if the story object is null.
   */
  public Passage resetGame(Story originalStory) throws NullPointerException {
    setStory(originalStory);
    player.resetPlayer();
    story.setCurrentPassage(story.getOpeningPassage());
    return story.getOpeningPassage();
  }

  /**
   * Finds the corresponding passage from the given links, and returns it.
   *
   * @param link The link used to find the corresponding passage
   * @return Passage from the corresponding link.
   * @throws NullPointerException If the link is null.
   */
  public Passage go(Link link) throws NullPointerException {
    if (link == null) {
      throw new NullPointerException("Link cannot be null");
    }
    return story.getPassage(link);
  }

  /**
   * The method checks for equality between Game objects.
   *
   * @param o the object to which it is being compared.
   * @return a boolean value which indicate whether they are equal or not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return Objects.equals(getGameId(), game.getGameId());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getGameId());
  }
}
