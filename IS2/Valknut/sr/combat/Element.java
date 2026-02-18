package combat;

public enum Element {
	ICE, CHAOS, NATURE, BLOOD, FIRE;
	
	public static Element parseElement(String command) {
        if (command == null) {
        	return ICE;
        }
        switch (command.toLowerCase()) {
            case "ice":
                return ICE;
            case "chaos":
                return CHAOS;
            case "nature":
                return NATURE;
            case "blood":
                return BLOOD;
            case "fire":
                return FIRE;
            default:
                return ICE;
        }
    }

    public String toString(Element e){
        switch (e) {
            case ICE:
                return "ICE";
            case CHAOS:
                return "CHAOS";
            case NATURE:
                return "NATURE";
            case BLOOD:
                return "BLOOD";
            case FIRE:
                return "FIRE";
            default:
                return "ICE";
        }
    }
}
