package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a passage.
 *
 * @author ...
 * @version JDK 17
 */
public class Passage {
  private final String title;
  private final String content;
  private final List<Link> links;

  /**
   * Constructor to create an object of the type passage.
   *
   * @param title overall description of passage.
   * @param content represents a paragraph or part of a dialogue.
   * @throws NullPointerException if title or content is null.
   * @throws IllegalArgumentException if title or content is blank.
   */
  public Passage(String title, String content) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(title, "\nTitle cannot be null");
    Objects.requireNonNull(content, "\nContent cannot be null");
    if (title.isBlank()) throw new IllegalArgumentException("\nTitle cannot be blank");
    if (content.isBlank()) throw new IllegalArgumentException("\nContent cannot be blank");
    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  /**
   * The method retrieves the title of the passage.
   *
   * @return title of the passage.
   */
  public String getTitle(){
    return title;
  }

  /**
   * The method retrieves the content of the passage.
   *
   * @return content of the passage.
   */
  public String getContent(){
    return content;
  }

  /**
   * The method adds a link to the list of links.
   *
   * @param link link which is being added to the list of links.
   * @return boolean value which checks if the link was added.
   */
  public boolean addLink(Link link){
    return links.add(link);
  }

  /**
   * The method retrieves the list of links.
   *
   * @return the list links.
   */
  public List<Link> getLinks(){
    return links;
  }

  /**
   * The method checks if the list of links contains links.
   *
   * @return true or false depending on whether the list is empty.
   */
  public boolean hasLinks(){
    return !links.isEmpty();
  }

  /**
   * The toString collects all the information about the passage,
   * and return a textual representation of the passage.
   *
   * @return information about the passage.
   */
  @Override
  public String toString(){
    return "\nTitle: " + getTitle() + "\nContent: " + getContent();
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
    Passage passage = (Passage) o;
    return getTitle().equals(passage.getTitle()) && getContent().equals(passage.getContent());
  }

  /**
   * The method generates a hash value for the object.
   *
   * @return hash value for the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getTitle(), getContent());
  }
}
