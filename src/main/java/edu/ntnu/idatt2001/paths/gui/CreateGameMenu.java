package edu.ntnu.idatt2001.paths.gui;

import edu.ntnu.idatt2001.paths.Difficulty;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.goals.GoldGoal;
import edu.ntnu.idatt2001.paths.goals.HealthGoal;
import edu.ntnu.idatt2001.paths.goals.ScoreGoal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class that represents a menu for creating a game. Holds input fields for all values needed to
 * create a game. Includes input validation to make sure that all input is valid.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class CreateGameMenu extends Pane {

  private final CreateGameListener listener;

  private final ComboBox<Difficulty> difficultyBox;
  private InputField playerNameInput;
  private InputField healthGoalInput;
  private InputField goldGoalInput;
  private InputField inventoryGoalInput;
  private InputField scoreGoalInput;

  /**
   * Constructor for a CreateGameMenu object.
   *
   * @param width    The width of the frame.
   * @param height   The height of the frame.
   * @param listener The listener used to activate button functionality.
   */
  public CreateGameMenu(int width, int height, CreateGameListener listener) {

    setWidth(width);
    setHeight(height);
    setPrefWidth(width);
    setPrefHeight(height);
    this.listener = listener;
    this.difficultyBox = new ComboBox<>();
    createDifficultyBox();
    setHeadLine();
    setTextInputField();
    addCreateGameButton();
    addReturnButton();
  }

  /**
   * Sets "Create" as a headline of the frame.
   */
  private void setHeadLine() {

    Text headLine = new Text("Create");
    headLine.setFont(Font.font("Arial", 30));
    headLine.setWrappingWidth(getWidth() / 2);
    headLine.setTextOrigin(VPos.CENTER);
    headLine.setLayoutX((getWidth() - headLine.getWrappingWidth()) / 2);
    headLine.setLayoutY(getHeight() * 0.05);
    headLine.setTextAlignment(TextAlignment.CENTER);

    getChildren().add(headLine);
  }

  /**
   * Creates a SplitPane with all input fields and their corresponding text fields.
   */
  private void setTextInputField() {

    SplitPane splitPane = new SplitPane();
    VBox leftControl = addInputText();
    VBox rightControl = addInputFields();

    splitPane.getItems().addAll(leftControl, rightControl);

    TitledPane titledPane = new TitledPane("Please fill out all boxes", splitPane);
    titledPane.setAnimated(false);
    titledPane.setCollapsible(false);
    titledPane.setPrefWidth(getWidth() / 2);
    titledPane.setPrefHeight(0);
    titledPane.setLayoutX((getWidth() - titledPane.getPrefWidth()) / 2);
    titledPane.setLayoutY((getWidth() * 0.25));
    getChildren().add(titledPane);


  }

  /**
   * Creates a VBox containing all input fields.
   *
   * @return A VBox containing all input fields.
   */
  private VBox addInputFields() {
    VBox rightControl = new VBox();
    rightControl.setSpacing(10);
    rightControl.setPadding(new Insets(10, 10, 10, 10));

    playerNameInput = new InputField(30, 30, false, true);
    healthGoalInput = new InputField(30, 30, true, true);
    goldGoalInput = new InputField(30, 30, true, true);
    inventoryGoalInput = new InputField(30, 30, true, true);
    scoreGoalInput = new InputField(30, 30, true, true);

    healthGoalInput.setHint("Integer between 1 and 100");
    rightControl.getChildren().add(difficultyBox);

    ArrayList<InputField> inputFields = new ArrayList<>(
        Arrays.asList(playerNameInput, healthGoalInput, goldGoalInput, inventoryGoalInput,
            scoreGoalInput));

    for (InputField inputField : inputFields) {
      rightControl.getChildren().add(inputField);
      inputField.setPrefferedPicture();
      inputField.getTextArea().setOnKeyReleased(action -> inputField.setPrefferedPicture());

    }
    return rightControl;
  }

  /**
   * Creates a VBox of all text for explaining input fields.
   *
   * @return a VBox of all text for explaining input fields.
   */
  private VBox addInputText() {
    VBox leftControl = new VBox();
    leftControl.setSpacing(15);
    leftControl.setPadding(new Insets(10, 10, 10, 10));

    Text difficulty = new Text("Difficulty:");
    Text playerName = new Text("Player name:");
    Text healthGoal = new Text("HealthGoal:");
    Text goldGoal = new Text("goldGoal:");
    Text inventoryGoal = new Text("inventoryGoal:");
    Text scoreGoal = new Text("scoreGoal:");

    leftControl.getChildren()
        .addAll(difficulty, new Separator(), playerName, new Separator(), healthGoal,
            new Separator(), goldGoal, new Separator(), inventoryGoal, new Separator(), scoreGoal);
    return leftControl;
  }

  /**
   * Creates a difficulty box with all the games difficulties.
   */
  private void createDifficultyBox() {
    difficultyBox.setPrefWidth(100000000);

    difficultyBox.getItems()
        .addAll(Arrays.asList(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD));
  }

  /**
   * Adds a return button to the frame, and adds the listener to it. Retrieves all game data of the
   * created game, and sends it to the listener.
   */
  private void addReturnButton() {
    Button returnButton = new Button("Return");
    returnButton.setPrefWidth(getWidth() / 15);
    returnButton.setOnAction(event -> listener.onReturnClicked());
    returnButton.setLayoutX(0);
    returnButton.setLayoutY(0);
    getChildren().add(returnButton);

  }

  /**
   * Adds a "create game" button and adds the listener to it.
   */
  private void addCreateGameButton() {
    Button createGame = new Button("Create");
    createGame.setPrefWidth(getWidth() / 15);

    createGame.disableProperty().bind(invalidInput());

    createGame.setOnAction(event -> {

      List<Goal> chosenGoals = getGoalInputData();
      String chosenName = getChosenName();

      try {
        getChosenDifficulty();
        listener.onCreateClicked(chosenGoals, chosenName, getChosenDifficulty());
      } catch (Exception e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setContentText("Please choose a valid difficulty");
        alert.show();
      }

    });

    Pane createButtonPane = new Pane();
    createButtonPane.setLayoutX(getWidth() - getWidth() / 15);
    createButtonPane.setLayoutY(getHeight() * 0.3);

    createButtonPane.setOnMouseEntered(event -> {
      if (invalidInput().get()) {
        Tooltip tooltip = new Tooltip("Please fill all fields correctly");
        Tooltip.install(createButtonPane, tooltip);
      }

    });

    createButtonPane.getChildren().add(createGame);
    getChildren().add(createButtonPane);

  }

  /**
   * BooleanBinding for checking if any input is invalid.
   *
   * @return A BooleanBinding representing true if any input is invalid, if not, it will represent
   *        true.
   */
  private BooleanBinding invalidInput() {
    return playerNameInput.getTextArea().textProperty().isEmpty()
        .or(goldGoalInput.getTextArea().textProperty().isEmpty())
        .or(inventoryGoalInput.getTextArea().textProperty().isEmpty())
        .or(scoreGoalInput.getTextArea().textProperty().isEmpty())
        .or(healthGoalInput.getTextArea().textProperty().isEmpty())
        .or(difficultyBox.valueProperty().isNull()).or(inputIsNotParseable());
  }

  /**
   * BooleanBinding for checking if all inputs that should be parseable are so.
   *
   * @return A BooleanBinding representing true parseable inputs are parseable, if not, it will
   *        represent true.
   */
  private BooleanBinding inputIsNotParseable() {
    return Bindings.createBooleanBinding(
        () -> !(goldGoalInputValid() && healthGoalInputValid() && inventoryGoalInputValid()
            && scoreGoalInputValid()), goldGoalInput.getTextArea().textProperty(),
        inventoryGoalInput.getTextArea().textProperty(),
        scoreGoalInput.getTextArea().textProperty(), healthGoalInput.getTextArea().textProperty());
  }

  private List<Goal> getGoalInputData() {
    List<Goal> listOfGoals = new ArrayList<>();

    try {
      HealthGoal healthGoal = new HealthGoal(
          Integer.parseInt(healthGoalInput.getTextArea().getText()));
      GoldGoal goldGoal = new GoldGoal(Integer.parseInt(goldGoalInput.getTextArea().getText()));
      ScoreGoal scoreGoal = new ScoreGoal(Integer.parseInt(scoreGoalInput.getTextArea().getText()));

      listOfGoals.add(healthGoal);
      listOfGoals.add(goldGoal);
      listOfGoals.add(scoreGoal);

    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText("All Goal input fields should be numerical values");
      alert.show();
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText(e.getMessage());
      alert.show();
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
   * Checks if the gold goal input is valid.
   *
   * @return True if the gold goal input is valid, false if not.
   */
  private boolean goldGoalInputValid() {
    try {
      Integer.parseInt(goldGoalInput.getTextArea().getText());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks if the inventory goal input is valid.
   *
   * @return True if the inventory goal input is valid, false if not.
   */
  private boolean inventoryGoalInputValid() {
    try {
      Integer.parseInt(inventoryGoalInput.getTextArea().getText());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks if the score goal input is valid.
   *
   * @return True if the score goal input is valid, false if not.
   */
  private boolean scoreGoalInputValid() {
    try {
      Integer.parseInt(scoreGoalInput.getTextArea().getText());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Checks if the health goal input is valid.
   *
   * @return True if the health goal input is valid, false if not.
   */
  private boolean healthGoalInputValid() {
    try {
      Integer.parseInt(healthGoalInput.getTextArea().getText());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


}
