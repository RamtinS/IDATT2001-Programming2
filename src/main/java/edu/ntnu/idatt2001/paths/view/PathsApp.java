package edu.ntnu.idatt2001.paths.view;

import com.google.gson.JsonSyntaxException;
import edu.ntnu.idatt2001.paths.model.actions.Action;
import edu.ntnu.idatt2001.paths.controller.GameManager;
import edu.ntnu.idatt2001.paths.model.filehandling.FileStoryHandler;
import edu.ntnu.idatt2001.paths.model.goals.Goal;
import edu.ntnu.idatt2001.paths.view.listeners.BaseFrameListener;
import edu.ntnu.idatt2001.paths.view.listeners.CreateGameListener;
import edu.ntnu.idatt2001.paths.view.listeners.MainMenuListener;
import edu.ntnu.idatt2001.paths.view.listeners.StoredGamesListener;
import edu.ntnu.idatt2001.paths.view.listeners.StoryCreatorListener;
import edu.ntnu.idatt2001.paths.view.listeners.TutorialListener;
import edu.ntnu.idatt2001.paths.view.menus.BaseFrame;
import edu.ntnu.idatt2001.paths.view.menus.CreateGameMenu;
import edu.ntnu.idatt2001.paths.view.menus.MainMenu;
import edu.ntnu.idatt2001.paths.view.menus.StoredGamesMenu;
import edu.ntnu.idatt2001.paths.view.menus.Tutorial;
import edu.ntnu.idatt2001.paths.view.storycreation.ScrollableStoryCreator;
import edu.ntnu.idatt2001.paths.model.Difficulty;
import edu.ntnu.idatt2001.paths.model.Game;
import edu.ntnu.idatt2001.paths.model.Link;
import edu.ntnu.idatt2001.paths.model.Passage;
import edu.ntnu.idatt2001.paths.model.Player;
import edu.ntnu.idatt2001.paths.model.Story;
import edu.ntnu.idatt2001.paths.model.tts.TextToSpeech;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * The class is the main entry point for the Paths application.
 * It extends the JavaFX Application class and provides the necessary
 * methods for users to create their own games, load existing games,
 * creat their own stories, and learn from the tutorial.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 19, 2023.
 */
public class PathsApp extends Application {

