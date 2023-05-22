package edu.ntnu.idatt2001.paths.view.menus;

import edu.ntnu.idatt2001.paths.model.Difficulty;
import edu.ntnu.idatt2001.paths.model.filehandling.FileStoryHandler;
import edu.ntnu.idatt2001.paths.model.goals.Goal;
import edu.ntnu.idatt2001.paths.model.goals.GoldGoal;
import edu.ntnu.idatt2001.paths.model.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.model.goals.InventoryGoal;
import edu.ntnu.idatt2001.paths.model.goals.ScoreGoal;
import edu.ntnu.idatt2001.paths.view.listeners.CheckListListener;
import edu.ntnu.idatt2001.paths.view.listeners.CreateGameListener;
import edu.ntnu.idatt2001.paths.view.uielements.CheckListView;
import edu.ntnu.idatt2001.paths.view.uielements.InputField;
import edu.ntnu.idatt2001.paths.view.utility.GuiUtils;
import edu.ntnu.idatt2001.paths.model.Link;
import edu.ntnu.idatt2001.paths.model.Story;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Class that represents a menu for creating a game. Holds input fields for all values needed to
 * create a game. Includes input validation to make sure that all input is valid.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since April 26, 2023.
 */
public class CreateGameMenu extends BorderPane {

  private static final Logger logger = Logger.getLogger(CreateGameMenu.class.getName());
  private static final String STORY_LOCATION = "src/main/resources/stories";
  private final Button inventoryButton;
  private final GridPane infoGrid;
  private final ComboBox<String> storyBox;
  private final CreateGameListener listener;
  private final double buttonWidth;
  private ComboBox<Difficulty> difficultyBox;
  private InputField playerNameInput;
  private InputField healthGoalInput;
  private InputField goldGoalInput;
  private InputField scoreGoalInput;
  private List<String> inputInventoryGoals;
  private Stage inventoryCheckList;
  private Hyperlink hyperlink;
  private InputField gameId;

  /**
   * Constructor for a CreateGameMenu object.
   *
   * @param width    The width of the frame.
   * @param height   The height of the frame.
   * @param listener The listener used to activate button functionality.
   * @throws NullPointerException if the listener is null.
   */
  public CreateGameMenu(double width, double height, CreateGameListener listener)
      throws NullPointerException {

    this.listener = Objects.requireNonNull(listener, "CreateGameListener cannot be null.");

    buttonWidth = width / 4;
    this.difficultyBox = new ComboBox<>();
    this.storyBox = new ComboBox<>();
    this.inventoryButton = new Button("Select");
    this.infoGrid = new GridPane();

    GuiUtils.setBackgroundImage(this, "images/forestadventure/beginnings.png");
    GuiUtils.setHeadline(this, "Create game", 40, 0, 30, 0, 30);
    GuiUtils.createReturnButton(this, "Return", listener::onReturnClicked, Pos.TOP_CENTER);

    setPadding(new Insets(20));
    setPrefWidth(width);
    setPrefHeight(height);
    createGrid();
    createUploadFilesHyperLink();
    addInputs();
    addInputLabels();
    addCreateGameButton();
  }

  /**
   * Creates a {@link GridPane} and adds it to the {@link Pos#CENTER} of the pane.
   */
  private void createGrid() {
    infoGrid.setHgap(10);
    infoGrid.setVgap(10);

    VBox gridHolder = new VBox();
    gridHolder.getChildren().add(infoGrid);
    gridHolder.setAlignment(Pos.CENTER);
    gridHolder.setPadding(new Insets(30));

    infoGrid.setAlignment(Pos.CENTER);
    gridHolder.setStyle("-fx-background-color: #e8e4e4; -fx-border-color: #000000");
    setCenter(gridHolder);
  }

