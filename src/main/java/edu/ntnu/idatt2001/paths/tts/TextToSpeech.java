package edu.ntnu.idatt2001.paths.tts;

import com.sun.speech.freetts.VoiceManager;
import de.dfki.lt.freetts.en.us.MbrolaVoiceDirectory;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

/**
 * Singleton class for handling TTS (text to speech) requests.
 */
public class TextToSpeech {

  private static Synthesizer synth;
  private static SynthesizerModeDesc desc;
  private static Voice[] voices;
  private static Voice voice;
  private static TextToSpeech instance;
  private static final Logger LOGGER = Logger.getLogger(TextToSpeech.class.getName());

  /**
   * If no instance of the class already exists, an instance will be made and returned. If it does
   * already exist, the already existing instance will be returned.
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
   * Creates a TextToSpeech object.
   * <li>Sets system properties to enable voices</li>
   * <li>Sets the current voice to "kevin16"</li>
   * <li>Creates a {@link Synthesizer object} used to emulate a real person's voice</li>
   */
  private TextToSpeech() {
    try {
      System.setProperty("FreeTTSSynthEngineCentral",
          "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
      System.setProperty("freetts.voices",
          "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
      Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

      desc = new SynthesizerModeDesc(null, "general", Locale.ENGLISH, null, null);
      synth = Central.createSynthesizer(desc);
      synth.allocate();
      desc = (SynthesizerModeDesc) synth.getEngineModeDesc();

      voices = desc.getVoices();

      TextToSpeech.voice = new Voice("Geir", Voice.GENDER_FEMALE, Voice.AGE_CHILD, "casual");
      System.out.println(voice);
      synth.getSynthesizerProperties().setVoice(voice);
      synth.resume();
    } catch (Exception e) {
      LOGGER.log(Level.INFO,
          "Error while initiating text to speak class because: " + e.getMessage());
    }
  }

  /**
   * Plays a sound simulating the input text as said by a voice using a {@link Synthesizer}.
   *
   * @param text The text used to be played out loud.
   */
  public void speech(String text) {
    try {
      synth.cancel();
      synth.speakPlainText(text, null);
    } catch (Exception e) {
      LOGGER.log(Level.INFO, "Error while speaking text because: " + e.getMessage());
    }

  }




}
