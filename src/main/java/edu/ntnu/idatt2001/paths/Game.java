package edu.ntnu.idatt2001.paths;

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
    Objects.requireNonNull(player, "\nPlayer cannot be null");
    Objects.requireNonNull(story, "\nStory cannot be null");
    this.player = player;
    this.story = story;
    this.goals = goals;
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

  public Passage begin() {
    return story.getOpeningPassage();
  }

  public Passage go(Link link) throws NullPointerException{
    Objects.requireNonNull(link, "\nLink cannot be null");
    return story.getPassage(link);
  }
}
