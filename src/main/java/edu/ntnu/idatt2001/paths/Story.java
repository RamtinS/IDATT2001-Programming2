package edu.ntnu.idatt2001.paths;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
  public Story(String title, Passage openingPassage)
          throws IllegalArgumentException, NullPointerException {
    if (title.isBlank()) {
      throw new IllegalArgumentException("Title cannot be blank.");
    }
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
    if (passage == null) {
      throw new NullPointerException("Passage cannot be null.");
    }
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
    if (link == null) {
      throw new NullPointerException("Link cannot be null.");
    }
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

  /**
   * The method removes a passage with the given link from the map of passages.
   * The link cannot remove a passage if other passages link to it.
   *
   * @param link the link representing the passage to be removed.
   * @throws NullPointerException if the link is null;
   * @throws IllegalStateException if the passage cannot be removed because of the state.
   */
  public void removePassage(Link link) throws NullPointerException, IllegalStateException {
    if (link == null) {
      throw new NullPointerException("Link cannot be null");
    }
    boolean invalidLink = getPassages()
            .stream()
            .anyMatch(p -> p.getLinks()
                    .stream()
                    .anyMatch(l -> !l.equals(link)
                            && l.getReference().equalsIgnoreCase(link.getReference())));
    if (invalidLink) {
      throw new IllegalStateException("Passage cannot be removed since other passages link to it.");
    }
    Link validLink = new Link(link.getReference(), link.getReference());
    this.passages.remove(validLink);
  }

  /**
   * The method finds and returns a list of broken links,
   * links that reference a non-existent passage.
   *
   * @return a list of broken links.
   */
  public List<Link> getBrokenLinks() {
    return getPassages().stream()
            .flatMap(passage -> passage.getLinks()
                    .stream()
                    .filter(link -> getPassages()
                            .stream()
                            .noneMatch(p -> p.getTitle().equals(link.getReference()))))
            .collect(Collectors.toList());
  }
}


