package me.command;

import me.model.AutonomousHero;
import me.model.Combat;
import me.model.Enemy;
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
    //private SaveGameData previousState;
    private Enemy attackedEnemy;
    private int previousEnemyLife;

    public AttackCommand(Combat combat, CombatView combatView, Hero currentHero, int target) {
        this.combat = combat;
        this.combatView = combatView;
        this.currentHero = currentHero;
        this.target = target;
        this.actionExecuted = false;
    }

    @Override
    public boolean execute() {
        //previousState = combat.save();

        if (currentHero instanceof AutonomousHero) {
            target = ((AutonomousHero) currentHero).selectTarjet() + 1;
        }

        if (target <= 0) {
            //previousState = null;
            return false;
        }

        attackedEnemy = combat.getEnemies().get(target - 1);
        previousEnemyLife = attackedEnemy.getLife();

        combatView.printLine(combat.attack(target));
        actionExecuted = true;
        return true;
    }

    @Override
    public boolean undo() {
        if (attackedEnemy == null) {
            return false;
        }

        attackedEnemy.setLife(previousEnemyLife);
        combatView.printLine("Last combat action was undone.");
        return true;
    }

    @Override
    public boolean canUndo() {
        return attackedEnemy != null;
    }

    @Override
    public boolean advancesTurn() {
        return actionExecuted;
    }
}