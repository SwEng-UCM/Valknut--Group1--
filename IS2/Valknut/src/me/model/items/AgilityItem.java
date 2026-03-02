package me.model.items;

import me.model.Character;

public class AgilityItem extends Item{

    int mod;

    public AgilityItem(String name, int cost, int mod){
        super(name, cost);
        this.mod = mod;
    }

    @Override
    public void use(Character c) {
       c.changeAgility(mod);
    }

    @Override
    public void revert(Character c) {
        c.changeAgility(-mod);
    }

}
