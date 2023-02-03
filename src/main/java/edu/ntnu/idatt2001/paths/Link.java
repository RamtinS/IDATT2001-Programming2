package edu.ntnu.idatt2001.paths;

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
  private List<Action> actions;

  public Link(String text, String reference) {
    if (text.isBlank()) throw new IllegalArgumentException("\nText cannot be blank.");
    if (reference.isBlank()) throw new IllegalArgumentException("\nReference cannot be blank.");
    this.text = text;
    this.reference = reference;
  }

  public String getText() {
    return text;
  }

  public String getReference() {
    return reference;
  }

  public addAction(Action action){
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
