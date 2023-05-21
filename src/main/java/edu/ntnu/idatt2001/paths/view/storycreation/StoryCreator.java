package edu.ntnu.idatt2001.paths.view.storycreation;

import edu.ntnu.idatt2001.paths.model.filehandling.FileStoryHandler;
import edu.ntnu.idatt2001.paths.view.listeners.StoryCreatorListener;
import edu.ntnu.idatt2001.paths.model.Passage;
import edu.ntnu.idatt2001.paths.model.Story;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Pane used to create a story, has methods for creating passages and links, and retrieving their
 * data.
 */
public class StoryCreator extends Pane {

  private Position firstposition;
  private Position secondPosition;
  private final StoryCreatorListener listener;
  private RadioButton createPassageInputsButton;
  private RadioButton createMovePassageButton;
  private RadioButton createDragLineButton;
  private Button mainMenuButton;
  private Button resetLinesButton;
  private Button undoButton;
  private Button saveStoryButton;
  private TextField storyNameField;
  private static final Logger LOGGER = Logger.getLogger(StoryCreator.class.getName());

  /**
   * Constructor for a StoryCreator object. Adds buttons, and adds the keyboard shortcuts.
   *
   * @param width The width of the frame.
   * @param height The height of the frame.
   * @param listener The listener used to handle button activities.
   * @throws NullPointerException if the listener is null.
   */
  public StoryCreator(double width, double height, StoryCreatorListener listener)
          throws NullPointerException {
    this.listener = Objects.requireNonNull(listener, "StoryCreatorListener cannot be null.");
    setFrameDimensions(width, height);
    addInputElements();
    addKeyboardShortcuts();
    showPrototypeAlert();

  }

