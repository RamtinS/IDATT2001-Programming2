package edu.ntnu.idatt2001.paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Story class.
 *
 * @author ...
 * @version JDK 17
 */
class StoryTest {

  Story story;

  @BeforeEach
  void setUp() {
    story = new Story("Test title", createDefualtPassage());
  }

  @Test
  @DisplayName("Should get the title of the story.")
  void shouldGetTitle() {
    String expected = "Test title";
    String actual = story.getTitle();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get the opening passage of the story.")
  void shouldGetOpeningPassage() {
    Passage expectedPassage = createDefualtPassage();
    Passage actualPassage = story.getOpeningPassage();
    assertEquals(expectedPassage, actualPassage);
  }

  @Test
  @DisplayName("Should add passage")
  void shouldAddPassage() {
    story.addPassage(createDefualtPassage());
    assertFalse(story.getPassages().isEmpty());
  }

  @Test
  @DisplayName("Should not add passage")
  void shouldNotAddPassage() {
    Passage passage = null;
    assertThrows(NullPointerException.class, () -> story.addPassage(passage));
  }

  @Test
  @DisplayName("Should get passage")
  void shouldGetPassage() {
    story.addPassage(createDefualtPassage());
    Link link = new Link (createDefualtPassage().getTitle(), createDefualtPassage().getTitle());
    Passage expected = createDefualtPassage();
    Passage actual = story.getPassage(link);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should not get passage")
  void shouldNotGetPassage() {
    Link link = null;
    assertThrows(NullPointerException.class, () -> story.getPassage(link));
  }

  @Test
  @DisplayName("Should get passages")
  void shouldGetPassages() {
    story.addPassage(createDefualtPassage());
    List<Passage> expectedPassages = new ArrayList<>();
    expectedPassages.add(createDefualtPassage());
    assertTrue(expectedPassages.containsAll(story.getPassages()));
  }

  /**
   * Creates a default passage that can be used in the test methods.
   *
   * @return default passage.
   */
  private Passage createDefualtPassage() {
    return new Passage("Test title", "Test content");
  }
}