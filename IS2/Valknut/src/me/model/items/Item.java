package me.model.items;

import me.model.Attribute;
import me.model.Character;

public abstract class Item {

    private final String name;
    protected boolean used;
    private int cuantity;
    private final int cost;
    private int turn;
    private Attribute type;
    protected Character c;

    public Item(String name, int cost, int time, Attribute type){
        this.name = name;
        this.cuantity = 0;
        this.cost = cost;
        this.turn = time;
        this.type = type;
    }

    public int getTurn(){
        return turn;
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

    public void decreaseTime(){
        turn--;
        if(turn == 0){
            delete();
        }
    }

    public int getCuantity(){
        return cuantity;
    }

    public String getName(){
        return name;
    }

    public void assignCharacter(Character c){
        this.c = c;
    }

    public abstract void use();
    public abstract void revert();
    public abstract String toString();

    public boolean isUsed(){
        return used;
    }

    public void delete(){
        revert();
    }

    

}
