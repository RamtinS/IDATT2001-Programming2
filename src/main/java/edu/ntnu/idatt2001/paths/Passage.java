package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a passage in a story. The passage
 * contains a title, content, and a list of links.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 15, 2023.
 */
public class Passage {
  private final String title;
  private final String content;
  private final List<Link> links;

  /**
   * Constructor to create an object of the type passage.
   *
   * @param title   overall description of passage.
   * @param content represents a paragraph or part of a dialogue.
   * @throws NullPointerException     if title or content is null.
   * @throws IllegalArgumentException if title or content is blank.
   */
  public Passage(String title, String content)
          throws IllegalArgumentException, NullPointerException {
    if (title.isBlank()) {
      throw new IllegalArgumentException("Title cannot be blank");
    }
    if (content.isBlank()) {
      throw new IllegalArgumentException("Content cannot be blank");
    }
    this.title = Objects.requireNonNull(title.trim(), "Title cannot be null");
    this.content = Objects.requireNonNull(content.trim(), "Content cannot be null");
    this.links = new ArrayList<>();
  }

  /**
   * The method retrieves the title of the passage.
   *
   * @return title of the passage.
   */
  public String getTitle() {
    return title;
  }

  /**
   * The method retrieves the content of the passage.
   *
   * @return content of the passage.
   */
  public String getContent() {
    return content;
  }

  /**
   * The method adds a link to the list of links.
   *
   * @param link link which is being added to the list of links.
   * @return boolean value which checks if the link was added.
   * @throws NullPointerException if link is null.
   */
  public boolean addLink(Link link) throws NullPointerException {
    if (link == null) {
      throw new NullPointerException("Link cannot be null");
    }
    return links.add(link);
  }

  /**
   * The method retrieves the list of links.
   *
   * @return the list links.
   */
  public List<Link> getLinks() {
    return links;
  }

  /**
   * The method checks if the list of links contains links.
   *
   * @return true or false depending on whether the list is empty.
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * The toString collects all the information about the
   * passage, and return a textual representation.
   *
   * @return textual representation of the passage.
   */
  @Override
  public String toString() {
    return "Title: " + getTitle()
            + "\nContent: " + getContent()
            + "\nLinks: " + getLinks();
  }

  /**
   * The method checks for equality between objects.
   *
   * @param o The object that this object will be compared to.
   * @return true if the objects are equal, false if they are not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Passage passage = (Passage) o;
    return getTitle().equals(passage.getTitle())
            && getContent().equals(passage.getContent())
            && getLinks().equals(passage.getLinks());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getTitle(), getContent(), getLinks());
  }
}