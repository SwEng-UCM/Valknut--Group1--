package me.command;

import me.model.AutonomousHero;
import me.model.Combat;
import me.model.Hero;
import me.view.CombatView;

/**
 * Command responsible for handling the defend action.
 */
public class DefendCommand implements Command {

    private final Combat combat;
    private final CombatView combatView;
    private final Hero currentHero;
    private boolean actionExecuted;

    public DefendCommand(Combat combat, CombatView combatView, Hero currentHero) {
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

        // Preserve the old behavior for autonomous heroes.
        if (currentHero.isAutonomous()) {
            AutonomousHero autonomousHero = (AutonomousHero) currentHero;
            autonomousHero.doDefensive();
        }

        combatView.printLine(combat.defend());
        actionExecuted = true;
        return true;
    }

    @Override
    public boolean advancesTurn() {
        return actionExecuted;
    }
}