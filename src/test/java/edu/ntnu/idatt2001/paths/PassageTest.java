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
  @BeforeEach
  void setUp() {
    passage = new Passage("Passage title", "Passage content");
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

    passage.addLink(createDefaultLink());
    List<Link> actual = passage.getLinks();

    List<Link> expected = new ArrayList<>();
    expected.add(createDefaultLink());

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should have links")
  void ShouldHaveLinks() {
    Link link = new Link("Link text", "Link reference");
    passage.addLink(link);
    boolean actual = passage.hasLinks();

    boolean expected = true;

    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("toString()")
  void testToString() {
    passage.addLink(createDefaultLink());
    String expected = """
                
            Title: Passage title
            Content: Passage content
            Links: [
            Text: Link text.
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

  /**
   * Standard data for some methods, creates a link that can
   * be used to test other methods.
   *
   * @return The created link.
   */
  private Link createDefaultLink(){
    return new Link("Link text", "Link reference");
  }
}