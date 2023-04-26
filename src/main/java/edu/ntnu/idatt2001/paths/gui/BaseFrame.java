package edu.ntnu.idatt2001.paths.gui;


import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class representing a frame for the game. Shows the passage text of the current passage and has
 * buttons for the links that the user can follow.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 26, 2023.
 */
public class BaseFrame extends AnchorPane {

  private final double width;
  private final double height;
  private final Player player;
  private final BaseFrameListener listener;
  private final Passage passage;

  /**
   * Constructor for a BaseFrame object.
   *
   * @param passage  Passage to present to the user.
   * @param player   The player used to represent player values.
   * @param width    The width of the frame.
   * @param height   The height of the frame.
   * @param listener The listener used to send out button activations from the frame.
   */
  public BaseFrame(Passage passage, Player player, double width, double height,
                   BaseFrameListener listener) {
    this.listener = listener;
    this.width = width;
    this.height = height;
    this.player = player;
    this.passage = passage;

    setPrefHeight(height);
    setPrefWidth(width);
    setHealthBar();
    addButtons();
    showGoldAndPoints();
    showPassageText();
    createInventory();
    applyBackground();
  }

  /**
   * Applies a background if any jpg in the resources/ImageFiles match the passage name.
   */
  private void applyBackground() {

    try {
      Image image = new Image("ImageFiles/" + passage.getTitle() + ".jpg");

      BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
          new BackgroundSize(1, 1, true, true, false, false));

      Background background = new Background(backgroundImage);

      setBackground(background);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Sets a health bar in the top left corner, showing how much health the user has left.
   */
  private void setHealthBar() {

    ProgressBar healthBar = new ProgressBar((double) player.getHealth() / 100);

    healthBar.prefWidthProperty().bind(widthProperty().divide(5));
    healthBar.prefHeightProperty().bind(heightProperty().divide(15));

    healthBar.setStyle("-fx-accent: red; -fx-background-color: gray;");

    getChildren().add(healthBar);
    setTopAnchor(healthBar, 0.0);
    setRightAnchor(healthBar, 0.0);


  }

  /**
   * Adds the restart, exit and link buttons to the frame.
   */
  private void addButtons() {
    addRestartButton();
    addExitButton();
    addLinkButtons();
  }

  /**
   * Adds an exit button to the frame, and adds the listener action to it.
   */
  private void addExitButton() {
    Button exitButton = new Button("Exit");
    exitButton.setStyle("-fx-wrap-text: false");
    exitButton.prefWidthProperty().bind(widthProperty().divide(15));
    exitButton.prefHeightProperty().bind(heightProperty().divide(30));
    exitButton.setOnAction(event -> listener.onExitClicked());
    exitButton.setLayoutX(0);
    exitButton.setLayoutY(0);
    setTopAnchor(exitButton, 0.0);
    setLeftAnchor(exitButton, 0.0);
    getChildren().add(exitButton);
  }

  /**
   * Adds a restart button to the frame and adds the listener action to it.
   */
  private void addRestartButton() {
    Button restartButton = new Button("Restart");
    ChangeListener<Number> sizeListener =
        (observable, oldValue, newValue) -> restartButton.setLayoutX(
        getWidth() / 15);
    restartButton.prefWidthProperty().bind(widthProperty().divide(15));
    restartButton.prefHeightProperty().bind(heightProperty().divide(30));

    widthProperty().addListener(sizeListener);

    restartButton.setOnAction(event -> listener.onRestartClicked());
    getChildren().add(restartButton);
  }

  /**
   * Adds the link buttons for the frame and adds the listener action to it.
   */
  private void addLinkButtons() {
    VBox choiceButtons = new VBox();
    try {
      for (Link link : passage.getLinks()) {
        Button button = new Button(link.getText());
        button.setOnAction(event -> listener.onOptionButtonClicked(link));
        button.setPrefWidth(width / 10);
        choiceButtons.getChildren().add(button);
      }

      choiceButtons.setLayoutX(width / 1.5);
      choiceButtons.setLayoutY(height - height / 3);
      getChildren().add(choiceButtons);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a text field of the amount of gold and points the user has.
   */
  private void showGoldAndPoints() {
    int gold = player.getGold();
    int points = player.getScore();
    Text healthText = new Text("Gold: " + gold);
    Text pointsText = new Text("Points:" + points);

    VBox attributes = new VBox();
    attributes.getChildren().addAll(healthText, pointsText);
    attributes.setLayoutX(0);
    attributes.setLayoutY(height / 3);
    attributes.setStyle("-fx-background-color: #C5B1FF");
    getChildren().add(attributes);

  }

  /**
   * Adds the passage text to the frame.
   */
  private void showPassageText() {

    Text text = new Text(passage.getContent());
    text.setWrappingWidth(width / 10);
    VBox textPane = new VBox();
    textPane.setStyle("-fx-background-color: #FFFFFF;");
    textPane.setLayoutX(width * 0.9 - text.getWrappingWidth());

    textPane.setLayoutY(height / 5);
    textPane.getChildren().add(text);
    textPane.setStyle("-fx-background-color: #C5B1FF");
    ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> {
      text.setWrappingWidth(getWidth() / 7);
      textPane.setLayoutX(getWidth() * 0.9 - text.getWrappingWidth());
      textPane.setLayoutY(getHeight() * 0.2);
    };
    widthProperty().addListener(sizeListener);
    heightProperty().addListener(sizeListener);
    getChildren().add(textPane);
  }

  /**
   * Adds a resizable inventory to the frame.
   */
  private void createInventory() {
    List<String> inventory = player.getInventory();
    List<Pane> inventorySlots = new ArrayList<>();
    HBox inventoryPane = new HBox();
    ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> {
      inventoryPane.setLayoutY(getHeight() * (391.0 / 450));
      inventoryPane.setLayoutX(getWidth() / 50);
      inventoryPane.setSpacing(getWidth() / 50);
    };
    widthProperty().addListener(sizeListener);
    heightProperty().addListener(sizeListener);

    makeInventoryResizable(inventory, inventorySlots, inventoryPane);

    getChildren().add(inventoryPane);
  }

  /**
   * Makes the input inventory resizable.
   *
   * @param inventory      Items in the inventory.
   * @param inventorySlots Inventory slots of the inventory.
   * @param inventoryPane  The pane that holds the inventory slots.
   */
  private void makeInventoryResizable(List<String> inventory, List<Pane> inventorySlots,
                                      HBox inventoryPane) {
    for (int i = 0; i < 10; i++) {
      Pane inventorySlot = new Pane();
      ChangeListener<Number> inventoryResizer = (observable, oldValue, newValue) -> {
        double slotSize = Math.min(getWidth() / 9 - inventoryPane.getSpacing(), getHeight() / 9);
        inventorySlot.setPrefHeight(slotSize);
        inventorySlot.setPrefWidth(slotSize);
      };
      widthProperty().addListener(inventoryResizer);
      heightProperty().addListener(inventoryResizer);

      inventoryPane.getChildren().add(inventorySlot);
      inventorySlots.add(inventorySlot);
      try {
        setInventoryItem(inventorySlot, inventory.get(i));
      } catch (Exception e) {
        inventorySlot.setStyle("-fx-background-color: A6FFC3");
      }
    }
  }

  /**
   * Sets an inventory item to a pane. If there are no pictures by the name of the item, a red cross
   * will be added instead.
   *
   * @param pane The inventory pane of the item.
   * @param item The item to be added to the pane.
   */
  private void setInventoryItem(Pane pane, String item) {
    try {

      Image image = new Image("ImageFiles/" + item + ".png");

      BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
          new BackgroundSize(0.9, 0.9, true, true, true, true));

      Background background = new Background(backgroundImage);
      pane.setBackground(background);
    } catch (Exception e) {
      try {
        Image image = new Image("ImageFiles/" + "Exclamation" + ".png");

        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(0.9, 0.9, true, true, true, true));

        Background background = new Background(backgroundImage);
        pane.setBackground(background);

      } catch (Exception f) {
        pane.setStyle("-fx-background-color: #A6FFC3");
      }
    }
  }


}