  /**
   * Creates a VBox containing all input fields.
   */
  private void addInputs() {

    playerNameInput = new InputField(buttonWidth, 30);
    playerNameInput.setShouldHaveText(true);

    healthGoalInput = new InputField(buttonWidth, 30);
    healthGoalInput.setShouldBePositiveInteger(true);

    goldGoalInput = new InputField(buttonWidth, 30);
    goldGoalInput.setShouldBePositiveInteger(true);

    scoreGoalInput = new InputField(buttonWidth, 30);
    scoreGoalInput.setShouldBePositiveInteger(true);

    gameId = new InputField(buttonWidth, 30);
    gameId.setShouldHaveText(true);

    updateStoryBox();
    difficultyBox = createDifficultyBox();
    createInventoryButton();

    ArrayList<Node> inputFields = new ArrayList<>(
        Arrays.asList(gameId, playerNameInput, healthGoalInput, goldGoalInput, scoreGoalInput,
            inventoryButton, storyBox, difficultyBox, hyperlink));

    for (int i = 0; i < inputFields.size(); i++) {
      infoGrid.add(inputFields.get(i), 1, i);
    }
  }

  /**
   * Adds all input labels to the menu.
   */
  private void addInputLabels() {
    Label gameIdLabel = new Label("Game ID:");
    Label difficulty = new Label("Difficulty:");
    Label playerName = new Label("Player name:");
    Label healthGoal = new Label("Health goal:");
    Label goldGoal = new Label("Gold goal:");
    Label inventoryGoal = new Label("Inventory goal:");
    Label scoreGoal = new Label("Score goal:");
    Label story = new Label("Story:");

    List<Label> inputDescriptors = Arrays.asList(gameIdLabel, playerName, healthGoal, goldGoal,
        scoreGoal, inventoryGoal, story, difficulty);
    for (int i = 0; i < inputDescriptors.size(); i++) {
      infoGrid.add(inputDescriptors.get(i), 0, i);
    }
  }

  /**
   * Creates a button that opens a {@link CheckListView} showing all possible inventory items to the
   * user and adds it to the menu.
   */
  private void createInventoryButton() {
    String[] possibleItems = new File("src/main/resources/items").list();

    if (possibleItems == null) {
      new Alert(AlertType.WARNING, "Cannot locate any inventory items").showAndWait();
      return;
    }

    Arrays.setAll(possibleItems, i -> possibleItems[i].replace(".jpg", "").replace(".png", ""));
    CheckListListener checkListListener = selectedGoals -> inputInventoryGoals = selectedGoals;
    CheckListView checkList = new CheckListView(checkListListener);

    inventoryButton.setPrefWidth(buttonWidth);
    inventoryButton.setOnAction(actionEvent -> {
      checkList.setListItems(List.of(possibleItems));
      if (inventoryCheckList == null) {
        inventoryCheckList = new Stage();
        inventoryCheckList.setScene(new Scene(checkList));
      }
      inventoryCheckList.show();
    });
  }

  /**
   * Creates a difficulty box with all the games difficulties.
   */
  private ComboBox<Difficulty> createDifficultyBox() {
    ComboBox<Difficulty> difficulties = new ComboBox<>();
    difficulties.setPrefWidth(buttonWidth);
    difficulties.getItems()
        .addAll(Arrays.asList(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD));
    return difficulties;
  }

  /**
   * Updates the {@link #storyBox} with all found stories.
   */
  private void updateStoryBox() {
    storyBox.getItems().removeAll(storyBox.getItems());
    storyBox.getItems().addAll(findAllDirectoryFiles(STORY_LOCATION));
    storyBox.setPrefWidth(buttonWidth);
  }

  /**
   * Adds a "create game" button and adds the listener to it.
   */
  private void addCreateGameButton() {
    Button createGame;
    createGame = new Button("Create");
    createGame.setOnAction(event -> createGameClicked());
    createGame.disableProperty().bind(inputIsValid().not());

    VBox createButtonPane = new VBox();
    createButtonPane.setAlignment(Pos.TOP_CENTER);
    createButtonPane.setPadding(new Insets(0, 0, 0, 40));

    createButtonPane.setOnMouseEntered(event -> {
      if (createGame.isDisable()) {
        Tooltip tooltip = new Tooltip("Please fill all fields correctly");
        Tooltip.install(createButtonPane, tooltip);
      }
    });
    createButtonPane.getChildren().add(createGame);
    setRight(createButtonPane);
  }

