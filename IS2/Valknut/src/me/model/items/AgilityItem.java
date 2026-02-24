package me.model.items;

import me.model.Character;

public class AgilityItem extends Item{

    int mod;

    public AgilityItem(String name, int cost){
        super(name, cost);
    }

    @Override
    public void use(Character c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
