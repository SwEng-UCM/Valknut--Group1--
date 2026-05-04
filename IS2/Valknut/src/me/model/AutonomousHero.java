package me.model;

import java.util.List;
import me.model.items.HealingItem;
import me.model.items.Item;
import me.socket.Request;

public class AutonomousHero extends Hero {
    
    private State combatState;

    public AutonomousHero(String name, int life, int max_life, String surname, int id){
        super(name, life, max_life, surname, id);
        setAutonomous(true);
        combatState = State.FOLLOWER;
    }

    @Override
    public void does(Request rq){ //Don't use the request but needed for overriding
        System.err.println(combatState);
        switch (combatState) {
            case HARMED -> doHarmed();
            case LEADER -> System.out.println(cmbt.attack(doLeader()));
            case FOLLOWER -> System.out.println(cmbt.attack(doFollower()));
            case DEFENSIVE -> {doDefensive(); cmbt.defend();}
            case SCARED -> {doScared(); cmbt.run();}
            default -> cmbt.attack(doLeader());
        }
    }

    public void doHarmed(){
        Item item = new HealingItem(null, 0, 0, 0, null);
        if(inventory.containsType(item)){
        } else {
            return;
        }
        boolean used = inventory.useFirstOfType(item);

        if(used && getLife() > 50)
            combatState = State.FOLLOWER;
        if(!used){
            double i = Math.random();
            if(i > 0.95)
                combatState = State.LEADER;
            else if(i > 0.85)
                combatState = State.DEFENSIVE;
            else
                combatState = State.FOLLOWER;
        }
       
    }

    public int doLeader(){
        int idx = 0;
        int tarjet = 0;
        boolean first = true;
        List<Enemy> enemies = cmbt.getEnemies();
        Enemy eTarjet = enemies.get(0);
        for(Enemy e : enemies){
            if(e.isWeak(getMainElement())){
                if(first){
                    eTarjet = e;
                    tarjet = idx;
                    first = false;
                }
                else{
                    if(eTarjet.getLife() < e.getLife())
                        tarjet = idx;
                }
                
            }
            idx++;
        }

        double i = Math.random();
        if(getLife() < getMaxLife() / 2){
            if(i > 0.95)
                combatState = State.LEADER;
            else if(i > 0.3)
                combatState = State.HARMED;
        }
        else if(i > 0.9){
            combatState = State.DEFENSIVE;
        }
        else if(i > 0.4)
            combatState = State.FOLLOWER;

        if(tarjet < 0) tarjet = 0;
        if(tarjet >= cmbt.getEnemies().size()) tarjet = cmbt.getEnemies().size() - 1;

        return tarjet;
    }

    public int doFollower(){
        int idx = cmbt.getLastTarjet();
        int size = cmbt.getEnemies().size();
        if(idx >= size)
            idx = size - 1;

        double i = Math.random();
        if(getLife() < getMaxLife() / 2){
            if(i > 0.95)
                combatState = State.LEADER;
            else if(i > 0.3)
                combatState = State.HARMED;
        }
        else if(i > 0.8){
            combatState = State.DEFENSIVE;
        }

        return idx + 1;
    }

    public int selectTarjet(){
        return switch (combatState) {
            case LEADER -> doLeader();
            case FOLLOWER -> doFollower();
            default -> doFollower();
        };
    }

    public void doDefensive(){
        //it defends on combat logic. This method is to change combatState
        double i = Math.random();
        if(i > 0.3){
            combatState = State.FOLLOWER;
        }
    }

    public void doScared(){
        //it runs on combat logic. This method is to change combatState
        double i = Math.random();
        if(getLife() < getMaxLife() / 2){
            if(i > 0.95)
                combatState = State.LEADER;
            else if(i > 0.3)
                combatState = State.HARMED;
            else
                combatState = State.FOLLOWER;

        }
        else{
            if(i > 0.95)
                combatState = State.LEADER;
            else if(i > 0.2)
                combatState = State.FOLLOWER;
            else
                combatState = State.DEFENSIVE;
        }
    }
    
}
