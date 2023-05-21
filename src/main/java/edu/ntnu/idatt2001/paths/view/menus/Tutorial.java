package edu.ntnu.idatt2001.paths.view.menus;

import edu.ntnu.idatt2001.paths.view.listeners.TutorialListener;
import edu.ntnu.idatt2001.paths.view.utility.GuiUtils;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

/**
 * The Tutorial class displays the game tutorial.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 20, 2023.
 */
public class Tutorial extends BorderPane {

  /**
   * Constructs a Tutorial object with the specified width, height, and listener.
   *
   * @param width the preferred width of the tutorial.
   * @param height the preferred height of the tutorial.
   * @param listener the listener for tutorial events.
   * @throws NullPointerException if the listener is null.
   */
  public Tutorial(double width, double height, TutorialListener listener)
          throws NullPointerException {

    TutorialListener tutorialListener = Objects.requireNonNull(listener,
            "TutorialListener cannot be null.");

    GuiUtils.setBackgroundImage(this, "images/tutorial.png");
    GuiUtils.createReturnButton(this, "Return to main menu",
            tutorialListener::onReturnClicked, Pos.CENTER);

    setPrefWidth(width);
    setPrefHeight(height);
  }
}
