package me.model;

import java.util.Random;
import me.model.items.Inventory;
import me.model.items.Item;
import me.view.Messages;

public class Hero extends Character {

    private final String surname;
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

    public String gainXp(int amount) {
        StringBuilder sb = new StringBuilder();
        if (amount <= 0) return sb.toString();
        xp += amount;
        sb.append(name().toUpperCase()).append(" gained ").append(amount);
        sb.append(" XP. (").append(xp).append("/").append(xpToNextLevel()).append( ")").append(Messages.NEW_LINE);

        while (xp >= xpToNextLevel()) {
            xp -= xpToNextLevel();
            sb.append(levelUp());
        }

        return sb.toString();
    }

    private String levelUp() {
        StringBuilder sb = new StringBuilder();
    	Random rand = new Random();
        level++;
        sb.append(name().toUpperCase()).append(" LEVEL UP! Now level ").append(level).append(Messages.NEW_LINE);
        
        // rewards: more max HP + heal a bit
        increaseMaxLife(20);
        changeLife(max_life);

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
        return sb.toString();
    }

    public boolean addItem(Item i){
        i.assignCharacter(this);
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
