package game;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Die Klasse Musik speichert sound-Clips in einer HashMap.
 * 
 * @author Aldo Costa (Kappa)
 */
public class Musik
{
    // Attribute
    private static HashMap<String, Clip> geraeusche = new HashMap<String, Clip>();;

    /**
     * Konstruktor der Klasse Musik
     */
    public Musik(){}

    /**
     * Fuegt einen Sound-Clip der HashMap hinzu.
     * 
     * @param pName Name des Sound-Clips
     * @param pPfadZurDatei Pfad zur Sounddatei
     */
    public static void geraeuschHinzufuegen(String pName, String pPfadZurDatei)
    {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            File lDatei = new File(pPfadZurDatei);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(lDatei);
            // Get a clip resource.
            Clip ausschnitt = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            ausschnitt.open(audioInputStream);
            // Add to Map
            geraeusche.put(pName, ausschnitt);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spielt einen Sound-Clip aus der HashMap ab.
     * 
     * param pName Name des Sound-Clips
     */
    public static void geraeuschAbspielen(String pName)
    {
        Clip ausschnitt = geraeusche.get(pName);
        if (ausschnitt.isRunning())
            ausschnitt.stop();
        ausschnitt.setFramePosition(0);
        ausschnitt.start();
    }
    
    public static Clip getClip(String pName)
    {
        return geraeusche.get(pName);
    }
    public static boolean isRunning(String pName)
    {
        Clip ausschnitt = geraeusche.get(pName);
        if (ausschnitt.isRunning())
            return true;
        else
            return false;
    }

    public static float getVolume(Clip clip) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public static void setVolume(float volume,Clip clip) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public static void geraeuschStoppen(String pName) {
        Clip ausschnitt = geraeusche.get(pName);
        if (ausschnitt.isRunning())
            ausschnitt.stop();
    }
}
