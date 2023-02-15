package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

import java.util.Objects;

/**
 * The class represents an action where a player scores points.
 *
 * @author ...
 * @version JDK 17
 */
public class ScoreAction implements Action {
  private final int points;

  /**
   * Constructor to create an object of ScoreAction.
   *
   * @param points The amount of points that the player will gain from completing the action.
   * @throws IllegalArgumentException If points is 0 or lower.
   */

  public ScoreAction(int points) throws IllegalArgumentException {
    if (points <= 0) throw new IllegalArgumentException("The points of a score action"
            + " has to be larger than 0");
    this.points = points;
  }

  /**
   * Gets the amount of points awarded for this action.
   *
   * @return Returns the amount of points awarded for this action.
   */
  public int getPoints() {
    return points;
  }

  /**
   * Executes the score action on a player.
   *
   * @param player The player performs the action.
   * @throws NullPointerException If the player object is null.
   */
  @Override
  public void execute(Player player) throws NullPointerException {
    Objects.requireNonNull(player, "Player cannot be null");
    player.addScore(this.points);
  }
}
