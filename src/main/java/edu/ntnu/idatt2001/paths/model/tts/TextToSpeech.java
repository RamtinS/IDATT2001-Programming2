package edu.ntnu.idatt2001.paths.model.tts;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.speech.Central;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

/**
 * Singleton class for handling TTS (text to speech) requests. The class contains methods for
 * speaking text objects out loud using synthetic English voice using an emulator.
 *
 * @author Ramtin Samavat and Tobias Oftedal.
 * @version 1.0
 * @since May 19, 2023.
 */
public class TextToSpeech {

  private static final Logger logger = Logger.getLogger(TextToSpeech.class.getName());
  private static TextToSpeech instance = null;
  private Synthesizer synthesizer;
  private boolean speechEnabled;

  /**
   * Creates a TextToSpeechObject. If no instance of the class already exists, an instance will be
   * made and returned. If it does already exist, the already existing instance will be returned.
   *
   * @return The only instance of the {@link TextToSpeech} class
   */
  public static TextToSpeech getInstance() {
    if (instance == null) {
      instance = new TextToSpeech();
    }
    return instance;
  }

  /**
   * Constructor for a TextToSpeech object.
   * <li>Sets system properties to enable voices</li>
   * <li>Sets the current voice to "kevin16"</li>
   * <li>Creates a {@link Synthesizer object} used to emulate a real person's voice</li>
   */
  private TextToSpeech() {
    speechEnabled = false;
    try {
      System.setProperty("FreeTTSSynthEngineCentral",
          "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
      System.setProperty("freetts.voices",
          "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
      Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

      SynthesizerModeDesc synthesizerModeDesc = new SynthesizerModeDesc(null, "general",
          Locale.ENGLISH, null, null);
      synthesizer = Central.createSynthesizer(synthesizerModeDesc);
      synthesizer.allocate();

      Voice voice = new Voice("Geir", Voice.GENDER_FEMALE, Voice.AGE_CHILD, "casual");
      synthesizer.getSynthesizerProperties().setVoice(voice);
      synthesizer.resume();
    } catch (Exception e) {
      logger.log(Level.WARNING,
          String.format("Error while initiating text to speak class because %s", e.getMessage()),
          e);
    }
  }

  /**
   * Sets the speech enabled or disabled.
   *
   * @param enabled true to enable speech, false to disable speech
   */
  public void setSpeechEnabled(boolean enabled) {
    this.speechEnabled = enabled;
  }

  /**
   * Checks if speech is enabled.
   *
   * @return true if speech is enabled, false otherwise
   */
  public boolean isSpeechEnabled() {
    return speechEnabled;
  }

  /**
   * Plays a sound simulating the input text as said by a voice using a {@link Synthesizer}.
   *
   * @param text The text used to be played out loud.
   */
  public void speech(String text) {
    if (!speechEnabled) {
      return;
    }
    resetSpeech();
    try {
      synthesizer.speakPlainText(text, null);
    } catch (EngineStateError | NullPointerException e) {
      logger.log(Level.WARNING,
          String.format("Error while speaking text because %s", e.getMessage()), e);
    }
  }

  /**
   * Clears the queue of text to speech requests for the {@link Synthesizer}.
   */
  public void resetSpeech() {
    try {
      synthesizer.cancel();
    } catch (EngineStateError | NullPointerException e) {
      logger.log(Level.WARNING,
          String.format("Error while resetting text because %s", e.getMessage()), e);
    }
  }
}
