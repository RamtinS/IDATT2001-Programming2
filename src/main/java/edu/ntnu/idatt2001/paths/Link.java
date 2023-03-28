package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a link between two passages in a story.
 * A link consists of a text indicating a choice or action, a reference
 * to a passage, and a list of actions that can influence the characteristics
 * of a player.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 26, 2023.
 */
public class Link {
  private final String text;
  private final String reference;
  private final List<Action> actions;

  /**
   * Constructs a Link object with the given text and reference.
   *
   * @param text description of a choice or action in a story.
   * @param reference uniquely identifies a passage.
   * @throws NullPointerException if text or reference is null.
   * @throws IllegalArgumentException if text or reference is blank.
   */
  public Link(String text, String reference) throws IllegalArgumentException, NullPointerException {
    if (text.isBlank()) {
      throw new IllegalArgumentException("Text cannot be blank.");
    }
    if (reference.isBlank()) {
      throw new IllegalArgumentException("Reference cannot be blank.");
    }
    this.text = Objects.requireNonNull(text, "Text cannot be null.");
    this.reference = Objects.requireNonNull(reference, "Reference cannot be null.");
    this.actions = new ArrayList<>();
  }

  /**
   * The method retrieves the text of the link.
   *
   * @return the text of the link.
   */
  public String getText() {
    return text;
  }

  /**
   * The method retrieves the reference of the link.
   *
   * @return the reference of the link.
   */
  public String getReference() {
    return reference;
  }

  /**
   * The method adds an action to the list of actions.
   *
   * @param action action which is being added.
   * @throws NullPointerException if action is null.
   */
  public void addAction(Action action) throws NullPointerException {
    if (action == null) {
      throw new NullPointerException("Action cannot be null");
    }
    actions.add(action);
  }

  /**
   * The method retrieves the list actions, which
   * contains objects that make it possible to
   * influence the characteristics of a player.
   *
   * @return list of actions.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * The toString collects all the information about the
   * link, and return a textual representation.
   *
   * @return textual representation of the link.
   */
  @Override
  public String toString() {
    return "Text: " + getText()
            + "\nReference: " + getReference()
            + "\nActions: " + getActions();
  }

  /**
   * The method checks for equality between link objects.
   *
   * @param o the object to which it is being compared.
   * @return a boolean value which indicate whether they are equal or not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return getText().equals(link.getText())
            && getReference().equals(link.getReference())
            && getActions().equals(link.getActions());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getText(), getReference(), getActions());
  }
}
