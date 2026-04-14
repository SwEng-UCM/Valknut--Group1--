package me.view;

import java.io.File;
import javax.sound.sampled.*;

class AudioManager {
    public Clip music;
    private static AudioManager am_instance;
    private float currentVolumeDB = 0.0f;

    public static AudioManager getInstance(){
        if(am_instance == null)
            am_instance = new AudioManager();
        return am_instance;
    }

    public void playMusic(String path) {
        stopMusic();
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
            music = AudioSystem.getClip();
            music.open(audioStream);

            applyVolume();

            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();
            
        } catch (Exception e) {
            System.err.println("Music play error: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (music != null && music.isRunning()) {
            music.stop();
            music.close();
        }
    }

    public void sound(String audio) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
            Clip sound = AudioSystem.getClip();
            sound.open(audioInputStream);
            sound.start();
        } catch (Exception e) {
            System.out.println("Sound play error: " + e.getMessage());
        }
    }

    public void setVolume(int percentage) {
            if (percentage <= 0)
                currentVolumeDB = -80.0f;
            else
                currentVolumeDB = (float) (Math.log10(percentage / 100.0) * 20.0);
            
            applyVolume();
    }

    private void applyVolume() {
        if (music != null && music.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) music.getControl(FloatControl.Type.VOLUME);
            float val = Math.max(-80.0f, Math.min(currentVolumeDB, 6.0f));
            gain.setValue(val);
        }
    }
}
