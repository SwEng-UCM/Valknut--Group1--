package me.model;

import java.util.ArrayList;
import java.util.List;
import me.model.items.Item;

public class Hero extends Character {

    private final static int INIT_INV_CAP = 10;

    private String surname;
    private List <Item> inventory; // Representing 'item'
    private int inventory_cap;

    public Hero(String name, int life, int max_life, String surname) {
        super(name, life, max_life);
        inventory_cap = INIT_INV_CAP;
        inventory = new ArrayList<>(inventory_cap);
        if(surname != null)
            this.surname = surname;
        else
            this.surname = " ";
    }

    public String toString(){
        return name.toUpperCase() + ", " + surname.toUpperCase();
    }

    public boolean addItem(Item i){
        if(inventory.size() < inventory_cap){
            inventory.add(i);
            return true;
        }
        return false;
    }

    public boolean useItem(Item i){
        if(!inventory.isEmpty()){
            int j = 0;
            while(j < inventory.size()){
                if(inventory.get(j).getName().equals(i.getName())){
                    inventory.remove(j);
                    i.use(this);
                    return true;
                }
                j++;
            }
            return false;
        }
        return false;
    }
}
