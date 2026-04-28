package me.socket;

import me.control.*;
import me.model.CombatOption;
import me.model.items.Item;

public class Dispatcher {

    Controller ctrl;

    public Dispatcher(Controller ctrl){
        this.ctrl = ctrl;
    }
    
    public void dispatch(Request rq, MultiplayerManager test){
        Request.RequestType rt = rq.getRT();
        switch(rt){
            case MESSAGE ->{dispatchMessage(rq, test);}
            case CLOSING ->{dispatchClose(rq, test);}
            case COMBATOPTION -> {dispatchCO(rq);}
            default ->{}
        }
    }

    public void dispatchMessage(Request rq, MultiplayerManager test){
        String message = (String) rq.getParameters()[0];
        if(test.getUser().getId() == rq.getId())
            message = "You: " + message;
        else if(rq.getId() == 1)
            message = "Server: " + message;
        else
            message = "Client: " + message;
        test.write(message);
    }

    public void dispatchClose(Request rq, MultiplayerManager test){
        if(test.getUser().getId() == rq.getId()){}
        else
            test.killUser();
    }

    public void dispatchCO(Request rq){
        CombatOption co = (CombatOption) rq.getParameters()[0];
        switch (co) {
            case ATTACK -> {ctrl.action(co, (int) rq.getParameters()[1], null);}
            case DEFEND -> {ctrl.action(co, 0, null);}
            case RUN -> {ctrl.action(co, 0, null);}
            case USE_ITEM -> {ctrl.action(co, 0, (Item) rq.getParameters()[1]);}
            case STATS -> {ctrl.action(co, (int) 0, null);}
            default -> {}
        }
    }
}
