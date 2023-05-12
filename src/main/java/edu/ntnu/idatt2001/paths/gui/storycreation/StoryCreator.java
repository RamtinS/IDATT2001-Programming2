package edu.ntnu.idatt2001.paths.gui.storycreation;

import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.gui.listeners.StoryCreatorListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Pane used to create a story, has methods for creating passages and links, and retrieving their
 * data.
 */
public class StoryCreator extends Pane {

  private Position firstposition;
  private Position secondPosition;
  private final StoryCreatorListener listener;

  /**
   * Constructor for a StoryCreator object. Adds buttons, and adds the keyboard shortcuts.
   *
   * @param width    The width of the frame.
   * @param height   The height of the frame.
   * @param listener The listener used to handle button activities.
   */
  public StoryCreator(double width, double height, StoryCreatorListener listener) {
    this.listener = listener;
    setFrameDimensions(width, height);
    addButtons();
    addKeyboardShortcuts();
  }

  /**
   * Sets all heights and widths of the frame to the given width and height.
   *
   * @param width  The width of the frame.
   * @param height The height of the frame.
   */
  private void setFrameDimensions(double width, double height) {
    setWidth(width);
    setHeight(height);
    setPrefWidth(width);
    setPrefHeight(height);
    setMinHeight(height);
    setMinWidth(width);
  }

  /**
   * Creates keyboard shortcuts for executing certain actions.
   * <ul>
   *     <li>Shift + z : deletes most recently created node, using:
   *     {@link StoryCreator#deleteMostRecentNode()}.</li>
   * </ul>
   */
  private void addKeyboardShortcuts() {
    KeyCodeCombination shiftZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHIFT_DOWN);

