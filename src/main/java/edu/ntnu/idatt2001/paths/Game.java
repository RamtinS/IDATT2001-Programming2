package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.goals.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a game.
 *
 * @author ...
 * @version JDK 17
 */
public class Game {
  private final Player player;
  private final Story story;
  private final List<Goal> goals;

  /**
   * Constructor to create an object of the type Game.
   *
   * @param player the player playing the game.
   * @param story the story of the game.
   * @param goals list of goals that indicate desired outcomes in a game.
   * @throws NullPointerException if player or story is null.
   */
  public Game(Player player, Story story, List<Goal> goals) throws NullPointerException {
    this.player = Objects.requireNonNull(player, "Player cannot be null");
    this.story = Objects.requireNonNull(story, "Story cannot be null");
    this.goals = new ArrayList<>();
    this.goals.addAll(Objects.requireNonNull(goals, "Goals cannot be null"));
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
   * Returns the opening passage of the game.
   *
   * @return The opening passage of the game.
   */
  public Passage begin() {
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
}
