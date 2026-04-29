package me.model;

import me.view.Messages;

public enum CombatOption {
    ATTACK, DEFEND, USE_ITEM, RUN, STATS, UNDO;

    private CombatOption co;

    public static CombatOption parseCommand(String command) {
        if (command == null) {
            return ATTACK; 
        }
        return switch (command.toLowerCase()) {
            case "attack" -> ATTACK;
            case "defend" -> DEFEND;
            case "use_item", "item" -> USE_ITEM;
            case "run" -> RUN;
            case "stats" -> STATS;
            case "undo" -> UNDO;
            default -> null;
        };
    }

    public static String display(){
        StringBuilder sb = new StringBuilder();
        for (CombatOption op : CombatOption.values())
            sb.append(Messages.NEW_LINE);

        return sb.toString();
    }
}
