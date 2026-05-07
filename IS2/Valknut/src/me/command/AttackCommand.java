/**
 * 
 * @author Hana Perocevic
 * 
 * ai-assisted: yes
 * 
 */



package me.command;

import me.model.Combat;
import me.model.Enemy;
import me.model.Hero;

/**
 * Command responsible for handling the attack action.
 */
public class AttackCommand implements Command {

    private final Combat combat;
    private final Hero currentHero;
    private int target;
    private boolean actionExecuted;
    //private SaveGameData previousState;
    private Enemy attackedEnemy;
    private int previousEnemyLife;

    public AttackCommand(Combat combat,  Hero currentHero, int target) {
        this.combat = combat;
        this.currentHero = currentHero;
        this.target = target;
        this.actionExecuted = false;
    }

    @Override
    public boolean execute(StringBuilder sb) {
        //previousState = combat.save();

        if (target <= 0) {
            //previousState = null;
            return false;
        }

        attackedEnemy = combat.getEnemies().get(target - 1);
        previousEnemyLife = attackedEnemy.getLife();

        sb.append(combat.attack(target));
        actionExecuted = true;
        return true;
    }

    @Override
    public boolean undo() {
        if (attackedEnemy == null) {
            return false;
        }

        attackedEnemy.setLife(previousEnemyLife);
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