  /**
   * Sends information used to create a game to the listener by using the
   * {@link CreateGameListener#onCreateClicked(String, List, String, String, Difficulty, Story)
   * onCreateClicked} method.
   */
  private void createGameClicked() {
    List<Goal> chosenGoals = getGoalInputData();
    String chosenName = getChosenName();
    String id = gameId.getTextArea().getText();
    Story selectedStory;

    try {
      getChosenDifficulty();
      String pathOfFile = "src/main/resources/stories/" + storyBox.getValue();
      selectedStory = FileStoryHandler.readStoryFromFile(pathOfFile);

      if (invalidActionsAlert(FileStoryHandler.getInvalidActions()) && brokenLinksAlert(
          selectedStory.getBrokenLinks())) {

        listener.onCreateClicked(pathOfFile, chosenGoals, id, chosenName, getChosenDifficulty(),
            selectedStory);
      }

    } catch (IllegalArgumentException | NullPointerException | IOException e) {
      String errorMessage = "Error while creating game: " + e.getMessage();
      logAndDisplayError(e, errorMessage, Level.SEVERE, AlertType.ERROR);
    }
  }

  /**
   * {@link BooleanBinding} for checking if all inputs that should be parseable are so.
   *
   * @return A BooleanBinding representing true parseable inputs are parseable, if not, it will
   * represent true.
   */
  private BooleanBinding inputIsValid() {
    return Bindings.createBooleanBinding(
        () -> (goldGoalInput.hasValidInput() && scoreGoalInput.hasValidInput()
            && storyBox.getValue() != null && difficultyBox.getValue() != null
            && playerNameInput.hasValidInput() && healthGoalInput.hasValidInput()),
        goldGoalInput.getTextArea().textProperty(), scoreGoalInput.getTextArea().textProperty(),
        healthGoalInput.getTextArea().textProperty(), difficultyBox.valueProperty(),
        storyBox.valueProperty(),

        playerNameInput.getTextArea().textProperty());
  }

  /**
   * Parses all {@link Goal} inputs to goals to create a list goals from the input goals of the
   * user.
   *
   * @return A list of all {@link Goal goals} specified by the user.
   */
  private List<Goal> getGoalInputData() {
    List<Goal> listOfGoals = new ArrayList<>();

    if (!healthGoalInput.getTextArea().getText().isEmpty()) {
      listOfGoals.add(new HealthGoal(Integer.parseInt(healthGoalInput.getTextArea().getText())));
    }

    if (!goldGoalInput.getTextArea().getText().isEmpty()) {
      listOfGoals.add(new GoldGoal(Integer.parseInt(goldGoalInput.getTextArea().getText())));
    }

    if (!scoreGoalInput.getTextArea().getText().isEmpty()) {
      listOfGoals.add(new ScoreGoal(Integer.parseInt(scoreGoalInput.getTextArea().getText())));
    }

    if (inputInventoryGoals != null && !inputInventoryGoals.isEmpty()) {
      listOfGoals.add(new InventoryGoal(inputInventoryGoals));
    }
    return listOfGoals;
  }

  /**
   * Gets the name that the user has chosen for the player.
   *
   * @return the name that the user has chosen for the player.
   */
  private String getChosenName() {
    return playerNameInput.getTextArea().getText();
  }

  /**
   * Gets the difficulty that the user has chosen.
   *
   * @return The difficulty that the user has chosen
   * @throws IllegalArgumentException If the difficultyBox does not have a difficulty.
   * @throws NullPointerException     If the difficultyBox is null.
   */
  private Difficulty getChosenDifficulty() throws IllegalArgumentException, NullPointerException {
    return Difficulty.parseToDifficulty(difficultyBox.getValue().toString());
  }

  /**
   * Finds all files in the director given by the location.
   *
   * @param location The location of the directory.
   * @return All files located in the given directory
   */
  private String[] findAllDirectoryFiles(String location) {
    return new File(location).list();
  }

