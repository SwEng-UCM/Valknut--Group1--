/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model;

//These are phisical related stats of characters
public enum Attribute {
    SPEED, STRENGTH, RESISTANCE, CLEVERNESS, AGILITY;

    public static Attribute parseCommand(String command) {
        if (command == null) {
            return SPEED; 
        }
        return switch (command.toLowerCase()) {
            case "speed" -> SPEED;
            case "strength" -> STRENGTH;
            case "resistance" -> RESISTANCE;
            case "cleverness" -> CLEVERNESS;
            case "agility" -> AGILITY;
            default -> SPEED;
        };
    }
}

