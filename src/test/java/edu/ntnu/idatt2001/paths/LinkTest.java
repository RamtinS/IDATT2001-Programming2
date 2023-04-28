package edu.ntnu.idatt2001.paths;

import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the Link class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since March 28, 2023.
 */
class LinkTest {
  private Link link;

  @BeforeEach
  void setUp() {
    link = new Link("Test text","Test reference");
  }

  @Nested
  @DisplayName("Constructor tests")
  class ConstructorTests {
    @Test
    @DisplayName("Test constructor valid input")
    void testConstructorValidInput() {
      Link testLink = new Link("Test constructor", "Test constructor");
      assertEquals("Test constructor", testLink.getText());
      assertEquals("Test constructor", testLink.getReference());
    }

    @Test
    @DisplayName("Test constructor invalid input throws NullPointerException")
    void testConstructorInvalidInputThrowsNullPointerException() {
      String invalidText = null;
      String invalidReference = null;
      String validText = "Test constructor";
      String validReference = "Test constructor";
      assertThrows(NullPointerException.class, () -> new Link(invalidText, validReference));
      assertThrows(NullPointerException.class, () -> new Link(validText, invalidReference));
    }

    @Test
    @DisplayName("Test constructor invalid input throws IllegalArgumentException")
    void testConstructorInvalidInputThrowsIllegalArgumentException() {
      String invalidText = "";
      String invalidReference = "";
      String validText = "Test constructor";
      String validReference = "Test constructor";
      assertThrows(IllegalArgumentException.class, () -> new Link(invalidText, validReference));
      assertThrows(IllegalArgumentException.class, () -> new Link(validText, invalidReference));
    }
  }

  @Nested
  @DisplayName("Test link information")
  class TestLinkInformation {
    @Test
    @DisplayName("Should get the text of the link.")
    void shouldGetTextOfLink() {
      String expectedText = "Test text";
      String actualText = link.getText();
      assertEquals(expectedText, actualText);
    }

    @Test
    @DisplayName("Should get the reference of the link.")
    void shouldGetReferenceOfLink() {
      String expectedReference = "Test reference";
      String actualReference = link.getReference();
      assertEquals(expectedReference, actualReference);
    }
  }

  @Nested
  @DisplayName("Action tests")
  class ActionTests {
    @Test
    @DisplayName("Should add action.")
    void shouldAddAction() {
      GoldAction goldAction = new GoldAction(5);
      link.addAction(goldAction);
      assertFalse(link.getActions().isEmpty());
    }

    @Test
    @DisplayName("Should not add action throws NullPointerException")
    void shouldNotAddActionThrowsNullPointerException() {
      GoldAction invalidAction = null;
      assertThrows(NullPointerException.class, () -> link.addAction(invalidAction));
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
  }

  @Nested
  @DisplayName("Test other methods")
  class TestOtherMethods {
    @Test
    @DisplayName("Test toString")
    void testToString() {
      String expected = """
            Text: Test text
            Reference: Test reference
            Actions: []""";
      String actual = link.toString();
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test equals method")
    void testEqualsMethod() {
      Link testLink = new Link ("Test text", "Test reference");
      assertEquals(link, testLink);
    }
  }
}