package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.actions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a Link.
 *
 * @author ...
 * @version JDK 17
 */
public class Link {
  private final String text;
  private final String reference;
  private final List<Action> actions;

  /**
   * Constructor to create an object of the type Link.
   *
   * @param text description of a choice or action in a story.
   * @param reference uniquely identifies a passage.
   * @throws NullPointerException if text or reference is null.
   * @throws IllegalArgumentException if text or reference is blank.
   */
  public Link(String text, String reference) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(text, "\nText cannot be null.");
    Objects.requireNonNull(reference, "\nReference cannot be null.");
    if (text.isBlank()) throw new IllegalArgumentException("\nText cannot be blank.");
    if (reference.isBlank()) throw new IllegalArgumentException("\nReference cannot be blank.");
    this.text = text;
    this.reference = reference;
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
   */
  public void addAction(Action action){
    Objects.requireNonNull(action, "\nAction cannot be null.");
    actions.add(action);
  }

  /**
   * The method retrieves the list actions, which
   * contains objects that make it possible to
   * influence the characteristics of a player
   *
   * @return list of actions.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * The toString collects all the information about the link,
   * and return a textual representation of the link.
   *
   * @return information about the link.
   */
  @Override
  public String toString() {
    return "\nText: " + getText() + "."
            + "\nReference: " + getReference() + ".";
  }

  /**
   * The method checks for equality between objects.
   *
   * @param o the object to which it is being compared.
   * @return a boolean value which indicate whether they are equal or not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Link link = (Link) o;
    return Objects.equals(getText(),
            link.getText()) && Objects.equals(getReference(),
            link.getReference());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getText(), getReference());
  }
}
