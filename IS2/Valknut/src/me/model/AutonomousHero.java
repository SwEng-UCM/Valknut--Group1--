package me.model;

import me.model.items.ItemType;
import me.view.Messages;

public class AutonomousHero extends Hero {
    
    private State combatState;

    public AutonomousHero(String name, int life, int max_life, String surname){
        super(name, life, max_life, surname);
        setAutonomous(true);
        combatState = State.LEADER;
    }

    public void doHarmed(){
        if(!inventory.containsType(ItemType.HEAL)){
            return;
        }
        inventory.useFirstOfType(ItemType.HEAL);

        if(getLife() > 50)
            combatState = State.FOLLOWER;
        
    }

    public void doLeader(){
        
    }

    public void doFollower(){
        
    }

    public void doDefensive(){
        
    }

    public String doScared(){
        StringBuilder sb = new StringBuilder();
        double i = Math.random();
        if(i > 0.5){
            setEscaped(true);
            sb.append(Messages.PLAYER_RUNS).append(Messages.NEW_LINE);
        }
        else{
            combatState = State.FOLLOWER;
            sb.append(Messages.PLAYER_RUNFAIL).append(Messages.NEW_LINE);
        }
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
        return sb.toString();
    }
    
}
