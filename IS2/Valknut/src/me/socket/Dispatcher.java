package me.socket;

import me.control.*;
import me.model.CombatOption;
import me.model.Game;
import me.model.HeroEnum;
import me.model.items.Item;

public class Dispatcher {

    Controller ctrl;
    Game game;

    public Dispatcher(Controller ctrl){
        this.ctrl = ctrl;
    }
    
    public void dispatch(Request rq, MultiplayerManager test){
        Request.RequestType rt = rq.getRT();
        switch(rt){
            case MESSAGE ->{dispatchMessage(rq, test);}
            case CLOSING ->{dispatchClose(rq, test);}
            case CHARACTERSELECT ->{dispatchCS(rq, test);}
            case STORYADVANCED ->{dispatchSA(rq, test);}
            case COMBATOPTION -> {dispatchCO(rq, test);}
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

    public void dispatchCS(Request rq, MultiplayerManager test){
        if(rq.getParameters()[0] == null) // If it's an action from character selection with no params, it's start
            ctrl.startSelectedGame();
        else //If has params, it's a choosing action
            test.chooseCharacter((HeroEnum) rq.getParameters()[0]);
    }

    public void dispatchSA(Request rq, MultiplayerManager test){
        if(!(test.getUser().getId() == 2 && rq.getId() == 2)){
            int opt = (int) rq.getParameters()[0];
            switch (opt) {
                case 0 -> {
                    ctrl.next();
                }
                case 1 -> {
                    ctrl.exit();
                }
                default -> System.exit(0);
            }
        }

    }

    public void dispatchCO(Request rq, MultiplayerManager test){
        if(!(test.getUser().getId() == 2 && rq.getId() == 2)){
            Object[] par = rq.getParameters();
            CombatOption co = (CombatOption) par[0];
            test.initCS();
            if(co == null){
                test.continueCombat();
            }
            else{
                switch (co) {
                    case ATTACK -> {test.attackAction((int) par[1]);}
                    case DEFEND -> {
                        ctrl.action(co, 0, null);
                        test.consumTextLog();
                    }
                    case RUN -> {
                        ctrl.action(co, 0, null);
                        test.consumTextLog();
                    }
                    case USE_ITEM -> {
                        ctrl.action(co, 0, (Item) par[1]);
                        test.consumTextLog();
                    }
                    case STATS -> {
                        ctrl.action(co, (int) 0, null);
                        test.consumTextLog();
                    }
                    default -> {}
                }
            }
        }
    }
}