  private void showPrototypeAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);

    alert.setTitle("Under Construction");

    String information =
        "This part of the application is still under construction. Because of this, some things "
            + "will not work optimally, but it should still be usefull to create simple stories. "
            + "This can be done by pressing the \"create passage\" and clicking at 2 points on "
            + "the canvas. Then you can create links by dragging the mouse between them. If you "
            + "prefer making stories manually, this can be done by uploading them through "
            + "the\"create game\" menu.";

    Text text = new Text(information);
    text.setWrappingWidth(getPrefWidth() / 2);
    alert.getDialogPane().setContent(text);
    alert.showAndWait();
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

    setOnKeyReleased(keyEvent -> {
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

      Position firstDrawnPosition = setInitialPosition(event, line);

      setOnMouseDragged(dragEvent -> dragLine(line, dragEvent));

      setOnMouseReleased(
          releaseEvent -> setFinalLinePoint(event, line, firstDrawnPosition, releaseEvent)

      );
    });
  }

  /**
   * Sets the final points of a {@link LinkLine} to the closest passages.
   *
   * @param event              The event used to calculate the initial position for when the line is
   *                           released.
   * @param line               The line to change the points of.
   * @param firstDrawnPosition The initial position of the line.
   * @param releaseEvent       The mouse event used to calculate the new release position.
   */
  private void setFinalLinePoint(MouseEvent event, LinkLine line, Position firstDrawnPosition,
                                 MouseEvent releaseEvent) {
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

        getChildren().add(line.getTextHolder());
        line.setTextAreaInMiddleOfLine();

        getChildren().remove(firstDrawnPosition.getIcon());
        line.moveArrowHead();
      } else {
        throw new IllegalArgumentException("Line is not close to enough to 2 passages.");
      }
    } catch (Exception e) {
      LOGGER.log(Level.INFO, e.toString());
      showPassageAlert(e);
      getChildren().remove(line);
      getChildren().remove(line.getArrowHead());
    }
  }

  /**
   * Sets the initial position for a link line to the location of a given {@link MouseEvent}.
   *
   * @param event The event used to calculate the initial position.
   * @param line  The line used to set the initial position.
   * @return The initial position that has been set to the line.
   */
  private Position setInitialPosition(MouseEvent event, LinkLine line) {
    Position firstDrawnPosition = new Position(event.getX(), event.getY());
    line.setStartPosition(firstDrawnPosition);
    line.setEndPosition(firstDrawnPosition);
    getChildren().add(line);
    getChildren().add(line.getArrowHead());
    return firstDrawnPosition;
  }

  /**
   * Moves the line to the location of a given {@link LinkLine}.
   *
   * @param line      The line that will be moved.
   * @param dragEvent The new location of the end position of the drag event.
   */
  private void dragLine(LinkLine line, MouseEvent dragEvent) {
    Position draggedPosition = new Position(dragEvent.getX(), dragEvent.getY());
    line.setEndPosition(draggedPosition);
    line.moveArrowHead();
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
   * Adds all input elements to the frame.
   * <p>
   * This includes:
   * <li>Return button</li>
   * </p>
   */
  public void addInputElements() {
    enableReturnToMenuButton();
    enableCreatePassageButton();
    enableMovingPassageButton();
    enableDragLineButton();
    enableResetLinesButton();
    enableUndoButton();
    enableToggleGroup();
    enableRetrieveButton();
    enableTitleField();

    VBox buttonBox = new VBox();
    buttonBox.setId("unbreakable");
    buttonBox.setPadding(new Insets(10));
    buttonBox.setSpacing(30);

    buttonBox.getChildren()
        .addAll(mainMenuButton, undoButton, resetLinesButton, createPassageInputsButton,
            createDragLineButton, createMovePassageButton, saveStoryButton);
    getChildren().add(buttonBox);
  }


  /**
   * Declares the {@link #storyNameField} and adds a "story title" prompt to it.
   */
  private void enableTitleField() {

    storyNameField = new TextField();
    storyNameField.setPromptText("Story title");
    storyNameField.setPrefWidth(getPrefWidth() / 4);
    storyNameField.setLayoutX((getPrefWidth() - storyNameField.getPrefWidth()) / 2);

    getChildren().add(storyNameField);
  }

  /**
   * Adds {@link #createDragLineButton}, {@link #createPassageInputsButton} &
   * {@link #createMovePassageButton} to a common toggle group.
   */
  private void enableToggleGroup() {
    ToggleGroup toggleGroup = new ToggleGroup();
    createDragLineButton.setToggleGroup(toggleGroup);
    createPassageInputsButton.setToggleGroup(toggleGroup);
    createMovePassageButton.setToggleGroup(toggleGroup);
  }

  /**
   * Creates a button that activates the {@link #retrieveStoryData()} method.
   */
  private void enableRetrieveButton() {
    saveStoryButton = new Button("Retrieve data");
    saveStoryButton.setId("confirm-button");
    saveStoryButton.setOnAction(event -> retrieveStoryData());
  }

  /**
   * Creates a button that activates the {@link #deleteMostRecentNode()} method.
   */
  private void enableUndoButton() {
    undoButton = new Button("Undo");
    undoButton.setOnAction(event -> deleteMostRecentNode());
  }

  /**
   * Creates a button that activates the {@link #moveAllLinkLines()} method.
   */
  private void enableResetLinesButton() {
    resetLinesButton = new Button("Reset lines");
    resetLinesButton.setOnAction(event -> moveAllLinkLines());
  }

  /**
   * Creates a button that activates the {@link #enableLineCreationDrag()} method.
   */
  private void enableDragLineButton() {
    createDragLineButton = new RadioButton("Create link");
    createDragLineButton.setOnAction(event -> {
      if (createDragLineButton.isSelected()) {
        disableMouseEvent();
        enableLineCreationDrag();
      }
    });
  }

  /**
   * Creates a button that activates the {@link #enableMovingPassages()} method.
   */
  private void enableMovingPassageButton() {
    createMovePassageButton = new RadioButton("Move passage");
    createMovePassageButton.setOnAction(event -> {
      disableMouseEvent();
      enableMovingPassages();
    });
  }

  /**
   * Creates a button that activates the {@link StoryCreatorListener#onReturnClicked()} method.
   */
  private void enableReturnToMenuButton() {
    mainMenuButton = new Button("Return to main menu");
    mainMenuButton.setId("return-button");
    mainMenuButton.setOnAction(event -> listener.onReturnClicked());
  }

  /**
   * Creates a button that activates the {@link #enableCreatingPassageInput()} method.
   */
  private void enableCreatePassageButton() {
    createPassageInputsButton = new RadioButton("Create passage");
    createPassageInputsButton.setOnAction(event -> {
      if (createPassageInputsButton.isSelected()) {
        disableMouseEvent();
        enableCreatingPassageInput();
      }
    });
  }

  /**
   * Finds the most recently added {@link Node}, and removes it.
   */
  private void deleteMostRecentNode() {
    Node lastChild = getChildren().get(getChildren().size() - 1);
    if (lastChild.getId() != null && lastChild.getId().equals("unbreakable")) {
      Alert alert = new Alert(AlertType.WARNING, "There are no more elements to remove");
      alert.showAndWait();
    } else {
      getChildren().remove(lastChild);
    }
  }

  /**
   * Creates a {@link PassageInput} between two points selected by the user.
   */
  private void enableCreatingPassageInput() {
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
    return (PassageInput) getChildren().stream().filter(PassageInput.class::isInstance).min(
        Comparator.comparingDouble(currentNode -> Position.getCenterOfNode(currentNode)
            .getDistance(Position.getCenterOfNode(node)))).orElse(null);

  }

  /**
   * Creates a story from the currently added data in the frame.
   */
  private void retrieveStoryData() {

    try {
      String name = storyNameField.getText();
      Passage openingPassage = findFirstPassage().getPassage();

      List<Passage> passages = new ArrayList<>(
          retrievePassages().stream().map(PassageInput::getPassage).toList());
      List<LinkLine> linkLines = retrieveLinks();

      matchLinksToPassages(passages, linkLines);

      openingPassage = replaceOpeningPassage(openingPassage, passages);
      Story story = new Story(name, openingPassage);
      passages.forEach(story::addPassage);

      saveStory(story, "src/main/resources/stories/");

      Alert alert = new Alert(AlertType.CONFIRMATION, "File has been saved");
      alert.showAndWait();
    } catch (Exception e) {
      LOGGER.log(Level.INFO, e.getMessage(), e);
      Text alertText = new Text("Could not save story to file because: \n" + e.getMessage());
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().setContent(alertText);
      alert.showAndWait();
    }

  }

  /**
   * Saves a given story at a given location.
   *
   * @param story    Story to be saved.
   * @param location The location where the story will be saved.
   * @throws IOException              If the file cannot be saved.
   * @throws IllegalArgumentException If a file with the same name already exists.
   */
  private void saveStory(Story story, String location)
      throws IOException, IllegalArgumentException {
    String path = location + story.getTitle() + ".paths";
    if (!Files.exists(Path.of(path))) {
      FileStoryHandler.writeStoryToFile(story, path);
    } else {
      throw new IllegalArgumentException(
          "File " + path + " already exists, Please choose a different name");
    }
  }

  /**
   * Finds the passage in the list of passages that should be the opening passage. It then returns
   * the opening passage, and removes it from the list of passages.
   *
   * @param openingPassage The opening passage to look for.
   * @param passages       The list of passages containing an opening passage that should be
   *                       transferred as the new opening passage.
   * @return The new opening passage.
   */
  private Passage replaceOpeningPassage(Passage openingPassage, List<Passage> passages) {
    Iterator<Passage> iterator = passages.iterator();
    while (iterator.hasNext()) {
      Passage passage = iterator.next();
      if (passage.getTitle().equals(openingPassage.getTitle())) {
        openingPassage = passage;
        iterator.remove();
        break;
      }
    }
    return openingPassage;
  }

  /**
   * Matches a list of {@link LinkLine linklines} up to a list of {@link Passage passages}.
   * <p>
   * If a linkLine has the same first passage input as a passage, the link of the LinkLine will be
   * added to the passage.
   * </p>
   *
   * @param passages  The passages to that the links will be added to.
   * @param linkLines The LinkLines used to retrieve matching links.
   */
  private void matchLinksToPassages(List<Passage> passages, List<LinkLine> linkLines) {
    for (Passage passage : passages) {
      for (LinkLine linkLine : linkLines) {
        if (passage.getTitle().equals(linkLine.getFirstPassageInput().getPassage().getTitle())) {
          passage.addLink(linkLine.getLink());
        }
      }

    }
  }

  /**
   * Retrieves all the passage data from all {@link PassageInput passage inputs} in the frame.
   *
   * @return A list of all user created passages.
   */
  private List<PassageInput> retrievePassages() {
    List<PassageInput> retrievedPassages = new ArrayList<>();
    getChildren().stream().filter(node -> node.getClass().equals(PassageInput.class))
        .map(PassageInput.class::cast).forEach(retrievedPassages::add);
    return retrievedPassages;
  }

  /**
   * Retrieves all the link data from all {@link LinkLine link lines} in the frame.
   *
   * @return A list of all user created links.
   */
  private List<LinkLine> retrieveLinks() {
    List<LinkLine> retrievedLinks = new ArrayList<>();
    getChildren().stream().filter(node -> node.getClass().equals(LinkLine.class))
        .map(LinkLine.class::cast).forEach(retrievedLinks::add);
    return retrievedLinks;
  }

  /**
   * Moves all {@link LinkLine link lines} in the frame to match up with their connected
   * {@link PassageInput passage inputs}.
   */
  private void moveAllLinkLines() {
    getChildren().stream().filter(LinkLine.class::isInstance).forEach(node -> {
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
  private PassageInput findFirstPassage() throws NullPointerException {
    return ((PassageInput) Objects.requireNonNull(
        getChildren().stream().filter(PassageInput.class::isInstance).findFirst().orElse(null)));
  }
}
