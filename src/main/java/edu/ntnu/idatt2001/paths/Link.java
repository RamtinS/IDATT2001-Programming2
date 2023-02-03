package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
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

  public Link(String text, String reference) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(text, "\nText cannot be null.");
    Objects.requireNonNull(reference, "\nReference cannot be null.");
    if (text.isBlank()) throw new IllegalArgumentException("\nText cannot be blank.");
    if (reference.isBlank()) throw new IllegalArgumentException("\nReference cannot be blank.");
    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
  }

  public String getText() {
    return text;
  }

  public String getReference() {
    return reference;
  }

  public void addAction(Action action){
    actions.add(action);
  }

  public List<Action> getActions() {
    return actions;
  }

  @Override
  public String toString() {
    return "\nText: " + getText() + "."
            + "\nReference: " + getReference() + ".";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Link link = (Link) o;
    return Objects.equals(getText(),
            link.getText()) && Objects.equals(getReference(),
            link.getReference());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getText(), getReference());
  }
}
