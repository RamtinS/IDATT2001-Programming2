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

  public Game(Player player, Story story, List<Goal> goals) throws NullPointerException {
    Objects.requireNonNull(player, "\nPlayer cannot be null");
    Objects.requireNonNull(story, "\nStory cannot be null");
    this.player = player;
    this.story = story;
    this.goals = goals;
  }

  public Player getPlayer() {
    return player;
  }

  public Story getStory() {
    return story;
  }

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
