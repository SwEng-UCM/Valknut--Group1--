package me.command;

import me.model.Combat;
import me.model.Hero;
import me.model.items.Item;
import me.view.CombatView;

/**
 * Command responsible for handling the item usage action.
 */
public class UseItemCommand implements Command {

    private final Combat combat;
    private final CombatView combatView;
    private final Hero currentHero;
    private final Item item;

    public UseItemCommand(Combat combat, CombatView combatView, Hero currentHero, Item item) {
        this.combat = combat;
        this.combatView = combatView;
        this.currentHero = currentHero;
        this.item = item;
    }

    @Override
    public boolean execute() {
        if (currentHero == null || !currentHero.isAlive() || currentHero.escaped() || combat.getEnemies().isEmpty()) {
            return false;
        }

        combat.useItem(currentHero, item);

        // The original controller did not mark item usage as a finished turn.
        return false;
    }

    @Override
    public boolean advancesTurn() {
        return false;
    }
}