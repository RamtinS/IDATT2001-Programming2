package edu.ntnu.idatt2001.paths.filehandling;

/**
 * The FilePathValidator class is used to validate the path of a file.
 *
 * @author Ramtin Samavat
 * @author Tobias Oftedal
 * @version 1.0
 * @since April 23, 2023.
 */
public class FilePathValidator {

  /**
   * Private constructor for the FilePathValidator.
   *
   * @throws IllegalStateException If the constructor is used.
   */
  private FilePathValidator() throws IllegalStateException {
    throw new IllegalStateException("Cannot instantiate a FilePathValidator object");
  }

  /**
   * The method validates the given path of the file.
   *
   * @param pathOfFile    the path of the file to read/write to.
   * @param fileExtension the file extension that the path should end with.
   * @throws NullPointerException     if the path of the file or the file extension is null.
   * @throws IllegalArgumentException if path of the file is blank or does not end with the file
   *                                  extension.
   */
  public static void validatePathOfFile(String pathOfFile, String fileExtension)
      throws NullPointerException, IllegalArgumentException {
    if (fileExtension == null) {
      throw new NullPointerException("The file extension cannot be null.");
    }
    if (pathOfFile == null) {
      throw new NullPointerException("The path of the file cannot be null.");
    }
    if (pathOfFile.isBlank()) {
      throw new IllegalArgumentException("The path to the file cannot be blank.");
    }
    if (!pathOfFile.toLowerCase().trim().endsWith(fileExtension)) {
      throw new IllegalArgumentException("The path to the file must end with " + fileExtension);
    }
  }
}
