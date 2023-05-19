package edu.ntnu.idatt2001.paths.gui.uielements;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Class that represents the inventory of a player.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 12, 2023.
 */
public class InventoryPane extends HBox {

  private final List<String> inventoryItems;

  /**
   * Creates an inventory with images corresponding to the input inventory items.
   *
   * @param width          The width of the pane.
   * @param height         The height of the pane.
   * @param inventoryItems Items to be added to the pane.
   */
  public InventoryPane(int width, int height, List<String> inventoryItems) {
    this.inventoryItems = inventoryItems;
    setSpacing(20);
    setWidth(width);
    setHeight(height);
    createPanes();
  }

  /**
   * Creates 1 pane for each inventory item, and tries to add the corresponding picture to it. If
   * the picture cannot be set, it will be set to a picture holding an exclamation icon. If this
   * does not work either, a default black background color will be set
   */
  public void createPanes() {
    for (String inventoryItem : inventoryItems) {
      Pane pane = new Pane();
      pane.setPrefHeight(getWidth());
      pane.setPrefWidth(getWidth());
      try {
        setPaneBackground(pane, inventoryItem);
      } catch (Exception e) {
        try {
          setPaneBackground(pane, "exclamation");
        } catch (Exception f) {
          pane.setStyle("-fx-background-color: #000000");
        }
      }
      getChildren().add(pane);
    }
  }

  /**
   * Sets the background image of a pane to a picture found in the resource folder. If the name of
   * the resource is not found, the method will throw
   *
   * @param pane The pane to change the background of.
   * @param name The file name used to find matching files in the resource folder.
   * @throws NullPointerException If there is no items containing the specified file name in the
   *                              resource folder.
   */
  private void setPaneBackground(Pane pane, String name) throws NullPointerException {

    String[] images = new File("src/main/resources/items").list();
    Objects.requireNonNull(images, "Image folder is empty");

    String location =
        "/items/" + Arrays.stream(images).filter(image -> image.contains(name)).findFirst()
            .orElseThrow(NullPointerException::new);

    pane.setStyle(
        "-fx-background-image: url('" + location + "');" + "-fx-background-size: stretch");

  }
}
