package me.model;

import java.util.List;
import me.model.items.HealingItem;
import me.model.items.Item;

public class AutonomousHero extends Hero {
    
    private State combatState;

    public AutonomousHero(String name, int life, int max_life, String surname){
        super(name, life, max_life, surname);
        setAutonomous(true);
        combatState = State.LEADER;
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
        if(getLife() < max_life / 2){
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

        return tarjet;
    }

    public int doFollower(){
        int idx = cmbt.getLastTarjet();
        int size = cmbt.getEnemies().size();
        if(idx >= size)
            idx = size - 1;

        double i = Math.random();
        if(getLife() < max_life / 2){
            if(i > 0.95)
                combatState = State.LEADER;
            else if(i > 0.3)
                combatState = State.HARMED;
        }
        else if(i > 0.8){
            combatState = State.DEFENSIVE;
        }

        return idx;
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
        if(getLife() < max_life / 2){
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

    public CombatOption selectAction(){
        CombatOption co;
        switch (combatState) {
            case HARMED -> co = CombatOption.USE_ITEM;
            case LEADER -> co = CombatOption.ATTACK;
            case FOLLOWER -> co = CombatOption.ATTACK;
            case DEFENSIVE -> co = CombatOption.DEFEND;
            case SCARED -> co = CombatOption.RUN;
            default -> co = CombatOption.ATTACK;
        }
        return co;
    }
    
}
