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
import me.model.items.Item;
import me.model.save.SaveGameData;
import me.view.CombatView;

/**
 * Command responsible for handling the item usage action.
 */
public class UseItemCommand implements Command {

    private final Combat combat;
    private final CombatView combatView;
    private final Hero currentHero;
    private final Item item;

    private SaveGameData previousState;

    public UseItemCommand(Combat combat, CombatView combatView, Hero currentHero, Item item) {
        this.combat = combat;
        this.combatView = combatView;
        this.currentHero = currentHero;
        this.item = item;
    }

    @Override
    public boolean execute(StringBuilder sb) {
    	if (!combat.multiPlayer()) previousState = combat.save();

        if (currentHero == null || !currentHero.isAlive() || currentHero.escaped() || combat.getEnemies().isEmpty() || item == null) {
            previousState = null;
            return false;
        }

        sb.append(combat.useItem(currentHero, item));

        return false;
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
        return false;
    }
}