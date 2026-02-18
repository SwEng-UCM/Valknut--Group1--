package combat;

public enum CombatOption {
    WAIT, ATTACK, DEFEND, USE_ITEM, RUN;

    public static CombatOption parseCommand(String command) {
        if (command == null) {
            return ATTACK; 
        }
        switch (command.toLowerCase()) {
            case "attack":
                return ATTACK;
            case "defend":
                return DEFEND;
            case "use_item":
            case "item":
                return USE_ITEM;
            case "run":
                return RUN;
            default:
                return WAIT;
        }
    }

    public void print(){
        for (CombatOption op : CombatOption.values()) {
            if(op != WAIT)
                System.out.print(op.name().toUpperCase() + "   ");
        }
        System.out.println();
    }
}
