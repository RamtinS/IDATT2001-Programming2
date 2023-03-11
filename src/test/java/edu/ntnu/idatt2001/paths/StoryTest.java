package edu.ntnu.idatt2001.paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Story class.
 *
 * @author ...
 * @version JDK 17
 */
class StoryTest {
  private Story story;
  private Passage passage;

  @BeforeEach
  void setUp() {
    passage = new Passage("Test title", "Test content");
    story = new Story("Test title", passage);
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTest {
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      Story testStoryConstructor = new Story("Test title", passage);
      assertEquals("Test title", testStoryConstructor.getTitle());
      assertEquals(passage, testStoryConstructor.getOpeningPassage());
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException() {
      String invalidTitle = null;
      Passage invalidPassage = null;
      String validTitle = "Test title";
      assertThrows(NullPointerException.class, () -> new Story(invalidTitle, passage));
      assertThrows(NullPointerException.class, () -> new Story(validTitle, invalidPassage));
    }

    @Test
    @DisplayName("Test constructor invalid input throws IllegalArgumentException")
    void testConstructorInvalidInputThrowsIllegalArgumentException() {
      String invalidTitle = "";
      assertThrows(IllegalArgumentException.class, () -> new Story(invalidTitle, passage));
    }
  }

  @Nested
  @DisplayName("Story information tests")
  class StoryInformationTests {
    @Test
    @DisplayName("Should get the title of the story.")
    void shouldGetTitle() {
      String expectedTitle = "Test title";
      String actualTitle = story.getTitle();
      assertEquals(expectedTitle, actualTitle);
    }

    @Test
    @DisplayName("Should get the opening passage of the story.")
    void shouldGetOpeningPassage() {
      Passage expectedPassage = passage;
      Passage actualPassage = story.getOpeningPassage();
      assertEquals(expectedPassage, actualPassage);
    }
  }

  @Nested
  @DisplayName("Passage tests")
  class PassageTests {
    @Test
    @DisplayName("Should add passage")
    void shouldAddPassage() {
      story.addPassage(passage);
      assertFalse(story.getPassages().isEmpty());
    }

    @Test
    @DisplayName("Should not add passage")
    void shouldNotAddPassage() {
      Passage invalidPassage = null;
      assertThrows(NullPointerException.class, () -> story.addPassage(invalidPassage));
    }

    @Test
    @DisplayName("Should get passage")
    void shouldGetPassage() {
      story.addPassage(passage);
      Link link = new Link (passage.getTitle(), passage.getTitle());
      Passage expectedPassage = passage;
      Passage actualPassage = story.getPassage(link);
      assertEquals(expectedPassage, actualPassage);
    }

    @Test
    @DisplayName("Should not get passage")
    void shouldNotGetPassage() {
      Link invalidLink = null;
      assertThrows(NullPointerException.class, () -> story.getPassage(invalidLink));
    }

    @Test
    @DisplayName("Should get passages")
    void shouldGetPassages() {
      story.addPassage(passage);
      Map<Link, Passage> passages = new HashMap<>();
      Link link = new Link(passage.getTitle(), passage.getTitle());
      passages.put(link, passage);
      Collection<Passage> expectedPassages = passages.values();
      Collection<Passage> actualPassages = story.getPassages();
      assertTrue(expectedPassages.containsAll(actualPassages));
    }
  }
}