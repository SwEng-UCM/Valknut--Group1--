package me.command;

import me.model.AutonomousHero;
import me.model.Combat;
import me.model.Hero;
import me.view.CombatView;

/**
 * Command responsible for handling the attack action.
 */
public class AttackCommand implements Command {

    private final Combat combat;
    private final CombatView combatView;
    private final Hero currentHero;
    private int target;
    private boolean actionExecuted;

    public AttackCommand(Combat combat, CombatView combatView, Hero currentHero, int target) {
        this.combat = combat;
        this.combatView = combatView;
        this.currentHero = currentHero;
        this.target = target;
        this.actionExecuted = false;
    }

    @Override
    public boolean execute() {
        if (currentHero == null || !currentHero.isAlive() || currentHero.escaped() || combat.getEnemies().isEmpty()) {
            return false;
        }

        // If the current hero is autonomous, let the hero choose the target.
        if (currentHero.isAutonomous()) {
            AutonomousHero autonomousHero = (AutonomousHero) currentHero;
            target = autonomousHero.selectTarjet() + 1;

            // The old code only attacked when the selected target was valid.
            if (target <= 0) {
                return false;
            }
        }

        combatView.printLine(combat.attack(target));
        actionExecuted = true;
        return true;
    }

    @Override
    public boolean advancesTurn() {
        return actionExecuted;
    }
}