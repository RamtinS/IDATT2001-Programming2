package edu.ntnu.idatt2001.paths;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
  Passage passage;
  Link testLink;
  @BeforeEach
  void setUp() {
    passage = new Passage("Passage title", "Passage content");
    testLink = new Link("Link text", "Link reference");
  }

  @Test
  @DisplayName("Test constructor with invalid input throws NullPointerException")
    void testConstructorWithInvalidInputThrowsNullPointerException(){

    String validTitle = "Test title";
    String validContent = "Test content";

    String invalidTitle = null;
    String invalidContent = null;

    assertThrows(NullPointerException.class, () -> new Passage(invalidTitle, validContent));
    assertThrows(NullPointerException.class, () -> new Passage(validTitle, invalidContent));
  }

  @Test
  @DisplayName("Test constructor with invalid input throws IllegalArgumentException")
    void testConstructorWithInvalidInputThrowsIllegalArguementException(){

    String validTitle = "Test title";
    String validContent = "Test content";

    String invalidTitle = "";
    String invalidContent = "";

    assertThrows(IllegalArgumentException.class, () -> new Passage(invalidTitle, validContent));
    assertThrows(IllegalArgumentException.class, () -> new Passage(validTitle, invalidContent));
  }

  @Test
  @DisplayName("Test constructor with valid input")
  void testConstructorWithValidInput(){

    String expectedTitle = "Test title";
    String expectedContent = "Test content";

    Passage passage = new Passage(expectedTitle, expectedContent);

    assertEquals(expectedTitle, passage.getTitle());
    assertEquals(expectedContent, passage.getContent());
  }

  @Test
  @DisplayName("Should get title")
  void shouldGetTitle() {
    String expected = "Passage title";
    String actual = passage.getTitle();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get content")
  void shouldGetContent() {
    String expected = "Passage content";
    String actual = passage.getContent();
    assertEquals(expected, actual);
  }


  @Test
  @DisplayName("Should add link")
  void shouldAddLink() {
    Link link = new Link("Link text", "Link reference");
    boolean expected = true;
    boolean actual = passage.addLink(link);
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should not add link, should throw exception")
  void shouldNotAddLinkAndThrowException(){
    assertThrows(NullPointerException.class,() -> passage.addLink(null));
}

  @Test
  @DisplayName("Should get links")
  void shouldGetLinks() {

    passage.addLink(testLink);
    List<Link> actual = passage.getLinks();

    List<Link> expected = new ArrayList<>();
    expected.add(testLink);

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should have links")
  void ShouldHaveLinks() {
    Link link = new Link("Link text", "Link reference");
    passage.addLink(link);
    boolean actual = passage.hasLinks();

    assertTrue(actual);
  }

  @Test
  @DisplayName("toString()")
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
  @DisplayName("Passages should be equal")
  void shouldBeEqual() {
    Passage passage2 = new Passage("Passage title", "Passage content");

    assertEquals(passage, passage2);

  }
}