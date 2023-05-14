package edu.ntnu.idatt2001.paths.gui;

import edu.ntnu.idatt2001.paths.Game;
import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Player;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.filehandling.FileStoryHandler;
import edu.ntnu.idatt2001.paths.goals.Goal;
import edu.ntnu.idatt2001.paths.gui.listeners.LoadStoredGamesListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 * Represents a menu used for loading stored games.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 12, 2023.
 */
public class LoadStoredGamesMenu extends Pane {

  private final LoadStoredGamesListener listener;
  private TableView<Game> gameTable;
  private List<Game> gameList;
  TableColumn<Game, String> gameColumn;
  TableColumn<Game, String> playerColumn;
  TableColumn<Game, String> brokenLinksColumn;

  /**
   * Constructor for a LoadStoredGamesMenu object. Adds a table with selectable games.
   *
   * @param width    The width of the menu.
   * @param height   The height of the menu.
   * @param listener The listener used to execute button actions.
   */
  public LoadStoredGamesMenu(int width, int height, LoadStoredGamesListener listener) {
    setPrefWidth(width);
    setPrefHeight(height);
    this.listener = listener;
    fillGamesData();
    addGameTable();
    addButtons();
    setStyle("-fx-background-color: #000000");
  }

  /**
   * Adds all buttons to the pane.
   */
  private void addButtons() {
    Button returnButton = new Button("RETURN");
    returnButton.setOnAction(event -> listener.onReturnClicked());
    getChildren().add(returnButton);
    returnButton.setLayoutX(0);
    returnButton.setLayoutY(0);

    Button confirmButton = new Button("Confirm");
    getChildren().add(confirmButton);
    confirmButton.setLayoutX(100);
    confirmButton.setOnAction(event -> {

      Game game = gameTable.getSelectionModel().getSelectedItem();

      if (game.getStory().getBrokenLinks().size() > 0) {
        showBrokenLinks(game.getStory());
      }

      listener.onSelectedGameClicked(game);
    });
    confirmButton.disableProperty().bind(gameIsSelected());
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

    gameColumn = new TableColumn<>();
    gameColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getStory().getTitle()));
    gameColumn.setText("Game");

    playerColumn = new TableColumn<>();
    playerColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPlayer().getName()));
    playerColumn.setText("Player");

    brokenLinksColumn = new TableColumn<>();
    brokenLinksColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
        String.valueOf(cellData.getValue().getStory().getBrokenLinks().size())));
    brokenLinksColumn.setText("Broken links");

    gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    gameTable.setStyle("-fx-border-color: #000000");
    gameTable.getColumns().add(playerColumn);
    gameTable.getColumns().add(gameColumn);
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
    addTestData();
  }


  private void addTestData() {
    try {

      Player player = new Player.PlayerBuilder("Test name").health(100).score(100).gold(50).build();
      Story story = FileStoryHandler.readStoryFromFile(
          "src/main/resources/stories/" + new File("src/main/resources/stories").list()[0]);
      List<Goal> goals = new ArrayList<>();
      Game game = new Game("Game id2", player, story, goals);
      gameList = new ArrayList<>();
      gameList.add(game);
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR, "Error while loading the selected game" + e);
      alert.showAndWait();
    }

  }

  /**
   * Boolean-binding for checking if a game has been selected.
   *
   * @return A boolean-binding containing information about if a game has been selected in the
   * gameTable or not. If a game has been selected, it will represent true, if not, the binding will
   * represent false.
   */
  private BooleanBinding gameIsSelected() {
    return gameTable.getSelectionModel().selectedItemProperty().isNull();
  }

  private void showBrokenLinks(Story story) {
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

    }
  }

}
