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
                File archivo = new File(path);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivo);
                
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                
                clip.start();
                
            } catch (Exception e) {
                System.err.println("Error al cargar la música: " + e.getMessage());
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
            System.out.println("Error al reproducir el sonido: " + e.getMessage());
        }
    }

        public void ajustarVolumen(float db) {
            // Aquí podrías acceder directamente a componentes de la clase externa
        }
    }
