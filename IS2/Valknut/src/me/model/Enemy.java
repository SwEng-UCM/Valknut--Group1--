package me.model;

import java.util.List;

public class Enemy extends Character {
    private static final long serialVersionUID = 1L;
    private final int xpReward;
    private String attkDesc;

    public Enemy(String name, int life, int max_life, int xpReward, String attack) {
        super(name, life, max_life);
        this.xpReward = xpReward;
        attkDesc = attack;
    }

    public int getXpReward() {
        return xpReward;
    }
//whatever
    public Hero selectTarjet(List<Hero> e){
        int max = 0, i = -1, j = 0;
        for(Hero hero: e){
            if(max < hero.getLife() && !hero.escaped()){
                max = hero.getLife();
                i = j;
            }
            j++;
        }
        if(i == -1)
            return null;
        else
            return e.get(i);
    }
    
    public String getAttack() {
		return attkDesc;
	}
}

