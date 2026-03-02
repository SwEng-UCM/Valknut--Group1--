package me.model.items;

import me.model.Character;

public class DamageItem extends Item{

    private int mod;

    public DamageItem(String name, int cost, int mod){
        super(name, cost);
        this.mod = mod;
    }

    public void use(Character c) {
        c.changeStrength(mod);
    }

    public void revert(Character c) {
        c.changeStrength(-mod);
    }

}
