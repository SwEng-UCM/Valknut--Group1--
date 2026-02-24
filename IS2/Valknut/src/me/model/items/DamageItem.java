package me.model.items;

import me.model.Character;

public class DamageItem extends Item{

    private double mod;

    public DamageItem(String name, int cost, double mod){
        super(name, cost);
        this.mod = mod;
    }

    @Override
    public void use(Character c) {
        
    }

    @Override
    public void revert(Character c) {
        
    }

}
