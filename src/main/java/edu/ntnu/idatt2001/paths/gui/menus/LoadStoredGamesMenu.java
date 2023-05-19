package edu.ntnu.idatt2001.paths.gui.menus;

import edu.ntnu.idatt2001.paths.controller.GameManager;
import edu.ntnu.idatt2001.paths.filehandling.FileGameHandler;
import edu.ntnu.idatt2001.paths.gui.listeners.LoadStoredGamesListener;
import edu.ntnu.idatt2001.paths.model.Game;
import edu.ntnu.idatt2001.paths.model.Link;
import edu.ntnu.idatt2001.paths.model.Story;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Represents a menu used for loading stored games.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 12, 2023.
 */
public class LoadStoredGamesMenu extends BorderPane {

  private final LoadStoredGamesListener listener;
  private final GameManager gameManager;
  private TableView<Game> gameTable;
  private List<Game> gameList;
  private VBox rightButtonPane;
  private VBox leftButtonPane;
  private static final Logger LOGGER = Logger.getLogger(LoadStoredGamesMenu.class.getName());

  /**
   * Constructor for a LoadStoredGamesMenu object. Adds a table with selectable games.
   *
   * @param width    The width of the menu.
   * @param height   The height of the menu.
   * @param listener The listener used to execute button actions.
   * @throws IllegalArgumentException If the listener is null.
   */
  public LoadStoredGamesMenu(int width, int height, LoadStoredGamesListener listener)
      throws IllegalArgumentException {
    this.listener = Objects.requireNonNull(listener, "Listener cannot be null");
    gameManager = GameManager.getInstance();

    createRightButtonPane();
    createLefButtonPane();
    setBackgroundImage("images/forestadventure/beginnings.png");
    setHeadline("Saved games");
    setDimensions(width, height);
    fillGamesData();
    addGameTable();
    addButtons();
    setPadding(new Insets(20));
    showInvalidGames();
  }

  private void showInvalidGames() {
    List<String> invalidGames = FileGameHandler.getInvalidGames();

    if (invalidGames.size() == 0){
      return;
    }

    String alertMessage = "You have " + invalidGames.size() + " invalid games:/n";
    for (String invalidGame : invalidGames){
      alertMessage = alertMessage.concat(invalidGame);
    }
    Alert alert = new Alert(AlertType.ERROR, alertMessage);
    alert.showAndWait();
  }

  private void setDimensions(int width, int height) {
    setPrefWidth(width);
    setPrefHeight(height);
  }

  private void createLefButtonPane() {
    leftButtonPane = new VBox();
    leftButtonPane.setPadding(new Insets(0, 40, 0, 0));
    setLeft(leftButtonPane);
  }

  private void createRightButtonPane() {
    rightButtonPane = new VBox();
    rightButtonPane.setSpacing(20);
    rightButtonPane.setPadding(new Insets(0, 0, 0, 40));
    rightButtonPane.setAlignment(Pos.TOP_CENTER);
    rightButtonPane.setPrefWidth(getPrefWidth() * 0.2);
    setRight(rightButtonPane);
  }

  /**
   * Adds all buttons to the pane.
   * <p>
   * This includes:
   * <li>Return button</li>
   * <li>Confirm button</li>
   * <li>Delete button</li>
   * </p>
   */
  private void addButtons() {
    addReturnButton();
    addDeleteButton();
    addConfirmButton();

  }

  /**
   * Adds a delete button to the menu.
   * <p>
   * <li>The button will try to delete the currently selected {@link Game} in the
   * {@link LoadStoredGamesMenu#gameTable}.</li>
   * <li>If the game cannot be deleted, an {@link Alert}
   * will be shown to the user.</li>
   * </p>
   */
  private void addDeleteButton() {
    Button deleteButton = new Button("Delete");
    deleteButton.setPrefWidth(Double.MAX_VALUE);
    deleteButton.setId("delete-button");
    deleteButton.setOnAction(event -> {
      Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
      try {
        gameManager.deleteGame(selectedGame);
        fillGamesData();
        addGameTable();
      } catch (IOException | IllegalArgumentException | NullPointerException e) {
        new Alert(AlertType.ERROR,
            "Could not delete game because: " + e.getMessage()).showAndWait();
      }
    });

    deleteButton.disableProperty().bind(gameIsNotSelected());
    rightButtonPane.getChildren().add(deleteButton);
  }

