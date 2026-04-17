package me.command;

import me.model.Combat;
import me.model.Hero;
import me.view.CombatView;

/**
 * Command responsible for handling the run action.
 */
public class RunCommand implements Command {

    private final Combat combat;
    private final CombatView combatView;
    private final Hero currentHero;
    private boolean actionExecuted;

    public RunCommand(Combat combat, CombatView combatView, Hero currentHero) {
        this.combat = combat;
        this.combatView = combatView;
        this.currentHero = currentHero;
        this.actionExecuted = false;
    }

    @Override
    public boolean execute() {
        if (currentHero == null || !currentHero.isAlive() || currentHero.escaped() || combat.getEnemies().isEmpty()) {
            return false;
        }

        combatView.printLine(combat.run());
        actionExecuted = true;
        return true;
    }

    @Override
    public boolean advancesTurn() {
        return actionExecuted;
    }
}