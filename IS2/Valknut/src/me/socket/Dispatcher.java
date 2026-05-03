package me.socket;

import me.control.*;
import me.model.CombatOption;
import me.model.Game;
import me.model.HeroEnum;
import me.model.items.Item;

public class Dispatcher {

    Controller ctrl;
    Game game;

    public Dispatcher(Controller ctrl, Game game){
        this.ctrl = ctrl;
        this.game = game;
    }
    
    public void dispatch(Request rq, MultiplayerManager test){
        Request.RequestType rt = rq.getRT();
        switch(rt){
            case MESSAGE ->{dispatchMessage(rq, test);}
            case CLOSING ->{dispatchClose(rq, test);}
            case CHARACTERSELECT ->{dispatchCS(rq, test);}
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

    public void dispatchCO(Request rq, MultiplayerManager test){
        Object[] par = rq.getParameters();
        CombatOption co = (CombatOption) par[0];
        switch (co) {
            case ATTACK -> {game.action(co, (int) par[1], null);}
            case DEFEND -> {game.action(co, 0, null);}
            case RUN -> {game.action(co, 0, null);}
            case USE_ITEM -> {game.action(co, 0, (Item) par[1]);}
            case STATS -> {game.action(co, (int) 0, null);}
            default -> {}
        }
        test.refreshCombatScreen();
    }
}
