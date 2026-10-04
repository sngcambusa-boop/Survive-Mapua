import javax.sound.sampled.*;
import java.io.File;
import java.io.InputStream;

public class AudioPlayer {
    private static Clip bgmClip;

    public static void playBGM(String filepath) {
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }
            
            AudioInputStream audioInput = openAudioStream(filepath);
            if (audioInput != null) {
                bgmClip = AudioSystem.getClip();
                try (audioInput) {
                    bgmClip.open(audioInput);
                }
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
            AudioInputStream audioInput = openAudioStream(filepath);
            if (audioInput != null) {
                Clip sfxClip = AudioSystem.getClip();
                try (audioInput) {
                    sfxClip.open(audioInput);
                }
                sfxClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        sfxClip.close();
                    }
                });
                sfxClip.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static AudioInputStream openAudioStream(String filepath) throws Exception {
        File audioFile = new File(filepath);
        if (audioFile.isFile()) {
            return AudioSystem.getAudioInputStream(audioFile);
        }

        String resourcePath = filepath.startsWith("/") ? filepath : "/" + filepath;
        InputStream resource = AudioPlayer.class.getResourceAsStream(resourcePath);
        return resource == null ? null : AudioSystem.getAudioInputStream(resource);
    }
}