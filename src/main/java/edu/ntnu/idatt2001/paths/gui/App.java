package edu.ntnu.idatt2001.paths.gui;

import edu.ntnu.idatt2001.paths.Difficulty;
import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Player;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.controller.GameManager;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.gui.listeners.BaseFrameListener;
import edu.ntnu.idatt2001.paths.gui.listeners.CreateGameListener;
import edu.ntnu.idatt2001.paths.gui.listeners.LoadStoredGamesListener;
import edu.ntnu.idatt2001.paths.gui.listeners.MainMenuListener;
import edu.ntnu.idatt2001.paths.gui.listeners.StoryCreatorListener;
import edu.ntnu.idatt2001.paths.gui.storycreation.ScrollableStoryCreator;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
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
  private Game currentGame;
  private MainMenuListener mainMenuListener;
  private BaseFrameListener baseFrameListener;
  private CreateGameListener createGameListener;
  private LoadStoredGamesListener loadStoredGamesListener;
  private StoryCreatorListener storyCreatorListener;

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

    createBaseFrameListener(stage);
    addMainMenuListener(stage);
    addCreateGameListener(stage);
    addLoadStoredGameListener(stage);
    addStoryCreatorListener(stage);

    stage.setTitle("Paths");
    MainMenu menu = new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener);
    stage.setScene(new Scene(menu));
    stage.show();
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
        BaseFrame restartFrame = new BaseFrame(currentGame.getStory().getOpeningPassage(),
            currentGame.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, this);
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
          action.execute(currentGame.getPlayer());
        }

        BaseFrame newFrame;
        try {
          newFrame = new BaseFrame(currentGame.go(link), currentGame.getPlayer(), FRAME_WIDTH,
              FRAME_HEIGHT, this);
        } catch (Exception e) {
          Alert alert = new Alert(AlertType.ERROR,
              "This link is broken, please choose a different button");
          alert.showAndWait();
          return;
        }
        stage.setScene(new Scene(newFrame));
        stage.show();

        boolean gameFinished = false;
        if (currentGame.getPlayer().getHealth() <= 0){
          Alert alert = new Alert(AlertType.CONFIRMATION, "The game is finished, you have died");
          alert.showAndWait();
          gameFinished = true;
        }

        if (currentGame.getStory().getPassage(link).getLinks().isEmpty() && !gameFinished) {
          Alert alert = new Alert(AlertType.CONFIRMATION, "The game is finished, you have won");
          alert.showAndWait();
          gameFinished = true;
        }

        if (gameFinished){
          MainMenu mainMenu = new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener);
          stage.setScene(new Scene(mainMenu));
          stage.show();
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
        stage.setScene(new Scene(new MainMenu(FRAME_WIDTH,FRAME_HEIGHT, mainMenuListener)));
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
                                  Difficulty chosenDifficulty, Story selectedStory) {
        Player player = new Player.PlayerBuilder(playerName).health(chosenDifficulty.getHealth())
            .build();
        try {
          currentGame = GameManager.getInstance()
              .createGame("Game id", player, selectedStory, chosenGoals);
        } catch (Exception e) {
          Alert alert = new Alert(AlertType.CONFIRMATION,
              "Error while saving game\nYour game will not be saved, do you still want to "
                  + "continue?");
          alert.showAndWait();
          if (alert.getResult().equals(ButtonType.OK)) {
            currentGame = new Game("Game id", player, selectedStory, chosenGoals);
          } else {
            return;
          }
        }

        BaseFrame currentFrame = new BaseFrame(currentGame.begin(), currentGame.getPlayer(),
            FRAME_WIDTH, FRAME_HEIGHT, baseFrameListener);
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
        Scene newScene = new Scene(createGameMenu);
        stage.setScene(newScene);
        stage.show();
      }

      /**
       * Loads a menu for selecting a game to play.
       */
      @Override
      public void onLoadGameClicked() {
        LoadStoredGamesMenu loadStoredGamesMenu = new LoadStoredGamesMenu(FRAME_WIDTH, FRAME_HEIGHT,
            loadStoredGamesListener);
        Scene newScene = new Scene(loadStoredGamesMenu);
        stage.setScene(newScene);
        stage.show();
      }

      /**
       * Loads the tutorial scene.
       */
      @Override
      public void onTutorialButtonClicked() {

      }

      @Override
      public void onCreateStoryMenuClicked() {
        ScrollableStoryCreator scrollableStoryCreator = new ScrollableStoryCreator(FRAME_WIDTH,
            FRAME_HEIGHT, storyCreatorListener);
        stage.setScene(new Scene(scrollableStoryCreator));
      }
    };
  }

  private void addLoadStoredGameListener(Stage stage) {
    loadStoredGamesListener = new LoadStoredGamesListener() {
      @Override
      public void onSelectedGameClicked(Game game) {
        currentGame = game;
        BaseFrame currentFrame = new BaseFrame(currentGame.getStory().getOpeningPassage(),
            currentGame.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, baseFrameListener);
        stage.setScene(new Scene(currentFrame));
      }

      @Override
      public void onReturnClicked() {
        MainMenu mainMenu = new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener);
        stage.setScene(new Scene(mainMenu));
      }
    };
  }

  private void addStoryCreatorListener(Stage stage) {
    storyCreatorListener = new StoryCreatorListener() {
      @Override
      public void onReturnClicked() {
        MainMenu mainMenu = new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener);
        stage.setScene(new Scene(mainMenu));
        stage.show();
      }
    };
  }



}
