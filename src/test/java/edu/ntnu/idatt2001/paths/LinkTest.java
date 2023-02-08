package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Link class.
 *
 * @author ...
 * @version JDK 17
 */
class LinkTest {

  Link link;

  @BeforeEach
  void setUp() {
    link = new Link("Test text","Test reference");
  }

  @Test
  @DisplayName("Should get the text of the link.")
  void shouldGetTextOfLink() {
    String expected = "Test text";
    String actual = link.getText();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should get the reference of the link.")
  void shouldGetReferenceOfLink() {
    String expected = "Test reference";
    String actual = link.getReference();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Should add action.")
  void shouldAddAction() {
    GoldAction goldAction = new GoldAction(5);
    link.addAction(goldAction);
    assertFalse(link.getActions().isEmpty());
  }

  @Test
  @DisplayName("Should not add action and throw Exception")
  void shouldNotAddActionAndThrowException() {
    GoldAction goldAction = null;
    assertThrows(NullPointerException.class, () -> link.addAction(goldAction));
  }

  @Test
  @DisplayName("Should get the list of actions.")
  void shouldGetActionsList() {
    GoldAction goldAction = new GoldAction(5);
    link.addAction(goldAction);

    List<Action> expectedList =  new ArrayList<>();
    expectedList.add(goldAction);
    List<Action> actualList = link.getActions();

    assertEquals(expectedList, actualList);
  }

  @Test
  void testToString() {
    String expected = """
            Text: Test text.
            Reference: Test reference.""";
    String actual = link.toString();
    assertEquals(expected, actual);
  }

  @Test
  void testEquals() {
    Link link2 = new Link ("Test text", "Test reference");
    assertEquals(link, link2);
  }
}