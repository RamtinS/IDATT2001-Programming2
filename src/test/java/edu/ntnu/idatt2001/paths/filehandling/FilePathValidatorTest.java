package edu.ntnu.idatt2001.paths.filehandling;

import edu.ntnu.idatt2001.paths.model.filehandling.FilePathValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The class tests the FilePathValidator class.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
class FilePathValidatorTest {

  @Test
  @DisplayName("Validate the path of the file")
  void validatePathOfFile() {
    String validPathToFile = "src/test/resources/stories/story.paths";
    String FILE_EXTENSION = ".paths";
    String invalidPathToFileNull = null;
    String invalidFileExtension = null;
    String invalidPathToFileExtension = "src/test/resources/stories/story.txt";
    String invalidPathToFileBlank = "";

    assertDoesNotThrow(() -> FilePathValidator.validatePathOfFile(validPathToFile, FILE_EXTENSION));

    assertThrows(NullPointerException.class,
            () -> FilePathValidator.validatePathOfFile(invalidPathToFileNull, FILE_EXTENSION));

    assertThrows(IllegalArgumentException.class,
            () -> FilePathValidator.validatePathOfFile(invalidPathToFileExtension, FILE_EXTENSION));

    assertThrows(IllegalArgumentException.class,
            () -> FilePathValidator.validatePathOfFile(invalidPathToFileBlank, FILE_EXTENSION));

    assertThrows(NullPointerException.class,
            () -> FilePathValidator.validatePathOfFile(validPathToFile, invalidFileExtension));
  }
}