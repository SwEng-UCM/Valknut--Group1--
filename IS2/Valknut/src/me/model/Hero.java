package me.model;

import java.util.Random;
import me.model.items.Inventory;
import me.model.items.Item;

public class Hero extends Character {

    private String surname;
    private Inventory inventory; // Representing 'item'
    private int level = 1;
    private int xp = 0;


    public Hero(String name, int life, int max_life, String surname) {
        super(name, life, max_life);
        if(surname != null)
            this.surname = surname;
        else
            this.surname = " ";
        inventory = new Inventory();
    }
    public int getLevel() { return level; }
    public int getXp() { return xp; }

    //XP curve (we can tweak this)
    private int xpToNextLevel() {
        return 100 + (level - 1) * 50;
    }

    public void gainXp(int amount) {
        if (amount <= 0) return;
        xp += amount;
        System.out.println(name().toUpperCase() + " gained " + amount + " XP. (" + xp + "/" + xpToNextLevel() + ")");

        while (xp >= xpToNextLevel()) {
            xp -= xpToNextLevel();
            levelUp();
        }
    }

    private void levelUp() {
    	Random rand = new Random();
        level++;
        System.out.println(name().toUpperCase() + " LEVEL UP! Now level " + level);

        // rewards: more max HP + heal a bit
        increaseMaxLife(10);
        changeLife(10);

        // small stat bumps
        changeAgility(1);
        changeShield(1); // uses RESISTANCE as shield stat
        
        //Increase 2 stats (greatest and random)
        changeElement(getMainElement(), 1);
        switch(rand.nextInt(5)) {
        case 0 -> changeElement(Element.BLOOD, 1);
        case 1 -> changeElement(Element.CHAOS, 1);
        case 2 -> changeElement(Element.FIRE, 1);
        case 3 -> changeElement(Element.ICE, 1);
        case 4 -> changeElement(Element.NATURE, 1);
        }
        
    }

    public boolean addItem(Item i){
        return inventory.addItem(i);
    }

    public Inventory getInventory(){
        return inventory;
    }

    public String toString(){
        return name.toUpperCase() + ", " + surname.toUpperCase();
    }

    public String displayInventory(){
        return inventory.getInfo();
    }
}
