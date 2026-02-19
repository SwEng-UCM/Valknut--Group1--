package me.model;

import java.util.List;

public class Enemy extends Character {
    public Enemy(String name, int life) {
        super(name, life);
    }

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
}
