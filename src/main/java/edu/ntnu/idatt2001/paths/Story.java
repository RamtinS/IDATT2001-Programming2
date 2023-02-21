package edu.ntnu.idatt2001.paths;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class that represents a story.
 *
 * @author ...
 * @version JDK 17
 */
public class Story {
  private final String title;
  private final Map<Link, Passage> passages;
  private final Passage openingPassage;

  /**
   * Constructor for the story object.
   *
   * @param title The title of the story
   * @param openingPassage The passage that the story will start from.
   * @throws NullPointerException If either the title or opening passage is null.
   * @throws IllegalArgumentException If the title is blank.
   */
  public Story(String title, Passage openingPassage) throws IllegalArgumentException, NullPointerException {
    if (title.isBlank()) throw new IllegalArgumentException("Title cannot be blank.");
    this.title = Objects.requireNonNull(title, "Title cannot be blank.");
    this.openingPassage = Objects.requireNonNull(openingPassage, "Opening passage cannot be null.");
    this.passages = new HashMap<>();
  }

  /**
   * Gets the title of the story.
   *
   * @return The title of the story.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Retrieves the opening passage of the story.
   *
   * @return The opening passage of the story.
   */
  public Passage getOpeningPassage() {
    return openingPassage;
  }

  /**
   * Adds a passage to the list of passages.
   *
   * @param passage The passage that will be added to the story.
   * @throws NullPointerException If the given passage is null.
   */
  public void addPassage(Passage passage) throws NullPointerException {
    Objects.requireNonNull(passage, "\nPassage cannot be null");
    Link link = new Link(passage.getTitle(), passage.getTitle());
    passages.put(link, passage);
  }

  /**
   * Gets the passage that is connected to a given link.
   *
   * @param link The link that will be used to search for matching passages.
   * @return The passage corresponding to the link.
   * @throws NullPointerException If the link is null.
   */
  public Passage getPassage(Link link) throws NullPointerException {
    Objects.requireNonNull(link, "\nLink cannot be null.");
    return this.passages.get(link);
  }

  /**
   * Creates a collection of all passages and returns it.
   *
   * @return A collection of all passages.
   */
  public Collection<Passage> getPassages() {
    return this.passages.values();
  }
}
