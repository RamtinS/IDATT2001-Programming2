package edu.ntnu.idatt2001.paths.gui.utility;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Utility class for changing dimensions of JavaFX objects.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal.
 * @version 1.0
 * @since May 6, 2023.
 */
public class DimensionUtility {

  /**
   * Changes the minimum, preferred and max width of a given {@link Button}.
   *
   * @param button   The button to resize.
   * @param newWidth The new width for the button.
   */
  public static void changeAllButtonWidths(Button button, double newWidth) {
    button.setMinWidth(newWidth);
    button.setPrefWidth(newWidth);
    button.setMaxWidth(newWidth);
  }


  /**
   * Changes the minimum, preferred and max height of a given {@link Button}.
   *
   * @param button    The button to resize.
   * @param newHeight The new height for the button.
   */
  public static void changeAllButtonHeights(Button button, double newHeight) {
    button.setMinHeight(newHeight);
    button.setPrefHeight(newHeight);
    button.setMaxHeight(newHeight);
  }

  /**
   * Changes the minimum, preferred and max width of a given {@link Pane}.
   *
   * @param pane     the pane to resize.
   * @param newWidth The new width for the pane.
   */
  public static void changeAllPaneWidths(Pane pane, double newWidth) {
    pane.setPrefWidth(newWidth);
    pane.setMinWidth(newWidth);
    pane.setMaxWidth(newWidth);
  }

  /**
   * Changes the minimum, preferred and max height of a given {@link Pane}.
   *
   * @param pane      the pane to resize.
   * @param newHeight The new height for the pane.
   */
  public static void changeAllPaneHeights(Pane pane, double newHeight) {
    pane.setPrefHeight(newHeight);
    pane.setMinHeight(newHeight);
    pane.setMaxHeight(newHeight);
  }


}
