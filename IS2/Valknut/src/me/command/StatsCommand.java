package me.command;

import me.model.Combat;
import me.model.Hero;
import me.view.CombatView;

/**
 * Command responsible for showing the current hero stats.
 */
public class StatsCommand implements Command {

    private final Combat combat;
    private final CombatView combatView;
    private final Hero currentHero;

    public StatsCommand(Combat combat, CombatView combatView, Hero currentHero) {
        this.combat = combat;
        this.combatView = combatView;
        this.currentHero = currentHero;
    }

    @Override
    public boolean execute(StringBuilder sb) {
        if (currentHero == null) {
            return false;
        }

        sb.append(combat.showStats(currentHero));
        return false;
    }

    @Override
    public boolean advancesTurn() {
        return false;
    }
}