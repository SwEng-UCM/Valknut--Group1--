package me.model;

import me.model.items.Inventory;

public class Hero extends Character {

    private String surname;
    private Inventory inventory; // Representing 'item'


    public Hero(String name, int life, int max_life, String surname) {
        super(name, life, max_life);
        if(surname != null)
            this.surname = surname;
        else
            this.surname = " ";
        inventory = new Inventory();
    }

    public Inventory getInventory(){
        return inventory;
    }

    public String toString(){
        return name.toUpperCase() + ", " + surname.toUpperCase();
    }
}
