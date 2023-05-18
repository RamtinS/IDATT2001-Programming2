package edu.ntnu.idatt2001.paths.gui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The class tests the {@link InputField} class.
 *
 * @author Tobias Oftedal
 * @author Ramtin Samavat
 * @version 1.0
 * @since May 15, 2023.
 */
class InputFieldTest {

  InputField inputField;

  @BeforeAll
  static void platformStart() {
    Platform.startup(() -> {
    });
  }


  @BeforeEach
  void setUp() {

    inputField = new InputField(100, 100);
  }

  @Test
  @DisplayName("Should have valid input")
  void shouldHaveValidInput() {
    inputField.getTextArea().setText("Hello");
    assertTrue(inputField.hasValidInput());
  }

  @Test
  @DisplayName("Should have invalid integer status")
  void shouldHaveInvalidIntegerStatus() {
    inputField.getTextArea().setText("Hello");
    inputField.setShouldBePositiveInteger(true);
    assertFalse(inputField.hasValidInput());
  }

  @Test
  @DisplayName("Should have valid integer status")
  void shouldHaveValidIntegerStatus() {
    inputField.getTextArea().setText("1");
    inputField.setShouldBePositiveInteger(true);
    assertTrue(inputField.hasValidInput());
  }

  @Test
  @DisplayName("Should have valid empty status")
  void shouldHaveValidEmptyStatus() {
    inputField.getTextArea().setText("");
    inputField.setShouldHaveText(false);
    assertTrue(inputField.hasValidInput());
  }

  @Test
  @DisplayName("Should have invalid empty status")
  void shouldHaveInvalidEmptyStatus() {
    inputField.getTextArea().setText("");
    inputField.setShouldHaveText(true);
    assertFalse(inputField.hasValidInput());
  }

  @Test
  @DisplayName("Empty text and non-integer input should return false")
  void shouldGetInvalidInputGivenInvalidInputs() {
    inputField.getTextArea().setText("");
    inputField.setShouldHaveText(true);
    inputField.setShouldBePositiveInteger(true);
    assertFalse(inputField.hasValidInput());
  }

  @Test
  @DisplayName("Filled text and integer input should return true")
  void shouldGetValidInputGivenValidInputs() {
    inputField.getTextArea().setText("12");
    inputField.setShouldHaveText(true);
    inputField.setShouldBePositiveInteger(true);
    assertTrue(inputField.hasValidInput());
  }


}