    setOnKeyPressed(keyEvent -> {
      if (shiftZ.match(keyEvent)) {
        deleteMostRecentNode();
      }
    });
  }

  /**
   * Enables moving passages around the frame using mouse input.
   */
  public void enableMovingPassages() {
    for (Node node : getChildren()) {
      if (node instanceof PassageInput passageInput) {

        passageInput.setOnMouseDragged(event -> {
          passageInput.setLayoutX(event.getSceneX());
          passageInput.setLayoutY(event.getSceneY());
          moveAllLinkLines();
        });
      }
    }
  }

  /**
   * Disables all currently set mouse events.
   */
  public void disableMouseEvent() {
    setOnMouseClicked(null);
    setOnMouseDragged(null);
    setOnMouseReleased(null);
    setOnMousePressed(null);
  }

  /**
   * Enables creating a line while dragging the mouse over the frame.
   */
  public void enableLineCreationDrag() {

    setOnMousePressed(event -> {
      LinkLine line = new LinkLine();

      Position firstDrawnPosition = new Position(event.getX(), event.getY());
      line.setStartPosition(firstDrawnPosition);
      line.setEndPosition(firstDrawnPosition);
      getChildren().add(line);
      getChildren().add(line.getArrowHead());

      setOnMouseDragged(dragEvent -> {
        Position draggedPosition = new Position(dragEvent.getX(), dragEvent.getY());
        line.setEndPosition(draggedPosition);
        line.moveArrowHead();
      });

      setOnMouseReleased(releaseEvent -> {

        try {
          Position releasePositionStart = new Position(event.getX(), event.getY());
          Position releasePositionEnd = new Position(releaseEvent.getX(), releaseEvent.getY());

          PassageInput startClosestPassage = getClosestPassage(releasePositionStart.getIcon());
          PassageInput endClosestPassage = getClosestPassage(releasePositionEnd.getIcon());

          double distanceToStartPassage = releasePositionStart.getDistance(
              Position.getCenterOfNode(startClosestPassage));
          double distanceToEndPassage = releasePositionEnd.getDistance(
              Position.getCenterOfNode(endClosestPassage));

          if (distanceToStartPassage < 300 && distanceToEndPassage < 300) {
            Position finalPositionStart = Position.getClosestSideMiddlePosition(startClosestPassage,
                releasePositionStart);
            Position finalPositionEnd = Position.getClosestSideMiddlePosition(endClosestPassage,
                releasePositionEnd);
            line.setStartPosition(finalPositionStart);
            line.setEndPosition(finalPositionEnd);

            line.setFirstPassageInput(startClosestPassage);
            line.setSecondPassageInput(endClosestPassage);

            line.getFirstPassageInput().addLinkLine(line);

            getChildren().add(line.getTextArea());
            line.setTextAreaInMiddleOfLine();

            getChildren().remove(firstDrawnPosition.getIcon());
            line.moveArrowHead();
          } else {
            throw new IllegalArgumentException("LIne is not close to enough to 2 passages.");
          }
        } catch (Exception e) {
          showPassageAlert(e);
          getChildren().remove(line);
          getChildren().remove(line.getArrowHead());
        }

      });
    });
  }

  /**
   * Shows an alert, telling the user why a line cannot be created at that moment.
   *
   * @param e The exception user to find out the reason why the line cannot be created.
   */
  private void showPassageAlert(Exception e) {
    Alert alert;
    if (e instanceof NullPointerException) {
      alert = new Alert(AlertType.WARNING, "Please create passages before creating lines");
    } else {
      alert = new Alert(AlertType.WARNING, "Cannot create line because:" + e.getMessage());
    }
    alert.showAndWait();
  }

  /**
   * Adds all buttons to the frame.
   */
  public void addButtons() {
    Button returnToMainMenu = new Button("Return to main menu");
    returnToMainMenu.setOnAction(event -> listener.onReturnClicked());

    RadioButton createPassageSquares = new RadioButton("Enable creating passages");
    createPassageSquares.setOnAction(event -> {
      if (createPassageSquares.isSelected()) {
        disableMouseEvent();
        enableCreatingPassageTextArea();
      }
    });

    RadioButton movePassage = new RadioButton("Move passage");
    movePassage.setOnAction(event -> {
      disableMouseEvent();
      enableMovingPassages();
    });

    RadioButton createDragLine = new RadioButton("Enable creating dragging lines");
    createDragLine.setOnAction(event -> {
      if (createDragLine.isSelected()) {
        disableMouseEvent();
        enableLineCreationDrag();
      }
    });

    Button resetLines = new Button("Reset lines");
    resetLines.setOnAction(event -> moveAllLinkLines());

    Button undoButton = new Button("Undo");
    undoButton.setOnAction(event -> deleteMostRecentNode());

    ToggleGroup toggleGroup = new ToggleGroup();
    createDragLine.setToggleGroup(toggleGroup);
    createPassageSquares.setToggleGroup(toggleGroup);
    movePassage.setToggleGroup(toggleGroup);

    Button retrieveData = new Button("Retrieve data");
    retrieveData.setOnAction(event -> retrieveStoryData());

    VBox buttonBox = new VBox();

    buttonBox.getChildren()
        .addAll(returnToMainMenu, undoButton, retrieveData, createPassageSquares, createDragLine,
            movePassage, resetLines);
    getChildren().add(buttonBox);
  }

  /**
   * Finds the most recently added {@link Node}, and removes it.
   */
  private void deleteMostRecentNode() {
    Node lastChild = getChildren().get(getChildren().size() - 1);

    if ((lastChild instanceof VBox && !(lastChild instanceof PassageInput))
        || lastChild instanceof Button || lastChild instanceof RadioButton) {
      Alert alert = new Alert(AlertType.WARNING, "There are no more elements to remove");
      alert.showAndWait();
    } else {
      getChildren().remove(lastChild);
    }
  }

  /**
   * Creates a {@link PassageInput} between two points selected by the user.
   */
  private void enableCreatingPassageTextArea() {
    setOnMouseClicked(event -> {
      if (firstposition == null) {
        firstposition = new Position(event.getX(), event.getY());
        getChildren().add(firstposition.getIcon());
        return;
      } else {
        secondPosition = new Position(event.getX(), event.getY());
        getChildren().add(secondPosition.getIcon());
      }
      double rectangleWidth = Math.abs(firstposition.getX() - secondPosition.getX());
      double rectangleHeight = Math.abs(firstposition.getY() - secondPosition.getY());
      double rectangleTopLeftX = Math.min(firstposition.getX(), secondPosition.getX());
      double rectangleTopLeftY = Math.min(firstposition.getY(), secondPosition.getY());

      PassageInput passageInput = new PassageInput();
      passageInput.setLayoutX(rectangleTopLeftX);
      passageInput.setLayoutY(rectangleTopLeftY);
      passageInput.setPrefWidth(rectangleWidth);
      passageInput.setPrefHeight(rectangleHeight);
      passageInput.setMaxHeight(rectangleHeight);
      getChildren().remove(firstposition.getIcon());
      getChildren().remove(secondPosition.getIcon());
      firstposition = null;
      secondPosition = null;
      getChildren().add(passageInput);

    });


  }

  /**
   * Finds the closest {@link PassageInput} to a given node.
   *
   * @param node The node used to find the closest passage input.
   * @return The closest passageInp
   * @throws NullPointerException If there are no existing passages.
   */
  private PassageInput getClosestPassage(Node node) throws NullPointerException {
    return (PassageInput) getChildren().stream()
        .filter(currentNode -> (currentNode instanceof PassageInput)).min(
            Comparator.comparingDouble(currentNode -> Position.getCenterOfNode(currentNode)
                .getDistance(Position.getCenterOfNode(node)))).orElse(null);

  }

  /**
   * Creates a story from the currently added data in the frame.
   *
   * @return A story made form the currently added data in the frame.
   */
  private Story retrieveStoryData() {
    List<Passage> retrievedPassages = retrievePassages();
    Story story = new Story("Retrieved story", findFirstPassage());
    retrievedPassages.forEach(story::addPassage);
    return story;
  }

  /**
   * Retrieves all the passage data from all {@link PassageInput passage inputs} in the frame.
   *
   * @return A list of all user created passages.
   */
  private List<Passage> retrievePassages() {
    List<Passage> retrievedPassages = new ArrayList<>();
    getChildren().stream().filter(node -> node.getClass().equals(PassageInput.class))
        .map(node -> (PassageInput) node)
        .forEach(passageInput -> retrievedPassages.add(passageInput.getPassage()));
    return retrievedPassages;
  }

  /**
   * Retrieves all the link data from all {@link LinkLine link lines} in the frame.
   *
   * @return A list of all user created links.
   */
  private List<Link> retrieveLinks() {
    List<Link> retrievedLinks = new ArrayList<>();
    getChildren().stream().filter(node -> node.getClass().equals(LinkLine.class))
        .map(node -> (LinkLine) node).forEach(linkLine -> retrievedLinks.add(linkLine.getLink()));
    return retrievedLinks;
  }

  /**
   * Moves all {@link LinkLine link lines} in the frame to match up with their connected
   * {@link PassageInput passage inputs}.
   */
  private void moveAllLinkLines() {
    getChildren().stream().filter(node -> node instanceof LinkLine).forEach(node -> {
      LinkLine linkline = (LinkLine) node;
      linkline.setStartPosition(
          Position.getClosestSideMiddlePosition(linkline.getFirstPassageInput(),
              linkline.getStartPosition()));
      linkline.setEndPosition(
          Position.getClosestSideMiddlePosition(linkline.getSecondPassageInput(),
              linkline.getEndPosition()));
      linkline.setTextAreaInMiddleOfLine();
    });

  }

  /**
   * Finds the first passage that has been added to the frame.
   *
   * @return the first passage that has been added to the frame.
   * @throws NullPointerException If the frame has no added passage.
   */
  private Passage findFirstPassage() throws NullPointerException {
    return ((PassageInput) Objects.requireNonNull(
        getChildren().stream().filter(node -> node instanceof PassageInput).findFirst()
            .orElse(null))).getPassage();
  }

}
