package edu.ntnu.idatt2001.paths.gui.menus;

import edu.ntnu.idatt2001.paths.controller.GameManager;
import edu.ntnu.idatt2001.paths.model.filehandling.FileGameHandler;
import edu.ntnu.idatt2001.paths.gui.listeners.StoredGamesListener;
import edu.ntnu.idatt2001.paths.gui.utility.GuiUtils;
import edu.ntnu.idatt2001.paths.model.Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * The StoredGamesMenu class represents a menu used for loading stored games.
 * It provides functionality for displaying a table of selectable games
 * and buttons for interacting with the games in the table.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 12, 2023.
 */
public class StoredGamesMenu extends BorderPane {
  private static final Logger logger = Logger.getLogger(StoredGamesMenu.class.getName());
  private final StoredGamesListener listener;
  private final GameManager gameManager;
  private TableView<Game> gameTable;
  private List<Game> gameList;
  private VBox rightButtonPane;

  /**
   * Constructor for a StoredGamesMenu object. Adds a table with selectable games.
   *
   * @param width The width of the menu.
   * @param height The height of the menu.
   * @param listener The listener used to execute button actions.
   * @throws IllegalArgumentException If the listener is null.
   * @throws NullPointerException if the listener is null.
   */
  public StoredGamesMenu(double width, double height, StoredGamesListener listener)
          throws NullPointerException, IllegalStateException {

    this.listener = Objects.requireNonNull(listener, "StoredGamesListener cannot be null.");
    gameManager = GameManager.getInstance();

    GuiUtils.setBackgroundImage(this, "images/forestadventure/beginnings.png");
    GuiUtils.setHeadline(this, "Saved games", 40, 0, 30, 0, 30);

    createRightButtonPane();
    setPrefWidth(width);
    setPrefHeight(height);
    setDimensions(width, height);
    fillGamesData();
    addGameTable();
    addButtons();
    setPadding(new Insets(20));
    showInvalidGames();
  }

  /**
   * The method displays an alert with information about any invalid games in the file.
   */
  private void showInvalidGames() {
    List<String> invalidGames = FileGameHandler.getInvalidGames();
    if (invalidGames.isEmpty()) {
      return;
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("You have ").append(invalidGames.size()).append(" invalid games:");
    for (String invalidGame : invalidGames) {
      stringBuilder.append("\n").append(invalidGame);
    }
    Alert alert = new Alert(AlertType.ERROR, stringBuilder.toString());
    alert.showAndWait();
  }

  /**
   * The method sets preferred width and height.
   *
   * @param width The desired width.
   * @param height The desired height.
   */
  private void setDimensions(double width, double height) {
    setPrefWidth(width);
    setPrefHeight(height);
  }

  /**
   * The method creates the right button pane for the StoredGamesMenu.
   */
  private void createRightButtonPane() {
    rightButtonPane = new VBox();
    rightButtonPane.setSpacing(20);
    rightButtonPane.setPadding(new Insets(0, 0, 0, 40));
    rightButtonPane.setAlignment(Pos.TOP_CENTER);
    rightButtonPane.setPrefWidth(getPrefWidth() * 0.2);
    setRight(rightButtonPane);
  }

  /**
   * The method adds all buttons to the pane.
   * <p>
   * This includes:
   * <li>Return button</li>
   * <li>Confirm button</li>
   * <li>Delete button</li>
   * </p>
   */
  private void addButtons() {
    GuiUtils.createReturnButton(this, "Return", listener::onReturnClicked, Pos.TOP_CENTER);
    addDeleteButton();
    addConfirmButton();
  }

  /**
   * Adds a delete button to the menu.
   * <p>
   * <li>The button will try to delete the currently selected {@link Game} in the
   * {@link StoredGamesMenu#gameTable}.</li>
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
        String errorMessage = "Could not delete game because: " + e.getMessage();
        logger.log(Level.WARNING, errorMessage, e);
        Alert alert = new Alert(AlertType.ERROR, errorMessage);
        alert.showAndWait();
      }
    });

    deleteButton.disableProperty().bind(gameIsNotSelected());
    rightButtonPane.getChildren().add(deleteButton);
  }

  /**
   * Adds a confirm button to the menu.
   * <p>
   * <li>The button activation triggers the
   * {@link StoredGamesListener#onSelectedGameClicked(Game)} method.</li>
   * </p>
   */
  private void addConfirmButton() {
    Button confirmButton = new Button("Confirm");
    confirmButton.disableProperty().bind(gameIsNotSelected());
    confirmButton.setMinWidth(100);
    HBox.setHgrow(rightButtonPane, Priority.NEVER);

    confirmButton.setOnAction(event -> {

      Game game = gameTable.getSelectionModel().getSelectedItem();

      listener.onSelectedGameClicked(game);
    });

    rightButtonPane.getChildren().add(confirmButton);
  }

  /**
   * Creates a game table providing data about all stored games.
   *
   * <p>Includes the following data values:
   * <ul>
   *     <li>Game ID</li>
   *     <li>Story title</li>
   *     <li>Player name</li>
   *     <li>Amount of broken links</li>
   * </ul>
   */
  private void addGameTable() {

    gameTable = new TableView<>();
    gameTable.getColumns().clear();

    addTableColumn(gameTable, "Game id", Game::getGameId);
    addTableColumn(gameTable, "Story", game -> game.getStory().getTitle());
    addTableColumn(gameTable, "Player", game -> game.getPlayer().getName());
    addTableColumn(gameTable, "Broken links", game -> String.valueOf(game.getStory().getBrokenLinks().size()));

    gameTable.setItems(FXCollections.observableList(gameList));
    gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    gameTable.setStyle("-fx-border-color: #000000");
    gameTable.setPrefWidth(getPrefWidth() * 0.5);
    gameTable.setMinWidth(getPrefWidth() * 0.3);
    gameTable.setPrefHeight(getPrefHeight() * 0.8);

    setCenter(gameTable);
  }

  /**
   * The method adds a new column to a TableView with the given text and value extractor.
   *
   * @param tableView the TableView to add the column to.
   * @param text the text to display in the column header
   * @param valueExtractor a function to extract the String value from an item of type T
   * @param <T> the type of items in the TableView
   */
  private <T> void addTableColumn(TableView<T> tableView, String text, Function<T, String> valueExtractor) {
    TableColumn<T, String> column = new TableColumn<>();
    column.setCellValueFactory(cellData -> new SimpleStringProperty(valueExtractor.apply(cellData.getValue())));
    column.setText(text);
    tableView.getColumns().add(column);
  }

  /**
   * Adds all games to the list of games.
   */
  private void fillGamesData() {
    List<Game> storedGames = GameManager.getInstance().getGames();
    gameList = new ArrayList<>(storedGames);
  }

  /**
   * The method creates a BooleanBinding that represents whether a game is selected in the gameTable.
   *
   * @return A BooleanBinding that evaluates to false if a game is selected, and true if no game is selected.
   */
  private BooleanBinding gameIsNotSelected() {
    return gameTable.getSelectionModel().selectedItemProperty().isNull();
  }
}
