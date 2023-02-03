package edu.ntnu.idatt2001.paths;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Story {
  private final String title;
  private final Map<Link, Passage> passages;
  private final Passage openingPassage;

  public Story(String title, Passage openingPassage) throws NullPointerException, IllegalArgumentException{
    Objects.requireNonNull(title, "\nTitle cannot be blank");
    Objects.requireNonNull(openingPassage, "\nopeningPassage cannot be null.");
    if (title.isBlank()) throw new IllegalArgumentException("\nTitle cannot be blank");
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap <>();
  }

  public String getTitle() {
    return title;
  }

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  public void addPassage(Passage passage) throws NullPointerException {
    Objects.requireNonNull(passage, "\nPassage cannot be null");
    Link link = new Link(passage.getTitle(), passage.getTitle());
    passages.put(link, passage);
  }

  public Passage getPassage(Link link) throws NullPointerException {
    Objects.requireNonNull(link, "\nLink cannot be null.");
    return this.passages.get(link);
  }

  public Collection<Passage> getPassages() {
    return this.passages.values();
  }
}
