package me.model.items;

import me.model.Character;


public class HealingItem extends Item {

    private int heal;

    public HealingItem(String name, int cost, int heal){
        super(name, cost);
        this.heal = heal;
    }

    @Override
    public void use(Character c) {
        c.changeLife(heal);
    }

}
