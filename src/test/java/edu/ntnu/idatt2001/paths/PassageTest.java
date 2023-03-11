package edu.ntnu.idatt2001.paths;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * The class tests the Passage class
 *
 * @author ...
 * @version JDK 17
 */
class PassageTest {
  private Passage passage;
  private Link testLink;

  @BeforeEach
  void setUp() {
    passage = new Passage("Passage title", "Passage content");
    testLink = new Link("Link text", "Link reference");
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTests {
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput(){
      String expectedTitle = "Test title";
      String expectedContent = "Test content";
      Passage passage = new Passage(expectedTitle, expectedContent);
      assertEquals(expectedTitle, passage.getTitle());
      assertEquals(expectedContent, passage.getContent());
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException(){
      String validTitle = "Test title";
      String validContent = "Test content";
      String invalidTitle = null;
      String invalidContent = null;
      assertThrows(NullPointerException.class, () -> new Passage(invalidTitle, validContent));
      assertThrows(NullPointerException.class, () -> new Passage(validTitle, invalidContent));
    }

    @Test
    @DisplayName("Test constructor invalid input throws IllegalArgumentException")
    void testConstructorInvalidInputThrowsIllegalArgumentException(){
      String validTitle = "Test title";
      String validContent = "Test content";
      String invalidTitle = "";
      String invalidContent = "";
      assertThrows(IllegalArgumentException.class, () -> new Passage(invalidTitle, validContent));
      assertThrows(IllegalArgumentException.class, () -> new Passage(validTitle, invalidContent));
    }
  }

  @Nested
  @DisplayName("Link tests")
  class LinkTests {
    @Test
    @DisplayName("Should add link")
    void shouldAddLink() {
      Link link = new Link("Link text", "Link reference");
      assertTrue(passage.addLink(link));
    }

    @Test
    @DisplayName("Should not add link throws NullPointerException")
    void shouldNotAddLinkThrowsNullPointerException(){
      assertThrows(NullPointerException.class,() -> passage.addLink(null));
    }

    @Test
    @DisplayName("Should get links")
    void shouldGetLinks() {
      passage.addLink(testLink);
      List<Link> actualLinks = passage.getLinks();
      List<Link> expectedLinks = new ArrayList<>();
      expectedLinks.add(testLink);
      assertEquals(expectedLinks, actualLinks);
    }

    @Test
    @DisplayName("Should have links")
    void ShouldHaveLinks() {
      Link link = new Link("Link text", "Link reference");
      passage.addLink(link);
      assertTrue(passage.hasLinks());
    }

    @Test
    @DisplayName("Should not have links")
    void ShouldNotHaveLinks() {
      assertFalse(passage.hasLinks());
    }
  }

  @Nested
  @DisplayName("Test passage information")
  class TestPassageInformation {
    @Test
    @DisplayName("Should get title")
    void shouldGetTitle() {
      String expectedTitle = "Passage title";
      String actualTitle = passage.getTitle();
      assertEquals(expectedTitle, actualTitle);
    }

    @Test
    @DisplayName("Should get content")
    void shouldGetContent() {
      String expectedContent = "Passage content";
      String actualContent = passage.getContent();
      assertEquals(expectedContent, actualContent);
    }
  }

  @Nested
  @DisplayName("Test other methods")
  class TestOtherMethods {
    @Test
    @DisplayName("Test toString()")
    void testToString() {
      passage.addLink(testLink);
      String expected = """
            Title: Passage title
            Content: Passage content
            Links: [Text: Link text.
            Reference: Link reference.]""";
      String actual = passage.toString();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test equals method")
    void testEqualsMethod() {
      Passage testPassage = new Passage("Passage title", "Passage content");
      assertEquals(passage, testPassage);
    }
  }
}