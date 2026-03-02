package me.model.items;

import me.model.Character;

public abstract class Item {

    private String name;
    protected boolean used;
    private int cuantity;
    private int cost;

    public Item(String name, int cost){
        this.name = name;
        this.cuantity = 0;
        this.cost = cost;
    }

    public int getCost(){
        return cost;
    }

    public void addCuantity(){
        cuantity++;
    }

    public void subCuantity(){
        cuantity--;
        if(cuantity < 0)
            cuantity = 0;
    }

    public int getCuantity(){
        return cuantity;
    }

    public String getName(){
        return name;
    }

    public abstract void use(Character c);
    public abstract String toString();

    public boolean isUsed(){
        return used;
    }

    public void delete(Character c){
        revert(c);
    }

    public abstract void revert(Character c);

}
