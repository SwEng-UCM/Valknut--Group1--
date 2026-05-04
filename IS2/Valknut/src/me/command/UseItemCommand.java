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
        previousState = combat.save();

        if (currentHero == null || !currentHero.isAlive() || currentHero.escaped() || combat.getEnemies().isEmpty() || item == null) {
            previousState = null;
            return false;
        }

        sb.append(combat.useItem(currentHero, item));

        // Original behavior: item usage does not advance the turn.
        return false;
    }

    @Override
    public boolean undo() {
        if (previousState == null) {
            return false;
        }

        combat.restore(previousState);
        combatView.printLine("Last combat action was undone.");
        return true;
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean advancesTurn() {
        return false;
    }
}