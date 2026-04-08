package me.model;

public enum Element {
	ICE, CHAOS, NATURE, BLOOD, FIRE;
	
	public static Element parseElement(String command) {
        if (command == null) 
        	return ICE;
        
        return switch (command.toLowerCase()) {
            case "ice" -> ICE;
            case "chaos" -> CHAOS;
            case "nature" -> NATURE;
            case "blood" -> BLOOD;
            case "fire" -> FIRE;
            default -> ICE;
        };
    }

    public String toString(Element e){
            return switch (e) {
                case ICE -> "ICE";
                case CHAOS -> "CHAOS";
                case NATURE -> "NATURE";
                case BLOOD -> "BLOOD";
                case FIRE -> "FIRE";
                default -> "ICE";
            };
    }
    
    public boolean isWeak(Element e) {
            return switch (this) {
                case ICE -> e == FIRE || e == CHAOS;
                case CHAOS -> e == NATURE || e == BLOOD;
                case NATURE -> e == BLOOD || e == FIRE;
                case BLOOD -> e == ICE || e == NATURE;
                case FIRE -> e == CHAOS || e == ICE;
                default -> false;
            };
    }

    public boolean isResistant(Element e) {
            return switch (this) {
                case ICE -> e == BLOOD;
                case CHAOS -> e == ICE;
                case NATURE -> e == CHAOS;
                case BLOOD -> e == FIRE;
                case FIRE -> e == NATURE;
                default -> false;
            };
    }
}
