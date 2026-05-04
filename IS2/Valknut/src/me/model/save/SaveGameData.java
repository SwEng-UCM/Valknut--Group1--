package me.model.save;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import me.model.Combat;
import me.model.Game;
import me.model.Storyteller;

public class SaveGameData implements Serializable {

    private static final long serialVersionUID = 1L;

    private final byte[] combatSnapshot;
    private final byte[] storytellerSnapshot;
    private final Game.GameMode mode;
    private final boolean finalBattle;
    private final String combatLog;

    public SaveGameData(Combat combat) {
        this.combatSnapshot = serialize(combat);
        this.storytellerSnapshot = null;
        this.mode = null;
        this.finalBattle = false;
        this.combatLog = "";
    }

    public SaveGameData(Game game) {
        this.combatSnapshot = serialize(game.getCombat());
        this.storytellerSnapshot = serialize(game.getStoryteller());
        this.mode = game.getMode();
        this.finalBattle = game.getFinalBattle();
        this.combatLog = game.getCombatLogSnapshot();
    }

    public Combat getCombat() {
        return deserialize(combatSnapshot, Combat.class);
    }

    public Storyteller getStoryteller() {
        if (storytellerSnapshot == null) {
            return null;
        }

        return deserialize(storytellerSnapshot, Storyteller.class);
    }

    public Game.GameMode getMode() {
        return mode == null ? Game.GameMode.LOCAL : mode;
    }

    public boolean isFinalBattle() {
        return finalBattle;
    }

    public String getCombatLog() {
        return combatLog == null ? "" : combatLog;
    }

    private byte[] serialize(Serializable value) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(value);
            return bytes.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Could not create save memento.", e);
        }
    }

    private <T> T deserialize(byte[] snapshot, Class<T> type) {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(snapshot))) {
            return type.cast(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Could not restore save memento.", e);
        }
    }
}
