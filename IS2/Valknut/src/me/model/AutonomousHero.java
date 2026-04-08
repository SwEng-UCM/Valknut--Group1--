package me.model;

import java.util.List;
import me.model.items.HealingItem;
import me.model.items.Item;
import me.view.Messages;

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
        inventory.useFirstOfType(item);

        if(getLife() > 50)
            combatState = State.FOLLOWER;
        
    }
    

    public int doLeader(){
        int idx = 0;

        List<Enemy> enemies = cmbt.getEnemies();
        for(Enemy e : enemies){
            
        }

        return idx;
    }

    public int doFollower(){
        int idx = 0;
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
        defend();
        double i = Math.random();
        if(i > 0.3){
            combatState = State.FOLLOWER;
        }
    }

    public String doScared(){
        StringBuilder sb = new StringBuilder();
        double i = Math.random();
        if(i > 0.5){
            setEscaped(true);
            sb.append(Messages.PLAYER_RUNS);
        }
        else{
            combatState = State.FOLLOWER;
            sb.append(Messages.PLAYER_RUNFAIL);
        }
        sb.append(Messages.NEW_LINE);
        return sb.toString();
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
        double i = Math.random();
        if(i > 0.3){
            combatState = State.FOLLOWER;
        }
        return co;
    }
    
}
