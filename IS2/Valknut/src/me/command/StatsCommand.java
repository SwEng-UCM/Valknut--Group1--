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

/**
 * Command responsible for showing the current hero stats.
 */
public class StatsCommand implements Command {

    private final Combat combat;
    private final Hero currentHero;

    public StatsCommand(Combat combat, Hero currentHero) {
        this.combat = combat;
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