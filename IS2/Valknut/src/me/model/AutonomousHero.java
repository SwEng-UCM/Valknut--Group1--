package me.model;

import me.model.items.HealingItem;
import me.model.items.Item;
import me.view.Messages;

public class AutonomousHero extends Hero {
    
    private State combatState;
    private Combat cmbt;

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

    public void initCombat(Combat cmbt){
        this.cmbt = cmbt;
    }

    public void doLeader(){
        
    }

    public void doFollower(){
        
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

    public String selectAction(){
        StringBuilder sb = new StringBuilder();
        switch (combatState) {
            case HARMED -> doHarmed();
            case LEADER -> doLeader();
            case FOLLOWER -> doFollower();
            case DEFENSIVE -> doDefensive();
            case SCARED -> sb.append(doScared());
            default -> throw new AssertionError();
        }
        double i = Math.random();
        if(i > 0.3){
            combatState = State.FOLLOWER;
        }
        return sb.toString();
    }
    
}
