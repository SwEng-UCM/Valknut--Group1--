package combat;

public enum Attribute {
    SPEED, STRENGTH, RESISTANCE, CLEVERNESS, AGILITY;

    public static Attribute parseCommand(String command) {
        if (command == null) {
            return SPEED; 
        }
        switch (command.toLowerCase()) {
            case "speed":
                return SPEED;
            case "strength":
                return STRENGTH;
            case "resistance":
                return RESISTANCE;
            case "cleverness":
                return CLEVERNESS;
            case "agility":
                return AGILITY;
            default:
                return SPEED;
        }
    }
}

