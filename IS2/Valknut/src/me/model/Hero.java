package me.model;

import me.model.items.Inventory;

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
        level++;
        System.out.println(name().toUpperCase() + " LEVEL UP! Now level " + level);

        // rewards: more max HP + heal a bit
        increaseMaxLife(10);
        changeLife(10);

        // small stat bumps
        changeAgility(1);
        changeShield(1); // uses RESISTANCE as shield stat
    }

    public Inventory getInventory(){
        return inventory;
    }

    public String toString(){
        return name.toUpperCase() + ", " + surname.toUpperCase();
    }
}
