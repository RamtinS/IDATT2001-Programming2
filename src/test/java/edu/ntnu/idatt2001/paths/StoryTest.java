package edu.ntnu.idatt2001.paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Story class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 24, 2023.
 */
class StoryTest {
  private Story story;
  private Passage openingPassage;

  @BeforeEach
  void setUp() {
    openingPassage = new Passage("Test title", "Test content");
    story = new Story("Test title", openingPassage);
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTest {
    private final String validTitle = "Story title";
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      Story testStoryConstructor = new Story(validTitle, openingPassage);
      assertEquals(validTitle, testStoryConstructor.getTitle());
      assertEquals(openingPassage, testStoryConstructor.getOpeningPassage());
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> new Story(null, openingPassage));
      assertThrows(NullPointerException.class, () -> new Story(validTitle, null));
    }

    @Test
    @DisplayName("Test constructor invalid input throws IllegalArgumentException")
    void testConstructorInvalidInputThrowsIllegalArgumentException() {
      String invalidTitle = "";
      assertThrows(IllegalArgumentException.class, () -> new Story(invalidTitle, openingPassage));
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
      Passage expectedPassage = openingPassage;
      Passage actualPassage = story.getOpeningPassage();
      assertEquals(expectedPassage, actualPassage);
    }
  }

  @Nested
  @DisplayName("Passage tests")
  class PassageTests {
    private final Link link1 = new Link("Text link1", "Title passage2");
    private final Passage passage1 = new Passage("Title passage1", "Content passage1");
    private final Link link2 = new Link("Text link2" , "Title passage4");
    private final Passage passage2 = new Passage("Title passage2", "Content passage2");
    private final Link link3 = new Link("Text link3" , "Title passage2");
    private final Passage passage3 = new Passage("Title passage3", "Content passage3");

    @Test
    @DisplayName("Should get current passage")
    void shouldGetCurrentPassage() {
      Passage expectedPassage = openingPassage;
      Passage actualPassage = story.getCurrentPassage();
      assertEquals(expectedPassage, actualPassage);
    }

    @Test
    @DisplayName("Should set new current passage")
    void shouldSetNewCurrentPassage() {
      story.setCurrentPassage(passage1);
      Passage actualPassage = story.getCurrentPassage();
      assertEquals(passage1, actualPassage);
    }

    @Test
    @DisplayName("Should not set new current passage throws NullPointerException")
    void shouldNotSetNewCurrentPassageThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> story.setCurrentPassage(null));
    }

    @Test
    @DisplayName("Should add passage")
    void shouldAddPassage() {
      story.addPassage(passage1);
      assertTrue(story.getPassages().contains(passage1));
    }

    @Test
    @DisplayName("Should not add passage throws NullPointerException")
    void shouldNotAddPassageThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> story.addPassage(null));
    }

    @Test
    @DisplayName("Should get passage")
    void shouldGetPassage() {
      story.addPassage(passage2);
      Passage actualPassage = story.getPassage(link1);
      assertEquals(passage2, actualPassage);
    }

    @Test
    @DisplayName("Should not get passage")
    void shouldNotGetPassage() {
      assertThrows(NullPointerException.class, () -> story.getPassage(null));
    }

    @Test
    @DisplayName("Should get passages")
    void shouldGetPassages() {
      story.addPassage(passage1);
      Map<Link, Passage> passages = new HashMap<>();
      Link link = new Link(passage1.getTitle(), passage1.getTitle());
      passages.put(link, passage1);
      Collection<Passage> expectedPassages = passages.values();
      Collection<Passage> actualPassages = story.getPassages();
      assertTrue(expectedPassages.containsAll(actualPassages));
    }

    @Test
    @DisplayName("Should remove passage")
    void shouldRemovePassage() {
      passage1.addLink(link1);
      passage2.addLink(link2);

      story.addPassage(passage1);
      story.addPassage(passage2);

      assertTrue(story.getPassages().contains(passage2));
      story.removePassage(link1);
      assertFalse(story.getPassages().contains(passage2));
    }

    @Test
    @DisplayName("Should not remove passage throws NullPointerException")
    void shouldNotRemovePassageThrowsNullPointerException() {
      assertThrows(NullPointerException.class, () -> story.removePassage(null));
    }

    @Test
    @DisplayName("Should not remove passage throws IllegalStateException")
    void shouldNotRemovePassageThrowsIllegalStateException() {
      passage1.addLink(link1);
      passage2.addLink(link2);
      passage3.addLink(link3);

      story.addPassage(passage1);
      story.addPassage(passage2);
      story.addPassage(passage3);

      assertThrows(IllegalStateException.class, () -> story.removePassage(link1));
    }

    @Test
    @DisplayName("Should get broken links")
    void shouldGetBrokenLinks() {
      passage1.addLink(link1);
      passage2.addLink(link2);

      story.addPassage(passage1);
      story.addPassage(passage2);

      story.removePassage(link1);

      List<Link> expectedBrokenLinks = new ArrayList<>();
      expectedBrokenLinks.add(link1);
      List<Link> actualBrokenLinks = story.getBrokenLinks();

      assertEquals(expectedBrokenLinks, actualBrokenLinks);
    }
  }
}