  /**
   * Adds a confirm button to the menu.
   * <p>
   * <li>The button activation triggers the
   * {@link LoadStoredGamesListener#onSelectedGameClicked(Game)} method.</li>
   * </p>
   */
  private void addConfirmButton() {
    Button confirmButton = new Button("Confirm");
    confirmButton.disableProperty().bind(gameIsNotSelected());
    confirmButton.setMinWidth(100);
    HBox.setHgrow(rightButtonPane, Priority.NEVER);

    confirmButton.setOnAction(event -> {

      Game game = gameTable.getSelectionModel().getSelectedItem();

      if (!showBrokenLinks(game.getStory()) ) {
        return;
      }
      listener.onSelectedGameClicked(game);
    });

    rightButtonPane.getChildren().add(confirmButton);
  }

  private void addReturnButton() {
    Button returnButton = new Button("Return");
    returnButton.setId("return-button");
    returnButton.setOnAction(event -> listener.onReturnClicked());
    leftButtonPane.getChildren().add(returnButton);
  }

  private void setHeadline(String headline){
    Label headLabel = new Label(headline);
    headLabel.setFont(new Font(40));
    headLabel.setId("headline-background");
    setTop(headLabel);
    setAlignment(headLabel, Pos.CENTER);
    headLabel.setTextAlignment(TextAlignment.CENTER);
    headLabel.setPadding(new Insets(0, 30, 0, 30));
  }

  /**
   * Creates a game table providing data about all stored games.
   *
   * <p>Includes the following data values:
   * <ul>
   *     <li>Story title</li>
   *     <li>Player name</li>
   *     <li>Amount of broken links</li>
   * </ul>
   */
  private void addGameTable() {

    gameTable = new TableView<>();
    gameTable.getColumns().clear();


    TableColumn<Game, String> gameIdColumn = new TableColumn<>();
    gameIdColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getGameId()));
    gameIdColumn.setText("Game id");

    TableColumn<Game, String> storyColumn = new TableColumn<>();
    storyColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getStory().getTitle()));
    storyColumn.setText("Story");

    TableColumn<Game, String> playerColumn = new TableColumn<>();
    playerColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPlayer().getName()));
    playerColumn.setText("Player");

    TableColumn<Game, String> brokenLinksColumn = new TableColumn<>();
    brokenLinksColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
        String.valueOf(cellData.getValue().getStory().getBrokenLinks().size())));
    brokenLinksColumn.setText("Broken links");

    gameTable.getColumns().add(gameIdColumn);
    gameTable.getColumns().add(playerColumn);
    gameTable.getColumns().add(storyColumn);
    gameTable.getColumns().add(brokenLinksColumn);
    gameTable.setItems(FXCollections.observableList(gameList));
    gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    gameTable.setStyle("-fx-border-color: #000000");
    gameTable.setPrefWidth(getPrefWidth() * 0.5);
    gameTable.setMinWidth(getPrefWidth() * 0.3);
    gameTable.setPrefHeight(getPrefHeight() * 0.8);

    setCenter(gameTable);
  }

  /**
   * Adds all games to the list of games.
   */
  private void fillGamesData() {
    List<Game> storedGames = GameManager.getInstance().getGames();
    gameList = new ArrayList<>(storedGames);


  }


  /**
   * Boolean-binding for checking if a game has been selected.
   *
   * @return A boolean-binding containing information about if a game has been selected in the
   * gameTable or not. If a game has been selected, it will represent true, if not, the binding will
   * represent false.
   */
  private BooleanBinding gameIsNotSelected() {
    return gameTable.getSelectionModel().selectedItemProperty().isNull();
  }

  private boolean showBrokenLinks(Story story) {
    List<Link> brokenLinks = story.getBrokenLinks();
    if (brokenLinks.size() > 0) {
      String errorMessage = "The uploaded passage has: " + brokenLinks.size() + " broken links.";

      errorMessage = errorMessage.concat("\nThese are the broken links:\n");
      for (Link link : brokenLinks) {
        errorMessage = errorMessage.concat("\n - " + link.getText() + "->" + link.getReference());
      }
      errorMessage = errorMessage.concat("\n\nAre you sure you want to continue?");
      Alert alert = new Alert(AlertType.CONFIRMATION, errorMessage);
      alert.showAndWait();
      return alert.getResult().equals(ButtonType.OK);

    }
    return true;
  }


  private void setBackgroundImage(String imagePath) {
    try {
      Image image = new Image(imagePath);
      BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
      Background background = new Background(backgroundImage);
      setBackground(background);
    } catch (Exception e) {
      LOGGER.log(Level.INFO, "Could not add background because" + e.getMessage());
    }

  }

}
