/**
 * 
 * @author Hana Perocevic
 * 
 * ai-assisted: no
 * 
 */


package me.command;

import me.model.Combat;
import me.model.Hero;
import me.model.save.SaveGameData;

/**
 * Command responsible for handling the defend action.
 */
public class DefendCommand implements Command {

    private final Combat combat;
    private final Hero currentHero;

    private boolean actionExecuted;
    private SaveGameData previousState;

    public DefendCommand(Combat combat, Hero currentHero) {
        this.combat = combat;
        this.currentHero = currentHero;
        this.actionExecuted = false;
    }

    @Override
    public boolean execute(StringBuilder sb) {

        // SAVE STATE for undo
    	if (!combat.multiPlayer()) previousState = combat.save();

        if (currentHero == null || !currentHero.isAlive() || currentHero.escaped() || combat.getEnemies().isEmpty()) {
            previousState = null;
            return false;
        }

        sb.append(combat.defend());
        actionExecuted = true;
        return true;
    }

    @Override
    public boolean undo() {
        if (previousState == null) {
            return false;
        }

        combat.restore(previousState);
        return true;
    }

    @Override
    public boolean canUndo() {
        return previousState != null;
    }

    @Override
    public boolean advancesTurn() {
        return true;
    }
}