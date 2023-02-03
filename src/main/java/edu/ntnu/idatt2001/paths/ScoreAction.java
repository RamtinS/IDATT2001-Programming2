package edu.ntnu.idatt2001.paths;

public class ScoreAction implements Action {
    private final int points;

    public ScoreAction(int points) throws IllegalArgumentException{
        if (points <= 0) throw new IllegalArgumentException("The points of a score action"
                + " has to be larger than 0");
        this.points = points;
    }


    public void execute(Player player){
        player.addScore(points);
    }
}
