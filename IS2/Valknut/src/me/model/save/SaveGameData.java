package me.model.save;

import java.io.Serializable;
import me.model.Combat;

public class SaveGameData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Combat combat;

    public SaveGameData(Combat combat) {
        this.combat = combat;
    }

    public Combat getCombat() {
        return combat;
    }
}