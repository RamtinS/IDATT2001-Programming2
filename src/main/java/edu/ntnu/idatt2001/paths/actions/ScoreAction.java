package edu.ntnu.idatt2001.paths.actions;

import edu.ntnu.idatt2001.paths.Player;

/**
 * The class represents an action where a player scores points.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 24, 2023.
 */
public class ScoreAction implements Action {
  private final int points;

  /**
   * Constructor to create an object of ScoreAction.
   *
   * @param points The amount of points that the player will gain from completing the action.
   */
  public ScoreAction(int points) {
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
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    player.addScore(this.points);
  }

  @Override
  public String toString() {
    return "{Score:" + getPoints() + "}";
  }
}
