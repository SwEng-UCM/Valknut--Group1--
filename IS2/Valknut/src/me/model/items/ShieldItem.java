package me.model.items;

import me.model.Character;

public class ShieldItem extends Item{

    private int mod;

    public ShieldItem(String name, int value, int shield){
        super(name, value);
        this.mod = shield;
    }

    @Override
    public void use(Character c) {
       c.changeShield(mod);
    }

    @Override
    public void revert(Character c) {
        c.changeShield(-mod);
    }
    
}
