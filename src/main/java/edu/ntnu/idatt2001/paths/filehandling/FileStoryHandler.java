package edu.ntnu.idatt2001.paths.filehandling;

import edu.ntnu.idatt2001.paths.Link;
import edu.ntnu.idatt2001.paths.Passage;
import edu.ntnu.idatt2001.paths.Story;
import edu.ntnu.idatt2001.paths.actions.Action;
import edu.ntnu.idatt2001.paths.actions.GoldAction;
import edu.ntnu.idatt2001.paths.actions.HealthAction;
import edu.ntnu.idatt2001.paths.actions.InventoryAction;
import edu.ntnu.idatt2001.paths.actions.ScoreAction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The FileStoryHandler class provides methods to write and read a story
 * object to/from a text file.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since April 23, 2023.
 */
public class FileStoryHandler {
  private static final Logger logger = Logger.getLogger(FileStoryHandler.class.getName());
  private static final String FILE_EXTENSION = ".paths";

  /**
   * The method writes a story object to a text file.
   *
   * @param story the story object to be written.
   * @param pathOfFile the path of the file to write the story to.
   * @throws NullPointerException if the story or pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error writing story to file.
   */
  public static void writeStoryToFile(Story story, String pathOfFile)
          throws NullPointerException, IllegalArgumentException, IOException {
    if (story == null) {
      throw new NullPointerException("The story cannot be null.");
    }
    FilePathValidator.validatePathOfFile(pathOfFile, FILE_EXTENSION);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathOfFile.toLowerCase().trim()))) {
      writer.write(story.getTitle() + "\n\n");
      writer.write("::" + story.getOpeningPassage().getTitle() + "\n");
      writer.write(story.getOpeningPassage().getContent() + "\n");
      for (Link link : story.getOpeningPassage().getLinks()) {
        writeLinkWithActions(writer, link);
      }
      for (Passage passage : story.getPassages()) {
        writer.write("\n::" + passage.getTitle() + "\n");
        writer.write(passage.getContent() + "\n");
        for (Link link : passage.getLinks()) {
          writeLinkWithActions(writer, link);
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error writing story to file.", e);
      throw new IOException("Error writing story to file: " + e.getMessage());
    }
  }

  /**
   * Helper method to write the given Link object with its associated actions.
   *
   * @param writer the BufferedWriter object to write to.
   * @param link the Link object to write.
   * @throws IOException if there is an error writing to the BufferedWriter.
   */
  private static void writeLinkWithActions(BufferedWriter writer, Link link) throws IOException {
    writer.write("[" + link.getText() + "]" + "(" + link.getReference() + ")");
    for (Action action : link.getActions()) {
      writer.write(action.toString());
    }
    writer.write("\n");
  }

  /**
   * The method reads a story object from a text file.
   *
   * @param pathOfFile the path of the file to read the story from.
   * @return the story object read from the file.
   * @throws NullPointerException if the pathOfFile is null.
   * @throws IllegalArgumentException if pathOfFile is blank or does not end with FILE_EXTENSION.
   * @throws IOException if there is an error reading story from file.
   */
  public static Story readStoryFromFile(String pathOfFile)
          throws NullPointerException, IllegalArgumentException, IOException {
    FilePathValidator.validatePathOfFile(pathOfFile, FILE_EXTENSION);

    Story story;
    try (BufferedReader reader = new BufferedReader(new FileReader(pathOfFile.toLowerCase().trim()))) {
      String storyTitle = reader.readLine();
      List<Passage> passages = readPassagesFromFile(reader);
      Passage openingPassage = passages.get(0);
      story = new Story(storyTitle, openingPassage);
      for (int i = 1; i < passages.size(); i++) {
        story.addPassage(passages.get(i));
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error reading story from file.", e);
      throw new IOException("Error reading story to file: " + e.getMessage());
    }
    return story;
  }

  /**
   * Helper method to read passages.
   *
   * @param reader the BufferedReader object to read from.
   * @return a list of Passage objects read from the BufferedReader.
   * @throws IOException if there is an error reading from the BufferedReader.
   * @throws IllegalArgumentException if the format of the file is incorrect.
   */
  private static List<Passage> readPassagesFromFile(BufferedReader reader)
          throws IOException, IllegalArgumentException {
    List<Passage> passages = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("::")) {
        Passage passage = parsePassage(line, reader.readLine());
        while ((line = reader.readLine()) != null && line.contains("[")) {
          Link link = parseLink(line);
          passage.addLink(link);
        }
        passages.add(passage);
      }
    }
    return passages;
  }

  /**
   * Helper method to parse a passage.
   *
   * @param titleLine the title string.
   * @param contentLine the content string.
   * @return the Passage object created from the title and content string.
   */
  private static Passage parsePassage(String titleLine, String contentLine) {
    String title = titleLine.substring(2).trim();
    String content = contentLine.trim();
    return new Passage(title, content);
  }

  /**
   * Helper method to parse a link.
   *
   * @param line the string to parse.
   * @return a link object created from the string.
   * @throws IllegalArgumentException if the action does not exist.
   */
  private static Link parseLink(String line) throws IllegalArgumentException {
    String[] linkParts = line.split("[])}]");
    String linkText = linkParts[0].substring(1).trim();
    String linkReference = linkParts[1].substring(1).trim();
    Link link = new Link(linkText, linkReference);
    parseLinkActions(link, linkParts);
    return link;
  }

  /**
   * Helper method to parse actions from a link string and add them to a Link object.
   *
   * @param link the link object to add actions to.
   * @param linkParts array of strings representing the link and its actions.
   * @throws IllegalStateException if the action does not exist.
   */
  private static void parseLinkActions(Link link, String[] linkParts)
          throws IllegalArgumentException {
    for (String linkPart : linkParts) {
      if (linkPart.startsWith("{")) {
        String[] actionParts = linkPart.split(":");
        String actionDescription = actionParts[0].substring(1).toLowerCase().trim();
        String actionValue = actionParts[1].trim();
        Action action;
        switch (actionDescription) {
          case "gold" -> action = new GoldAction(Integer.parseInt(actionValue));
          case "health" -> action = new HealthAction(Integer.parseInt(actionValue));
          case "inventory" -> action = new InventoryAction(actionValue);
          case "score" -> action = new ScoreAction(Integer.parseInt(actionValue));
          default -> throw new IllegalArgumentException("Invalid action type: " + actionDescription);
        }
        link.addAction(action);
      }
    }
  }
}
