package me.command;

import me.model.Combat;
import me.model.CombatOption;
import me.model.Hero;
import me.model.items.Item;
import me.view.CombatView;

/**
 * Factory responsible for creating the correct command object.
 */
public class CommandFactory {

    private CommandFactory() {
        // Prevent instantiation of utility factory class.
    }

    /**
     * Creates a command based on the selected combat option.
     *
     * @param combat the current combat object
     * @param combatView the combat view used for printing and user interaction
     * @param currentHero the hero whose turn is currently active
     * @param option the selected combat option
     * @param target the selected target index
     * @return a command instance or null if the option is not supported
     */
    public static Command createCommand(
            Combat combat,
            Hero currentHero,
            CombatOption option,
            int target,
            Item item,
            Command lastCommand
    ) {
        if (option == null) {
            return null;
        }

        return switch (option) {
            case ATTACK -> new AttackCommand(combat, currentHero, target);
            case DEFEND -> new DefendCommand(combat, currentHero);
            case USE_ITEM -> new UseItemCommand(combat, currentHero, item);
            case RUN -> new RunCommand(combat, currentHero);
            case STATS -> new StatsCommand(combat, currentHero);
            case UNDO -> new UndoCommand(combat, lastCommand);
            default -> null;
        };
    }
}