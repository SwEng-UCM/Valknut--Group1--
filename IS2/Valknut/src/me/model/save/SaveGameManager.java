package me.model.save;

import java.io.*;

public class SaveGameManager {

    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(SaveGameData data) {
        saveGame(data, SAVE_FILE);
    }

    public static void saveGame(SaveGameData data, String filename) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(filename))) {

            out.writeObject(data);
            System.out.println("Game saved successfully (" + filename + ").");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveGameData loadGame() {
        return loadGame(SAVE_FILE);
    }

    public static SaveGameData loadGame(String filename) {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(filename))) {

            return (SaveGameData) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No save file found: " + filename);
            return null;
        }
    }
}