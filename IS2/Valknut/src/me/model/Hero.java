package me.model;

import java.util.Random;
import me.model.items.Inventory;
import me.model.items.Item;
import me.socket.Request;
import me.view.Messages;

public class Hero extends Character implements Player {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String surname;
    private boolean autonomous; 
    protected Inventory inventory; // Representing 'item'
    private Inventory using;
    protected Combat cmbt;
    private int level = 1;
    private int xp = 0;


    public Hero(String name, int life, int max_life, String surname, int id) {
        super(name, life, max_life);
        this.id = id;
        if(surname != null)
            this.surname = surname;
        else
            this.surname = " ";
        inventory = new Inventory();
        using = new Inventory();
    }

    public void setHero(String name, int life, int max_life, String surname){
        setName(name);
        setLife(life);
        setMaxLife(max_life);
        this.surname = surname;
    }

    public void setAutonomous(boolean x){ autonomous = x; }
    public boolean isAutonomous(){ return autonomous; }
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
        changeLife(getMaxLife());

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

        switch(rand.nextInt(3)) {
        case 0 -> changeStrength(1);
        case 1 -> changeShield(1);
        case 2 -> changeAgility(1);
        }

        return sb.toString();
    }

    @Override
    public String does(Request rq){
        return null;
    }

    public boolean addItem(Item i){
        i.assignCharacter(this);
        return inventory.addItem(i);
    }

    public Inventory getInventory(){
        return inventory;
    }

    @Override
    public String toString(){
        return getName().toUpperCase() + ", " + surname.toUpperCase();
    }

    private String displayInv(){
        return inventory.getInfo();
    }

     public String displayInventory(){
        StringBuilder sb = new StringBuilder();

        sb.append("INVENTORY... ").append(Messages.NEW_LINE);
        sb.append(displayInv());

        return sb.toString();
    }
    public boolean isUsing(Item i){
        return using.containsItem(i, i.getName());
    }

    public String useItem(Item i){
        StringBuilder sb = new StringBuilder();
        //if(!isUsing(i)){
        inventory.dropItem(i);
        i.assignCharacter(this);
            i.use();
        //    using.addItem(i);
            sb.append(Messages.USED_ITEM).append(i.getName().toUpperCase());
        //}
        //else
        //    sb.append(Messages.USING_ITEM).append(i.getName().toUpperCase());
        
        return sb.toString();
    }

    public void setCombat(Combat cb){
        this.cmbt = cb;
    }
}
