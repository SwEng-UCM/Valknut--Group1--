package me.model.items;

import me.model.Character;

public class ShieldItem extends Item{

    private int shield;

    public ShieldItem(String name, int value, int shield){
        super(name, value);
        this.shield = shield;
    }

    @Override
    public void use(Character c) {
       c.changeShield(shield);
    }
    
}
