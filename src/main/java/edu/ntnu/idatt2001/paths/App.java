package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.controller.GameManager;
import edu.ntnu.idatt2001.paths.filehandling.FileStoryHandler;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.gui.listeners.BaseFrameListener;
import edu.ntnu.idatt2001.paths.gui.listeners.CreateGameListener;
import edu.ntnu.idatt2001.paths.gui.listeners.LoadStoredGamesListener;
import edu.ntnu.idatt2001.paths.gui.listeners.MainMenuListener;
import edu.ntnu.idatt2001.paths.gui.listeners.StoryCreatorListener;
import edu.ntnu.idatt2001.paths.gui.menus.BaseFrame;
import edu.ntnu.idatt2001.paths.gui.menus.CreateGameMenu;
import edu.ntnu.idatt2001.paths.gui.menus.LoadStoredGamesMenu;
import edu.ntnu.idatt2001.paths.gui.menus.MainMenu;
import edu.ntnu.idatt2001.paths.gui.storycreation.ScrollableStoryCreator;
import edu.ntnu.idatt2001.paths.model.Game;
import edu.ntnu.idatt2001.paths.model.Link;
import edu.ntnu.idatt2001.paths.model.Passage;
import edu.ntnu.idatt2001.paths.model.Player;
import edu.ntnu.idatt2001.paths.model.Story;
import edu.ntnu.idatt2001.paths.tts.TextToSpeech;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Represents the Path application entrypoint.
 * The class is responsible for the creating "main menu",
 * "base frame", and "create game menu" objects
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 19, 2023.
 */
public class App extends Application {

  private static final Logger logger = Logger.getLogger(App.class.getName());
  private static final int FRAME_HEIGHT = 600;
  private static final int FRAME_WIDTH = 1000;
  private static final String STANDARD_STYLING =
      "file:src/main/resources/stylesheets" + "/StandardStyling.css";
  private Game currentGame;
  private Passage currentPassage;
  private String pathOStoryFile;
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
    try {
      GameManager.initialize("src/main/resources/games/game_objects.json");
    } catch (IllegalArgumentException | NullPointerException | IllegalStateException
             | IOException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }

