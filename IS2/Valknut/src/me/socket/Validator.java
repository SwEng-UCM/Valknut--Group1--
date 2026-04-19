package me.socket;

import me.model.CombatOption;
import me.model.items.Item;

public class Validator {



    public boolean validate(Request rq, int turn){
        switch (rq.getRT()) {
            case COMBAT-> {
                return validateCombatRequest(rq, turn);
            }
            case STORY ->{}
            case ERROR ->{}
            default -> {}
        }
        return false;
    }

    private boolean validateCombatRequest(Request rq, int turn){
        CombatOption co = rq.getCO();
        Object j = rq.getParameters()[0];
        if(((co == CombatOption.ATTACK || co == CombatOption.USE_ITEM) && rq.parameter[0] == null))
            return false;
        if (co == CombatOption.USE_ITEM && !(j instanceof Item)){
            return false;
        }
        if(co == CombatOption.ATTACK){
            try{
                Integer.parseInt((String)j);
            }catch(NumberFormatException nfe){
                return false;
            }
        }
        if(turn != 2){
            return false;
        }
        
        return true;
    }

}
