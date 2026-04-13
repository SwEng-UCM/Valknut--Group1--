package me.model.save;

import java.io.*;

public class SaveGameManager {

    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(SaveGameData data) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {

            out.writeObject(data);
            System.out.println("Game saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveGameData loadGame() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(SAVE_FILE))) {

            return (SaveGameData) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No save file found.");
            return null;
        }
    }
}