    stage.setTitle("Paths");
    createBaseFrameListener(stage);
    addMainMenuListener(stage);
    addCreateGameListener(stage);
    addLoadStoredGameListener(stage);
    addStoryCreatorListener(stage);
    switchToMainMenu(stage);
    setCloseAction(stage);
    setStageSizes(stage);
  }

  private void setStyleSheet(Scene scene, String filePath) {
    scene.getStylesheets().add(filePath);
  }

  private void setStageSizes(Stage stage) {
    stage.setMinWidth(FRAME_WIDTH - 200.0);
    stage.setMaxWidth(FRAME_WIDTH + 200.0);
    stage.setMinHeight(FRAME_HEIGHT - 200.0);
    stage.setMaxHeight(FRAME_HEIGHT + 200.0);
  }

  /**
   * The method retrieves a new instance of the original story of the game.
   *
   * @return the original story.
   */
  private Story getOriginalStory() {
    Story story = null;
    try {
      story = FileStoryHandler.readStoryFromFile(pathOStoryFile);
    } catch (NullPointerException | IllegalArgumentException | IOException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }
    return story;
  }

  /**
   * Sets a new {@link BaseFrameListener}.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void createBaseFrameListener(Stage stage) {
    baseFrameListener = new BaseFrameListener() {

      /**
       * Restarts the game and takes the player to the opening passage.
       */
      @Override
      public void onRestartClicked() {
        loadNewBaseFrame(stage, currentGame.resetGame(getOriginalStory()));
      }

      /**
       * The method sets the scene to the main menu stage when
       * the exit button is clicked.
       *
       * @param shouldSaveGame true if the game should be saved before exiting, false otherwise.
       */
      @Override
      public void onExitClicked(boolean shouldSaveGame) {
        TextToSpeech.getInstance().resetSpeech();
        if (shouldSaveGame) {
          try {
            GameManager.getInstance().saveGame(currentGame, currentPassage);
            switchToMainMenu(stage);
          } catch (IOException | NullPointerException | IllegalArgumentException e) {
            String errorMessage = "The game could not be saved due to an error: " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            Alert alert = new Alert(AlertType.ERROR, errorMessage);
            alert.showAndWait();
            switchToMainMenu(stage);
          }
        } else {
          switchToMainMenu(stage);
        }
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
        link.getActions().clear();
        BaseFrame newFrame;
        try {
          newFrame = new BaseFrame(currentGame.getStory().getTitle(), currentGame.go(link),
              currentGame.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, this);
          currentPassage = currentGame.go(link);
        } catch (Exception e) {
          Alert alert = new Alert(AlertType.ERROR,
              "This link is broken, please choose a different button");
          alert.showAndWait();
          return;
        }
        Scene scene = new Scene(newFrame);
        setStyleSheet(scene, STANDARD_STYLING);
        stage.setScene(scene);
        stage.show();


        if (currentGame.getPlayer().getHealth() <= 0) {
          TextToSpeech.getInstance().resetSpeech();
          Alert alert = new Alert(AlertType.CONFIRMATION, "The game is finished, you have died.");
          alert.showAndWait();
          loadNewBaseFrame(stage, currentGame.resetGame(getOriginalStory()));
        }

        if (currentGame.getStory().getPassage(link).getLinks().isEmpty()) {
          Alert alert = new Alert(AlertType.CONFIRMATION, "Congratulations you have won the game.");
          alert.showAndWait();
          currentGame.resetGame(getOriginalStory());
          switchToMainMenu(stage);
        }
      }
    };
  }

  /**
   * Sets a new {@link CreateGameListener}.
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
        switchToMainMenu(stage);
      }

      /**
       * Creates a new game of the input values.
       *
       * @param chosenGoals The goals to add to the game.
       * @param playerName The player to add to the game.
       * @param chosenDifficulty The chosen difficulty for the game
       */
      @Override
      public void onCreateClicked(String pathOfFile, List<Goal> chosenGoals, String gameId,
                                  String playerName, Difficulty chosenDifficulty, Story selectedStory) {
        pathOStoryFile = pathOfFile;
        Player player = new Player.PlayerBuilder(playerName)
                .health(chosenDifficulty.getHealth())
                .build();
        try {
          currentGame = GameManager.getInstance()
                  .createGame(gameId, player, selectedStory, chosenGoals);
          loadNewBaseFrame(stage, currentGame.getStory().getOpeningPassage());
        } catch (NullPointerException | IllegalArgumentException e) {
          logger.log(Level.WARNING, e.getMessage(), e);
          Alert alert = new Alert(AlertType.WARNING, e.getMessage());
          alert.showAndWait();
        }
      }
    };
  }

  /**
   * Sets a new {@link MainMenuListener}.
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
        switchToCreateGameMenu(stage);
      }

      /**
       * Loads a menu for selecting a game to play.
       */
      @Override
      public void onLoadGameClicked() {
        loadStoredGames(stage);
      }

      /**
       * Loads the tutorial scene.
       */
      @Override
      public void onTutorialButtonClicked() {
        BaseFrameListener tutorialListener = new BaseFrameListener() {
          @Override
          public void onRestartClicked() {

          }

          @Override
          public void onExitClicked(boolean shouldSaveGame) {
            switchToMainMenu(stage);
          }

          @Override
          public void onOptionButtonClicked(Link link) {

          }
        };
        loadTutorial(stage, tutorialListener);
      }

      @Override
      public void onCreateStoryMenuClicked() {
        loadStoryCreator(stage);
      }

      @Override
      public void onExitClicked() {
        stage.close();
      }
    };
  }

  private void loadTutorial(Stage stage, BaseFrameListener baseFrameListener) {
    Player player = new Player.PlayerBuilder("Test name").health(100).score(100).gold(50).build();
    Passage openingPassage = new Passage("Test title", "Test content");
    Story story = new Story("Test title", openingPassage);
    BaseFrame tutorialFrame = new BaseFrame(story.getTitle(), openingPassage, player, FRAME_WIDTH,
        FRAME_HEIGHT, baseFrameListener);
    stage.setScene(new Scene(tutorialFrame));

  }

  /**
   * Sets a new {@link LoadStoredGamesListener}.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addLoadStoredGameListener(Stage stage) {
    loadStoredGamesListener = new LoadStoredGamesListener() {
      /**
       * Sets the current game to the selected game. And executes
       * {@link App#loadNewBaseFrame(Stage, Passage)}
       *
       * @param game The selected game.
       */
      @Override
      public void onSelectedGameClicked(Game game) {
        currentGame = game;
        loadNewBaseFrame(stage, game.getStory().getCurrentPassage());
      }

      /**
       * Switches the scene to the main menu.
       */
      @Override
      public void onReturnClicked() {
        switchToMainMenu(stage);
      }
    };
  }

  /**
   * Sets a new {@link StoryCreatorListener}.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addStoryCreatorListener(Stage stage) {
    storyCreatorListener = new StoryCreatorListener() {

      /**
       * Switches the scene to the main menu.
       */
      @Override
      public void onReturnClicked() {
        switchToMainMenu(stage);
      }
    };
  }

  /**
   * Loads a new {@link MainMenu} to the stage.
   *
   * @param stage The stage to load the {@link MainMenu} to.
   */
  private void switchToMainMenu(Stage stage) {
    MainMenu mainMenu = new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener);
    Scene scene = new Scene(mainMenu);
    setStyleSheet(scene, STANDARD_STYLING);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Loads a new {@link CreateGameMenu} to the stage.
   *
   * @param stage The stage to load the {@link CreateGameMenu} to.
   */
  private void switchToCreateGameMenu(Stage stage) {
    CreateGameMenu createGameMenu = new CreateGameMenu(FRAME_WIDTH, FRAME_HEIGHT,
        createGameListener);
    Scene scene = new Scene(createGameMenu);
    setStyleSheet(scene, STANDARD_STYLING);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Loads a new {@link BaseFrame} to the stage.
   *
   * @param stage The stage to load the {@link BaseFrame} to.
   */
  private void loadNewBaseFrame(Stage stage, Passage passage) {
    BaseFrame currentFrame = new BaseFrame(currentGame.getStory().getTitle(), passage,
        currentGame.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, baseFrameListener);
    this.currentPassage = passage;
    Scene scene = new Scene(currentFrame);
    setStyleSheet(scene, STANDARD_STYLING);
    stage.setScene(scene);
  }

  /**
   * Loads a {@link edu.ntnu.idatt2001.paths.gui.storycreation.StoryCreator StoryCreator} to the
   * stage.
   *
   * @param stage The stage to load the
   *              {@link edu.ntnu.idatt2001.paths.gui.storycreation.StoryCreator StoryCreator} to.
   */
  private void loadStoryCreator(Stage stage) {
    ScrollableStoryCreator scrollableStoryCreator = new ScrollableStoryCreator(FRAME_WIDTH,
        FRAME_HEIGHT, storyCreatorListener);
    stage.setScene(new Scene(scrollableStoryCreator));
  }

  /**
   * Loads a {@link LoadStoredGamesMenu} to the stage.
   *
   * @param stage The stage to load the {@link LoadStoredGamesMenu} to.
   */
  private void loadStoredGames(Stage stage) {
    LoadStoredGamesMenu loadStoredGamesMenu = new LoadStoredGamesMenu(FRAME_WIDTH, FRAME_HEIGHT,
        loadStoredGamesListener);
    Scene scene = new Scene(loadStoredGamesMenu);
    setStyleSheet(scene, STANDARD_STYLING);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Sets the stage close request to close the currently running JVM.
   *
   * @param stage The stage to set the action binding to.
   */
  private void setCloseAction(Stage stage) {
    stage.setOnCloseRequest(event -> {
      Platform.exit();
      System.exit(0);
    });
  }
}
