package edu.ntnu.idatt2001.paths.gui.storycreation;

import edu.ntnu.idatt2001.paths.model.Link;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * Represents a line that connects two passage inputs together. Has a text area used to create a
 * link referring to the second connected passage. Has an arrowhead on the end of the line to
 * represent direction.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since May 12, 2023.
 */
public class LinkLine extends Line {

  private Button actionsButton;
  private final ActionCreator actionCreator;
  private final VBox textHolder;
  private final TextArea linkText;
  private PassageInput firstPassageInput;
  private PassageInput secondPassageInput;
  private final Polygon arrowHead;
  private Stage actionStage;

  /**
   * Constructor for a linkLine object.
   */
  public LinkLine() {
    this.textHolder = new VBox();
    actionCreator = new ActionCreator();
    createActionStage();
    createActionsButton();
    textHolder.setPrefWidth(100);


    this.linkText = new TextArea();
    textHolder.getChildren().addAll(linkText, actionsButton);

    textHolder.setPrefHeight(10);
    arrowHead = new Polygon();
  }

  /**
   * Creates new action stage from an {@link ActionCreator}.
   */
  private void createActionStage() {
    actionStage = new Stage();
    actionStage.setScene(new Scene(actionCreator));
  }

  /**
   * Creates a button for showing the action stage.
   */
  private void createActionsButton() {
    actionsButton = new Button("Add actions");
    actionsButton.setOnAction(actionEvent -> actionStage.show());
    actionsButton.setId("action-button");
  }

  /**
   * Sets the first passage input connected to the LinkLine.
   *
   * @param passageInput The first passage input connected to the LinkLine.
   */
  public void setFirstPassageInput(PassageInput passageInput) {
    this.firstPassageInput = passageInput;
  }

  /**
   * Sets the second passage input connected to the LinkLine, this is the passage that the link will
   * link to.
   *
   * @param passageInput The second passage input connected to the LinkLine.
   */
  public void setSecondPassageInput(PassageInput passageInput) {
    this.secondPassageInput = passageInput;
  }

  /**
   * Gets the first passage input of the LinkLine.
   *
   * @return The first passage input of the LinkLine.
   */
  public PassageInput getFirstPassageInput() {
    return firstPassageInput;
  }

  /**
   * Gets the second passage input of the LinkLine.
   *
   * @return The second passage input of the LinkLine.
   */
  public PassageInput getSecondPassageInput() {
    return secondPassageInput;
  }

  /**
   * Sets the text area position to a new position.
   *
   * @param position The position to the se the text area to.
   */
  private void setTextAreaPosition(Position position) {
    textHolder.setLayoutX(position.getX());
    textHolder.setLayoutY(position.getY());
  }

  /**
   * Puts the position of the text area in the middle of the LinkLine.
   */
  public void setTextAreaInMiddleOfLine() {
    Position newPosition = new Position((getEndX() + getStartX()) / 2,
        (getEndY() + getStartY()) / 2);
    setTextAreaPosition(newPosition);
  }

  /**
   * Returns the text area of the LinkLine.
   *
   * @return The text area of the LinkLine
   */
  public VBox getTextHolder() {
    return textHolder;
  }

  /**
   * Creates a link with the text from the text area as the text, and has the title of the second
   * passage as the reference.
   *
   * @return A link with the current text input as link text, and the title of the second connected
   *        passage as the reference.
   */
  public Link getLink() {
    Link link = new Link(linkText.getText(), getSecondPassageInput().getPassage().getTitle());
    actionCreator.retrieveActions().forEach(link::addAction);
    return link;
  }

  /**
   * Sets a new start position for the LinkLine.
   *
   * @param position The new starting position for the LinkLine.
   */
  public void setStartPosition(Position position) {
    setStartX(position.getX());
    setStartY(position.getY());

  }

  /**
   * Sets a new end position for the LinkLine.
   *
   * @param position The new ending position for the LinkLine.
   */
  public void setEndPosition(Position position) {
    setEndX(position.getX());
    setEndY(position.getY());
  }

  /**
   * Gets the starting position of the LinkLine.
   *
   * @return the starting position of the LinkLine.
   */
  public Position getStartPosition() {
    return new Position(getStartX(), getStartY());
  }

  /**
   * Gets the end position of the LinkLine.
   *
   * @return the end position of the LinkLine.
   */
  public Position getEndPosition() {
    return new Position(getEndX(), getEndY());
  }

  /**
   * Adds points to the arrowhead and moves its position to point at the second connected passage
   * input.
   */
  public void moveArrowHead() {
    double startX = getEndX();
    double startY = getEndY();
    double lineEndX = getStartX();
    double lineEndY = getStartY();
    double arrowLength = 10;

    double lineAngle = Math.atan2(lineEndY - startY, lineEndX - startX);
    double arrowAngle1 = lineAngle + Math.toRadians(30);
    double arrowAngle2 = lineAngle - Math.toRadians(30);
    double leftX = getEndX() + arrowLength * Math.cos(arrowAngle1);
    double leftY = getEndY() + arrowLength * Math.sin(arrowAngle1);
    double rightX = getEndX() + arrowLength * Math.cos(arrowAngle2);
    double rightY = getEndY() + arrowLength * Math.sin(arrowAngle2);

    arrowHead.getPoints().removeAll(arrowHead.getPoints());
    arrowHead.getPoints().addAll(getEndX(), getEndY(), leftX, leftY, rightX, rightY);
  }

  /**
   * Gets the arrowhead of the LinkLine.
   *
   * @return The arrowhead of the LinkLine.
   */
  public Polygon getArrowHead() {
    return arrowHead;
  }

}
