package me.model;

public abstract class Item {

    private String name;
    protected boolean used;
    private int value;

    public Item(String name, int value){
        this.name = name;
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public String getName(){
        return name;
    }

    public abstract void use(Character c);

    public boolean isUsed(){
        return used;
    }

}
