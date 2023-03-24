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
   * Constructs a ScoreAction object with the specified number of points.
   *
   * @param points The amount of points that the player will gain from completing the action.
   */
  public ScoreAction(int points) {
    this.points = points;
  }

  /**
   * The method retrieves the number of points awarded for this action.
   *
   * @return the number of points awarded for this action.
   */
  public int getPoints() {
    return points;
  }

  /**
   * The method executes the score action on a player,
   * adding the points to the player's score.
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

  /**
   * The method return a string representation of the ScoreAction.
   *
   * @return string representation of the ScoreAction.
   */
  @Override
  public String toString() {
    return "{Score:" + getPoints() + "}";
  }
}
