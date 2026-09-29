import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {
    private static Clip bgmClip;

    public static void playBGM(String filepath) {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }
            
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioInput);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY); 
                bgmClip.start();
            } else {
                System.out.println("Cannot find audio file: " + filepath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playSFX(String filepath) {
        try {
            File soundPath = new File(filepath);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip sfxClip = AudioSystem.getClip();
                sfxClip.open(audioInput);
                sfxClip.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}