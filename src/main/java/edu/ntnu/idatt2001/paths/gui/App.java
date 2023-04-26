package edu.ntnu.idatt2001.paths.gui;

import edu.ntnu.idatt2001.paths.Difficulty;
import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.Player;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.goals.Goal;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Represents the Path application entrypoint. Responsible for creating "main menu", "base frame"
 * and "create game menu" objects
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class App extends Application {

  private static final int FRAME_HEIGHT = 600;
  private static final int FRAME_WIDTH = 1000;
  private Game game;
  private MainMenuListener mainMenuListener;
  private BaseFrameListener baseFrameListener;
  private CreateGameListener createGameListener;

  /**
   * Launches the application.
   *
   * @param args The arguments to launch.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Starts the application.
   *
   * @param stage Stage to set the application to.
   */
  @Override
  public void start(Stage stage) {

    createExampleGame();
    createBaseFrameListener(stage);
    addMainMenuListener(stage);
    addCreateGameListener(stage);

    stage.setScene(new Scene(new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener)));
    stage.show();
  }

  /**
   * Creates an example game and sets it to the current game.
   */
  private void createExampleGame() {
    Player player = new Player("Test name", 100, 100, 50);
    Passage openingPassage = new Passage("Image1", "geir dreper deg");
    Link firstLink = new Link("ikke trykk her", "Image3");
    firstLink.addAction(new HealthAction(-10));
    firstLink.addAction(new GoldAction(-10));
    firstLink.addAction(new InventoryAction("Red_X"));
    openingPassage.addLink(firstLink);
    Story story = new Story("Test title", openingPassage);
    Passage newPassage = new Passage("Image3", "geir slår deg");

    story.addPassage(newPassage);

    List<Goal> goals = new ArrayList<>();
    InventoryAction pickaxe = new InventoryAction("Pickaxe");
    pickaxe.execute(player);
    game = new Game(player, story, goals);
  }

  /**
   * Creates a BaseFrameListener, adds button functionality to all buttons in the BaseFrame.
   *
   * @param stage The stage to add the BaseFrame to
   */
  private void createBaseFrameListener(Stage stage) {
    baseFrameListener = new BaseFrameListener() {

      /**
       * Restarts the application to the opening passage.
       */
      @Override
      public void onRestartClicked() {
        BaseFrame restartFrame = new BaseFrame(game.getStory().getOpeningPassage(),
            game.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, this);
        stage.setScene(new Scene(restartFrame));
        stage.show();
      }

      /**
       * Sets the scene to the main menu.
       */
      @Override
      public void onExitClicked() {
        stage.setScene(new Scene(new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener)));

      }


      /**
       * Sets the scene to a new BaseFrame from the chosen link.
       *
       * @param link The link containing information about what BaseFrame to switch to.
       */
      @Override
      public void onOptionButtonClicked(Link link) {

        for (Action action : link.getActions()) {
          action.execute(game.getPlayer());
        }
        BaseFrame newFrame = new BaseFrame(game.getStory().getPassage(link), game.getPlayer(),
            FRAME_WIDTH, FRAME_HEIGHT, this);

        stage.setScene(new Scene(newFrame));
        stage.show();

        if (game.getStory().getPassage(link).getLinks().isEmpty()) {
          Alert alert = new Alert(AlertType.CONFIRMATION, "The game is finished");
          alert.showAndWait();
        }


      }
    };
  }

  /**
   * Creates a CreateGameListener for adding methods to CreateGameMenu buttons.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addCreateGameListener(Stage stage) {
    createGameListener = new CreateGameListener() {

      /**
       * Returns the scene to the main menu.
       */
      @Override
      public void onReturnClicked() {
        stage.setScene(new Scene(new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener)));
        stage.show();
      }

      /**
       * Creates a new game of the input values.
       *
       * @param chosenGoals The goals to add to the game.
       * @param playerName The player to add to the game.
       * @param chosenDifficulty The chosen difficulty for the game
       */
      @Override
      public void onCreateClicked(List<Goal> chosenGoals, String playerName,
                                  Difficulty chosenDifficulty) {
        Player player = new Player(playerName, chosenDifficulty.getHealth(), 0, 0);
        //TODO Story should be read here

        Passage openingPassage = new Passage("Image1", "Test");
        Link firstLink = new Link("ikke trykk her", "Image2");
        firstLink.addAction(new HealthAction(10));
        firstLink.addAction(new GoldAction(10));
        openingPassage.addLink(firstLink);
        Story story = new Story("Test Title", openingPassage);
        Passage newPassage = new Passage("Image2", "geir slår deg");

        story.addPassage(newPassage);

        //TODO Ends here
        game = new Game(player, story, chosenGoals);
        BaseFrame currentFrame = new BaseFrame(story.getOpeningPassage(), player, FRAME_WIDTH,
            FRAME_HEIGHT, baseFrameListener);
        stage.setScene(new Scene(currentFrame));
        stage.show();
      }

    };
  }

  /**
   * Sets the main menu listener.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addMainMenuListener(Stage stage) {
    mainMenuListener = new MainMenuListener() {

      /**
       * Loads a CreateGameMenu to the stage.
       */
      @Override
      public void onNewGameClicked() {
        CreateGameMenu createGameMenu = new CreateGameMenu(FRAME_WIDTH, FRAME_HEIGHT,
            createGameListener);
        stage.setScene(new Scene(createGameMenu));
        stage.show();
      }

      /**
       * Loads a menu for selecting a game to play.
       */
      @Override
      public void onLoadGameClicked() {
        BaseFrame baseFrame = new BaseFrame(game.getStory().getOpeningPassage(), game.getPlayer(),
            FRAME_WIDTH, FRAME_HEIGHT, baseFrameListener);
        stage.setScene(new Scene(baseFrame));
      }

      /**
       * Loads the tutorial scene.
       */
      @Override
      public void onTutorialButtonClicked() {

      }

    };
  }


}