  /**
   * Creates a button for uploading a paths file from the user.
   * <li>If the file cannot be read or written, the user will be alerted.</li>
   * <li>Only files ending with ".paths" can be submitted.</li>
   */
  private void createUploadFilesHyperLink() {
    hyperlink = new Hyperlink("Upload your own files here");
    hyperlink.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters()
          .addAll(new ExtensionFilter("Path files (*.paths)", "*.paths"));
      Stage stage = (Stage) hyperlink.getScene().getWindow();
      File selectedFile = fileChooser.showOpenDialog(stage);
      if (selectedFile == null) {
        return;
      }
      try {
        Story story = FileStoryHandler.readStoryFromFile(selectedFile.getPath());
        if (brokenLinksAlert(story.getBrokenLinks()) && invalidActionsAlert(
            FileStoryHandler.getInvalidActions())) {

          String pathOfFile = story.getTitle().trim().toLowerCase().replace(" ", "_");

          FileStoryHandler.writeStoryToFile(story,
              "src/main/resources/stories/" + pathOfFile + ".paths");
          updateStoryBox();
        }
      } catch (IllegalArgumentException | NullPointerException | IOException e) {
        logAndDisplayError(e, e.getMessage(), Level.WARNING, AlertType.ERROR);
      }
    });
  }

  /**
   * The method checks if there are any invalid actions in the provided list and displays a warning
   * alert if any invalid actions are found.
   *
   * @param invalidActions the list of invalid actions to check.
   * @return true if no invalid actions or of user confirms the continuation, false otherwise.
   * @throws NullPointerException if the list of invalid actions is null.
   */
  private boolean invalidActionsAlert(List<String> invalidActions) throws NullPointerException {
    if (invalidActions == null) {
      throw new NullPointerException("The list of invalid actions cannot be null.");
    }

    if (!invalidActions.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder().append("The chosen story has ")
          .append(invalidActions.size()).append(" invalid actions.");
      for (String action : invalidActions) {
        stringBuilder.append("\n - ").append(action);
      }
      stringBuilder.append("\n\nAre you sure you want to continue?");
      return createConfirmationAlert(AlertType.WARNING, stringBuilder.toString());
    }
    return true;
  }

  /**
   * The method checks if there are any broken links in the provided list and displays a warning
   * alert if any broken links are found.
   *
   * @param brokenLinks the list of broken links to check.
   * @return true if no broken links or of user confirms the continuation, false otherwise.
   * @throws NullPointerException if the list of broken links is null.
   */
  private boolean brokenLinksAlert(List<Link> brokenLinks) throws NullPointerException {
    if (brokenLinks == null) {
      throw new NullPointerException("The list of broken links cannot be null.");
    }

    if (!brokenLinks.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder().append("The chosen story has ")
          .append(brokenLinks.size()).append(" broken links..");
      for (Link link : brokenLinks) {
        stringBuilder.append("\n - ").append(link.getText()).append("  ->  ")
            .append(link.getReference());
      }
      stringBuilder.append("\n\nAre you sure you want to continue?");
      return createConfirmationAlert(AlertType.WARNING, stringBuilder.toString());
    }
    return true;
  }

  /**
   * The method creates and displays an alert dialog with the specified alert type and message. The
   * alert contains custom buttons, "Yes" and "No".
   *
   * @param alertType    the type of the alert.
   * @param alterMessage the type of the alert.
   * @return true if the yes button is clicked, false otherwise.
   */
  private boolean createConfirmationAlert(AlertType alertType, String alterMessage) {
    Alert alert = new Alert(alertType, alterMessage);
    ButtonType yesButton = new ButtonType("Yes");
    ButtonType noButton = new ButtonType("No");
    alert.getButtonTypes().setAll(yesButton, noButton);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == yesButton;
  }

  /**
   * The method creates and displays an alert dialog with the specified exception, error message,
   * log level, and alert type.
   *
   * @param e            the exception to be logged.
   * @param errorMessage the error message to be logged and displayed.
   * @param level        the log level for logging the exception.
   * @param alertType    the type of the alert.
   */
  private void logAndDisplayError(Exception e, String errorMessage, Level level,
                                  AlertType alertType) {
    logger.log(level, errorMessage, e);
    Alert alert = new Alert(alertType, errorMessage);
    alert.showAndWait();
  }
}