  private static final Logger logger = Logger.getLogger(PathsApp.class.getName());
  private static final int FRAME_HEIGHT = 650;
  private static final int FRAME_WIDTH = 1050;
  private static final String STANDARD_STYLING =
      "file:src/main/resources/stylesheets/standard.css";
  private Game currentGame;
  private Passage currentPassage;
  private String pathOfStoryFile;
  private TutorialListener loadTutorialListener;
  private MainMenuListener mainMenuListener;
  private BaseFrameListener baseFrameListener;
  private CreateGameListener createGameListener;
  private StoredGamesListener loadStoredGamesListener;
  private StoryCreatorListener storyCreatorListener;
  private List<Goal> completedGoals;

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
             | IOException | JsonSyntaxException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
    completedGoals = new ArrayList<>();
    stage.setTitle("Paths");
    addBaseFrameListener(stage);
    addMainMenuListener(stage);
    addCreateGameListener(stage);
    addLoadStoredGameListener(stage);
    addStoryCreatorListener(stage);
    addLoadTutorialListener(stage);
    loadMainMenu(stage);
    setCloseAction(stage);
    setStageSizes(stage);
  }

  /**
   * The method sets a stylesheet for the given scene.
   *
   * @param scene The scene to apply the stylesheet to.
   * @param filePath The file path of the stylesheet to be applied.
   */
  private void setStyleSheet(Scene scene, String filePath) {
    scene.getStylesheets().add(filePath);
  }

  /**
   * The method sets the minimum and maximum width and height for the given stage.
   *
   * @param stage the stage to set the sizes for.
   */
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
      story = FileStoryHandler.readStoryFromFile(pathOfStoryFile);
    } catch (NullPointerException | IllegalArgumentException | IOException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
    return story;
  }

  /**
   * Sets a new {@link BaseFrameListener}.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addBaseFrameListener(Stage stage) {
    baseFrameListener = new BaseFrameListener() {

      /**
       * Restarts the game and takes the player to the opening passage.
       */
      @Override
      public void onRestartClicked() {
        completedGoals.clear();
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
            loadMainMenu(stage);

          } catch (IOException | NullPointerException | IllegalArgumentException
                   | IllegalStateException e) {

            String errorMessage = "The game could not be saved due to an error: " + e.getMessage();
            logAndDisplayError(e, errorMessage, Level.SEVERE, AlertType.ERROR);
            loadMainMenu(stage);
          }
        } else {
          loadMainMenu(stage);
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

        checkForGoals(currentGame.getGoals(), currentGame.getPlayer());

        link.getActions().clear();
        BaseFrame newFrame;
        try {
          newFrame = new BaseFrame(currentGame.getStory().getTitle(), currentGame.go(link),
              currentGame.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, this);
          currentPassage = currentGame.go(link);
        } catch (NullPointerException | NoSuchElementException e) {
          String errorMessage = "Unable to continue the story. " + e.getMessage();
          logAndDisplayError(e, errorMessage, Level.WARNING, AlertType.WARNING);
          return;
        }
        Scene scene = new Scene(newFrame);
        setStyleSheet(scene, STANDARD_STYLING);
        stage.setScene(scene);
        stage.show();

        if (currentGame.getPlayer().getHealth() <= 0) {
          completedGoals.clear();
          TextToSpeech.getInstance().resetSpeech();
          Alert alert = new Alert(AlertType.INFORMATION, "The game is finished, you have died.");
          alert.showAndWait();
          loadNewBaseFrame(stage, currentGame.resetGame(getOriginalStory()));
        } else if (currentGame.getStory().getPassage(link).getLinks().isEmpty()) {
          Alert alert = new Alert(AlertType.INFORMATION, "Congratulations you have won the game.");
          alert.showAndWait();
        }
      }
    };
  }

  /**
   * Checks if there are any achieved goals in a list of goals on a given player.
   * <p>
   * If the goal is fulfilled, a notification will be shown to the user, containing information
   * about the achieved goal.
   * </p>
   *
   * @param goals  The used to look for achieved goals
   * @param player The
   */
  private void checkForGoals(List<Goal> goals, Player player) {
    for (Goal goal : goals) {
      if (goal.isFulfilled(player) && !completedGoals.contains(goal)) {
        completedGoals.add(goal);
        Notifications.create().title("Goal achieved").text(goal.toString())
                .threshold(10, Notifications.create().title("Collapsed Notification"))
                .position(Pos.TOP_RIGHT)
                .showWarning();
      }
    }
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
        loadMainMenu(stage);
      }

      /**
       * Creates a new game of the input values.
       *
       * @param chosenGoals The goals to add to the game.
       * @param playerName The player to add to the game.
       * @param chosenDifficulty The chosen difficulty for the game
       */
      @Override
      public void onCreateClicked(String pathOfFile, List<Goal> chosenGoals,
                                  String gameId, String playerName,
                                  Difficulty chosenDifficulty, Story selectedStory) {
        pathOfStoryFile = pathOfFile;
        Player player = new Player.PlayerBuilder(playerName)
                .health(chosenDifficulty.getHealth())
                .build();
        try {
          currentGame = GameManager.getInstance()
                  .createGame(gameId, player, selectedStory, chosenGoals);
          loadNewBaseFrame(stage, currentGame.getStory().getOpeningPassage());
        } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
          logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
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
       * Loads a create game menu to the stage.
       */
      @Override
      public void onNewGameClicked() {
        loadCreateGameMenu(stage);
      }

      /**
       * Loads the menu for stored games.
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
        loadTutorial(stage);
      }

      /**
       * Loads the story creat scene.
       */
      @Override
      public void onCreateStoryMenuClicked() {
        loadStoryCreator(stage);
      }

      /**
       * Exits the application.
       */
      @Override
      public void onExitClicked() {
        Platform.exit();
        System.exit(0);
      }
    };
  }

  /**
   * Sets a new {@link StoredGamesListener}.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addLoadStoredGameListener(Stage stage) {
    loadStoredGamesListener = new StoredGamesListener() {

      /**
       * Sets the current game to the selected game. And executes
       * {@link PathsApp#loadNewBaseFrame(Stage, Passage)}
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
        loadMainMenu(stage);
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
        stage.setResizable(true);
        loadMainMenu(stage);
      }
    };
  }

  /**
   * Sets a new {@link TutorialListener}.
   *
   * @param stage The stage to add the button functionality to.
   */
  private void addLoadTutorialListener(Stage stage) {
    loadTutorialListener = new TutorialListener() {

      /**
       * Switches the scene to the main menu.
       */
      @Override
      public void onReturnClicked() {
        loadMainMenu(stage);
      }
    };
  }

  /**
   * Loads the tutorial onto the specified stage.
   *
   * @param stage The stage to load the tutorial onto.
   */
  private void loadTutorial(Stage stage) {
    try {
      Tutorial tutorial = new Tutorial(FRAME_WIDTH, FRAME_HEIGHT, loadTutorialListener);
      loadScene(stage, tutorial);
    } catch (NullPointerException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
  }

  /**
   * Loads the MainMenu to the stage.
   *
   * @param stage The stage to load the MainMenu onto.
   */
  private void loadMainMenu(Stage stage) {
    try {
      MainMenu mainMenu = new MainMenu(FRAME_WIDTH, FRAME_HEIGHT, mainMenuListener);
      loadScene(stage, mainMenu);
    } catch (NullPointerException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
  }

  /**
   * Loads the CreateGameMenu to the stage.
   *
   * @param stage The stage to load the CreateGameMenu onto.
   */
  private void loadCreateGameMenu(Stage stage) {
    try {
      CreateGameMenu createGameMenu = new CreateGameMenu(FRAME_WIDTH, FRAME_HEIGHT,
              createGameListener);
      loadScene(stage, createGameMenu);
    } catch (NullPointerException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
  }

  /**
   * Loads a new BaseFrame to the stage.
   *
   * @param stage The stage to load the base frame onto.
   */
  private void loadNewBaseFrame(Stage stage, Passage passage) {
    try {
      BaseFrame currentFrame = new BaseFrame(currentGame.getStory().getTitle(), passage,
              currentGame.getPlayer(), FRAME_WIDTH, FRAME_HEIGHT, baseFrameListener);
      this.currentPassage = passage;
      loadScene(stage, currentFrame);
    } catch (NullPointerException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
  }

  /**
   * Loads the ScrollableStoryCreator onto the specified stage.
   *
   * @param stage The stage to load the ScrollableStoryCreator onto.
   */
  private void loadStoryCreator(Stage stage) {
    try {
      ScrollableStoryCreator scrollableStoryCreator = new ScrollableStoryCreator(FRAME_WIDTH,
              FRAME_HEIGHT, storyCreatorListener);
      stage.setResizable(false);
      loadScene(stage, scrollableStoryCreator);
    } catch (NullPointerException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
  }

  /**
   * Loads the StoredGamesMenu to the stage.
   *
   * @param stage The stage to load the StoredGamesMenu onto.
   */
  private void loadStoredGames(Stage stage) {
    try {
      StoredGamesMenu loadStoredGamesMenu = new StoredGamesMenu(FRAME_WIDTH, FRAME_HEIGHT,
              loadStoredGamesListener);
      loadScene(stage, loadStoredGamesMenu);
    } catch (NullPointerException | IllegalStateException e) {
      logAndDisplayError(e, e.getMessage(), Level.SEVERE, AlertType.ERROR);
    }
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

  /**
   * The method loads the provided scene onto the specified stage.
   *
   * @param stage The stage to load the scene onto.
   * @param root The root node of the scene.
   */
  private void loadScene(Stage stage, Parent root) {
    Scene scene = new Scene(root);
    setStyleSheet(scene, STANDARD_STYLING);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The method handles the given exception by logging it
   * and displaying an alert with the error message.
   *
   * @param e The exception to be handled.
   * @param errorMessage the error message to be logged and displayed.
   * @param level The log level to use for logging the exception.
   * @param alertType The type of alert to display.
   */
  private void logAndDisplayError(Exception e, String errorMessage,
                                  Level level, AlertType alertType) {
    logger.log(level, errorMessage, e);
    Alert alert = new Alert(alertType, errorMessage);
    alert.showAndWait();
  }
}
