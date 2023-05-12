package edu.ntnu.idatt2001.paths.gui.storycreation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Represents a position given by an x and y coordinate. Contains a circle point that can be used to
 * show where the position is located on the screen.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 12, 2023.
 */
public class Position {

  private double x;
  private double y;
  private final Circle icon;

  /**
   * Creates a position from given x and y values.
   *
   * @param x The x value of the position.
   * @param y The y value of the position.
   */
  public Position(double x, double y) {
    if (x < 0) {
      throw new IllegalArgumentException("X value cannot be negative");
    }
    if (y < 0) {
      throw new IllegalArgumentException("Y value cannot be negative");
    }
    this.x = x;
    this.y = y;
    this.icon = new Circle(x, y, 2, Paint.valueOf("#000000"));
  }

  /**
   * Finds the distance between this object and a given position.
   *
   * @param position The position to find the distance to this object.
   * @return The distance between this object and the given position
   */
  public double getDistance(Position position) {
    return Math.sqrt(Math.pow(Math.abs(position.getY() - getY()), 2) + Math.pow(
        Math.abs(position.getX() - getX()), 2));
  }

  /**
   * Finds the closest of the 4 middle positions on each side of the bounds of a given node.
   *
   * @param node     The node that will be used to calculate the closest position
   * @param position The position used to calculate the distance between a position and the sides of
   *                 the node.
   * @return The closest position from this object that is located on the middle position of * the
   * closest node side.
   */
  public static Position getClosestSideMiddlePosition(Node node, Position position) {
    List<Position> positionsList = new ArrayList<>();
    positionsList.add(getTopCenterPosition(node));
    positionsList.add(getMiddleLeftPosition(node));
    positionsList.add(getLowerCenterPosition(node));
    positionsList.add(getMiddleRightPosition(node));
    return positionsList.stream().min(Comparator.comparingDouble(position::getDistance))
        .orElse(null);
  }

  /**
   * Calculates the top center position of the bounds of a node.
   *
   * @param node The node used to calculate the top center position of.
   * @return The top center position of the bounds of the given node.
   */
  public static Position getTopCenterPosition(Node node) {
    Bounds boundsOfNode = node.localToScene(node.getBoundsInLocal());
    return new Position(boundsOfNode.getCenterX(), boundsOfNode.getMinY());
  }

  /**
   * Calculates the top center position of the bounds of a node.
   *
   * @param node The node used to calculate the top center position of.
   * @return The top center position of the bounds of the given node.
   */
  public static Position getLowerCenterPosition(Node node) {
    Bounds boundsOfNode = node.localToScene(node.getBoundsInLocal());
    return new Position(boundsOfNode.getCenterX(), boundsOfNode.getMaxY());
  }

  /**
   * Calculates the top center position of the bounds of a node.
   *
   * @param node The node used to calculate the top center position of.
   * @return The top center position of the bounds of the given node.
   */
  public static Position getMiddleRightPosition(Node node) {
    Bounds boundsOfNode = node.localToScene(node.getBoundsInLocal());
    return new Position(boundsOfNode.getMaxX(), boundsOfNode.getCenterY());
  }

  /**
   * Calculates the top center position of the bounds of a node.
   *
   * @param node The node used to calculate the top center position of.
   * @return The top center position of the bounds of the given node.
   */
  public static Position getMiddleLeftPosition(Node node) {
    Bounds boundsOfNode = node.localToScene(node.getBoundsInLocal());
    return new Position(boundsOfNode.getMinX(), boundsOfNode.getCenterY());
  }

  /**
   * Calculates the center of a the {@link Bounds} of a {@link Node}.
   *
   * @param node The node used to calculate the center of.
   * @return The center position of the given node.
   */
  public static Position getCenterOfNode(Node node) {

    Bounds boundsOfNode = node.localToScene(node.getBoundsInLocal());
    return new Position(boundsOfNode.getCenterX(), boundsOfNode.getCenterY());
  }

  /**
   * Gets a circle representing the icon of the position.
   *
   * @return A circle representing the icon of the position.
   */
  public Circle getIcon() {
    return icon;
  }

  /**
   * Gets the current x value of the position.
   *
   * @return The current x value of the position.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the current y value of the position.
   *
   * @return The current y value of the position.
   */
  public double getY() {
    return y;
  }

  /**
   * Sets a new x value for the position.
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Sets a new x value for the position.
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Creates a string containing the x and y coordinate of the position.
   *
   * @return A string containing the x and y coordinate of the position.
   */
  @Override
  public String toString() {
    return "Position(" + x + "," + y + ")";
  }
}
