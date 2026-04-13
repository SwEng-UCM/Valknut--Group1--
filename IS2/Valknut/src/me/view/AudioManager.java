package me.view;

import java.io.File;
import javax.sound.sampled.*;

class AudioManager {
        private Clip clip;
        private static AudioManager am_instance;

        private AudioManager(){}

        public static AudioManager getInstance(){
            if(am_instance == null)
                am_instance = new AudioManager();
            return am_instance;
        }

        public void playMusic(String path) {
            try {
                File file = new File(path);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                
                clip.start();
                
            } catch (Exception e) {
                System.err.println("Music play error: " + e.getMessage());
            }
        }

        public void stopMusic() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        }

        public void sound(String audio) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound play error: " + e.getMessage());
        }
    }

        public void setVolumen(float db) {
           
        }
    }
