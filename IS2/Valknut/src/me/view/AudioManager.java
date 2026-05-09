/**
 * 
 * @author Helio Vega Fernández
 * AI assited
 * 
 */
package me.view;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.*;

public class AudioManager {
    public Clip music;
    private static AudioManager am_instance;
    private float currentVolumeDB = 0.0f;

    public static AudioManager getInstance(){
        if(am_instance == null)
            am_instance = new AudioManager();
        return am_instance;
    }

    public void playMusic(URL url) {
        stopMusic();
        try {
            InputStream is = url.openStream();
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            music = AudioSystem.getClip();
            music.open(audioStream);
            applyVolume();
            music.loop(Clip.LOOP_CONTINUOUSLY);
            music.start();
            
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Music play error: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (music != null && music.isRunning()) {
            music.stop();
            music.close();
        }
    }

    public void sound(URL url) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip sound = AudioSystem.getClip();
            sound.open(audioInputStream);
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("Sound play error: " + e.getMessage());
        }
    }

    public void setVolume(int percentage) {
        if (music == null) {
            System.out.println("Null music");
            return; 
        }
        if (percentage <= 0)
            currentVolumeDB = -80.0f;
        else
            currentVolumeDB = (float) (Math.log10(percentage / 100.0) * 20.0);
        
        applyVolume();
    }

    private void applyVolume() {
        try{
            if (music != null && music.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gain = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
                float val = Math.max(-80.0f, Math.min(currentVolumeDB, 6.0f));
                gain.setValue(val);
            }
            else {
                System.out.println("Control gain not supported");
            }
        }catch(Exception e){
            System.err.println("Error when setting volume " + e.getMessage());   
        }
    }
}
