package edu.ntnu.idatt2001.paths.gui.storycreation;

import edu.ntnu.idatt2001.paths.gui.listeners.StoryCreatorListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

/**
 * Represents a scrollable version of the StoryCreator class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 12, 2023.
 */
public class ScrollableStoryCreator extends HBox {

  private final StoryCreator storyCreator;

  /**
   * Constructor that creates a scrollPane that contains a StoryCreator.
   *
   * @param width                The width of the pane.
   * @param height               The height of the pane.
   * @param storyCreatorListener The story creator listener used to handle button actions.
   */
  public ScrollableStoryCreator(double width, double height,
                                StoryCreatorListener storyCreatorListener) {

    ScrollPane scrollPane = new ScrollPane();
    storyCreator = new StoryCreator(width, height, storyCreatorListener);
    scrollPane.setContent(storyCreator);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    getChildren().addAll(scrollPane);
  }

  /**
   * Gets the story creator object.
   *
   * @return The story creator object.
   */
  public StoryCreator getStoryCreator() {
    return storyCreator;
  }
}
