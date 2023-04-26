package edu.ntnu.idatt2001.paths.filehandling;

/**
 * The FilePathValidator class is used to validate the path of a file.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
public class FilePathValidator {

  /**
   * The method validates the given path of the file.
   *
   * @param pathOfFile the path of the file to read/write to.
   * @param FILE_EXTENSION the file extension that the path should end with.
   * @throws NullPointerException if the pathOfFile or FILE_EXTENSION is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   */
  public static void validatePathOfFile(String pathOfFile, String FILE_EXTENSION)
          throws NullPointerException, IllegalArgumentException {
    if (FILE_EXTENSION == null) {
      throw new NullPointerException("The file extension cannot be null.");
    }
    if (pathOfFile == null) {
      throw new NullPointerException("The path of the file cannot be null.");
    }
    if (pathOfFile.isBlank()) {
      throw new IllegalArgumentException("The path to the file cannot be blank.");
    }
    if (!pathOfFile.toLowerCase().trim().endsWith(FILE_EXTENSION)) {
      throw new IllegalArgumentException("The path to the file must end with " + FILE_EXTENSION);
    }
  }
}
