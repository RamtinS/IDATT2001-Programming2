package edu.ntnu.idatt2001.paths.gui;

import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.controller.GameManager;
import edu.ntnu.idatt2001.paths.gui.listeners.LoadStoredGamesListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 * Represents a menu used for loading stored games.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 12, 2023.
 */
public class LoadStoredGamesMenu extends Pane {

  private static final Logger logger = Logger.getLogger(LoadStoredGamesMenu.class.getName());
  private final LoadStoredGamesListener listener;
  private GameManager gameManager;
  private TableView<Game> gameTable;
  private List<Game> gameList;

  /**
   * Constructor for a LoadStoredGamesMenu object. Adds a table with selectable games.
   *
   * @param width The width of the menu.
   * @param height The height of the menu.
   * @param listener The listener used to execute button actions.
   * @throws IllegalArgumentException If the listener is null.
   */
  public LoadStoredGamesMenu(int width, int height, LoadStoredGamesListener listener)
      throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    try {
      this.gameManager = GameManager.getInstance();
    } catch (IllegalStateException e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
      Alert alert = new Alert(AlertType.ERROR, e.getMessage());
      alert.showAndWait();
    }
    this.listener = listener;
    setPrefWidth(width);
    setPrefHeight(height);
    fillGamesData();
    addGameTable();
    addButtons();
    setStyle("-fx-background-color: #000000");
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
    addConfirmButton();
    addDeleteButton();
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
    getChildren().add(deleteButton);
    deleteButton.setLayoutX(200);
    deleteButton.setOnAction(event -> {
      Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
      try {
        gameManager.deleteGame(selectedGame);
      } catch (IOException | IllegalArgumentException | NullPointerException e) {
        logger.log(Level.WARNING, e.getMessage(), e);
        Alert alert = new Alert(AlertType.ERROR, e.getMessage());
        alert.showAndWait();
      }
    });
    deleteButton.disableProperty().bind(gameIsNotSelected());
  }

  /**
   * Adds a confirm button to the menu.
   * <p>
   * <li>The button activation triggers the
   * {@link LoadStoredGamesListener#onSelectedGameClicked(Game)} method.</li>
   * <li></li>
   * </p>
   */
  private void addConfirmButton() {
    Button confirmButton = new Button("Confirm");
    confirmButton.setOnAction(event -> {
      Game game = gameTable.getSelectionModel().getSelectedItem();
      if (!showBrokenLinks(game.getStory())) {
        return;
      }
      listener.onSelectedGameClicked(game);
    });
    confirmButton.disableProperty().bind(gameIsNotSelected());
    confirmButton.setLayoutX(100);
    getChildren().add(confirmButton);
  }

  private void addReturnButton() {
    Button returnButton = new Button("RETURN");
    returnButton.setOnAction(event -> listener.onReturnClicked());
    returnButton.setLayoutX(0);
    returnButton.setLayoutY(0);
    getChildren().add(returnButton);
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

    gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    gameTable.setStyle("-fx-border-color: #000000");
    gameTable.getColumns().add(gameIdColumn);
    gameTable.getColumns().add(playerColumn);
    gameTable.getColumns().add(storyColumn);
    gameTable.getColumns().add(brokenLinksColumn);
    gameTable.setItems(FXCollections.observableList(gameList));
    gameTable.setPrefWidth(400);
    gameTable.setLayoutX((getPrefWidth() - gameTable.getPrefWidth()) / 2);
    getChildren().add(gameTable);
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
      return !alert.getResult().equals(ButtonType.OK);

    }
    return true;
  }
}
