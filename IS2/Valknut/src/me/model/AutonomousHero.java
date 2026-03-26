package me.model;

public class AutonomousHero extends Hero {
    
    private ActionStrategyInteface asi;

    public AutonomousHero(String name, int life, int max_life, String surname){
        super(name, life, max_life, surname);
        setAutonomous(true);
    }

    public String selectAction(){

        return null;
    }
    
}
