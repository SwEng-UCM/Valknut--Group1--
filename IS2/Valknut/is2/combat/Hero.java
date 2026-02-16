package combat;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {
    private String surname;
    private List <Item> inventory; // Representing 'item'

    public Hero(String name, int life, String surname) {
        super(name, life);
        inventory = new ArrayList<>(10);
        if(surname != null)
            this.surname = surname;
        else
            this.surname = " ";
    }

    public void print(){
        System.out.println(name.toUpperCase() + ", " + surname.toUpperCase());
        System.out.println();
    }
}
