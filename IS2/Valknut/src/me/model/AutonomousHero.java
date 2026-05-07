/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model;

import me.model.items.HealingItem;
import me.model.items.Item;
import me.socket.Request;

public class AutonomousHero extends Hero {

    //Autonomous hero has a state on the combat and a strategy to attack
    //As well as all the standar hero attributes
    
    private State combatState;
    private CombatStrategy combatStrategy;

    public AutonomousHero(String name, int life, int max_life, String surname, int id){
        super(name, life, max_life, surname, id);
        setAutonomous(true);
        combatState = State.ATTACK;
        combatStrategy = new FollowerCombatStrategy();
    }

    @Override
    public String does(Request rq){ //Don't use the request but needed for overriding
        StringBuilder sb = new StringBuilder();
        switch (combatState) {
            case HARMED ->{doHarmed(); sb.append(name().toUpperCase()).append(" heals theirself");}
            case ATTACK -> sb.append(cmbt.attack(doAttack()));
            case DEFENSIVE -> {doDefensive(); sb.append(cmbt.defend());}
            case SCARED -> {doScared(); sb.append(cmbt.run());}
            default -> sb.append(cmbt.attack(doAttack()));
        }

        return sb.toString();
    }

    //Every state has a probability of changing to any of the other states
    //Combat strategy is chosen by the player
    public void doHarmed(){
        Item item = new HealingItem(null, 0, 0, 0, null);
        if(inventory.containsType(item)){
        } else {
            return;
        }
        boolean used = inventory.useFirstOfType(item);

        if(used && getLife() > 50)
            combatState = State.ATTACK;
        if(!used){
            double i = Math.random();
            if(i > 0.95){
                combatStrategy = new LeaderCombatStrategy();
                combatState = State.ATTACK;
            }
            else if(i > 0.85)
                combatState = State.DEFENSIVE;
            else if(i > 0.65)
                combatState = State.SCARED;
            else{
                combatStrategy = new FollowerCombatStrategy();
                combatState = State.ATTACK;
            }
        }
    }

    public int doAttack(){
        
        int tarjet = combatStrategy.execute(getMainElement(), cmbt.getEnemies(), cmbt.getLastTarjet());

        double i = Math.random();
        if(getLife() < getMaxLife() / 2){
            if(i > 0.3)
                combatState = State.HARMED;
        }
        else if(i > 0.85){
            combatState = State.DEFENSIVE;
            i = Math.random();
            if(i > 0.95){
                combatState = State.SCARED;
            }
            else if (i > 0.90){
                setCombatStrategy(0);
            }
            else{
                setCombatStrategy(1);
            }
        }

        return tarjet;
    }

    public void doDefensive(){
        //it defends on combat logic. This method is to change combatState
        double i = Math.random();
        if(getLife() < getMaxLife() / 2){
            if(i > 0.3)
                combatState = State.HARMED;
        }
        else if(i > 0.3){
            combatState = State.ATTACK;
            i = Math.random();
            if(i > 0.95){
                combatState = State.SCARED;
            }
            else if (i > 0.90){
                setCombatStrategy(0);
            }
            else{
                setCombatStrategy(1);
            }
        }
    }

    public void doScared(){
        //it runs on combat logic. This method is to change combatState
        double i = Math.random();
        if(getLife() < getMaxLife() / 2){
            if(i > 0.35)
                combatState = State.HARMED;
            else{
                combatState = State.ATTACK;
                i = Math.random();
                if(i > 0.95)
                    setCombatStrategy(0);
                else
                    setCombatStrategy(1);
            }

        }
        else{
            if(i > 0.2){
                combatState = State.ATTACK;
                i = Math.random();
                if(i > 0.95)
                    setCombatStrategy(0);
                else
                    setCombatStrategy(1);
            }
            else
                combatState = State.DEFENSIVE;
        }
    }

    public void setCombatStrategy(int i){
        switch (i) {
            case 0 -> combatStrategy = new LeaderCombatStrategy();
            case 1 -> combatStrategy = new FollowerCombatStrategy();
            default -> {
                combatStrategy = new FollowerCombatStrategy();
            }
        }
    }
    
}
