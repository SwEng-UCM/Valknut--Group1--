package me.model.items;

import me.model.Character;

public abstract class Item {

    private String name;
    protected boolean used;
    private int cost;

    public Item(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    public int getValue(){
        return cost;
    }

    public String getName(){
        return name;
    }

    public abstract void use(Character c);

    public boolean isUsed(){
        return used;
    }

    public void delete(Character c){
        revert(c);
    }

    public abstract void revert(Character c);

}
