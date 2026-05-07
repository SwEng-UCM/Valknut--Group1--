/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model.items;

import java.io.Serializable;
import me.model.Character;
import me.view.Messages;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    //Items has a name, a modificator, a cunatity for the stack in inventory
    // a cost, a number of turns its effect lasts, a type, the hero it belongs
    // and a possible complement that modifies mod

    private final String name;
    protected int mod;
    protected boolean used;
    private int cuantity;
    private final int cost;
    private int turn;
    protected ItemType type;
    protected Character c;
    protected Complement complement;

    public Item(String name, int cost, int mod, int time, ItemType type){
        this.name = name.toLowerCase();
        this.mod = mod;
        this.cuantity = 1;
        this.cost = cost;
        this.turn = time;
        this.type = type;
    }

    public ItemType getType(){ // HEAL, RESITANCE, DAMAGE, ELEMENTAL, AGILITY, SPEED
        return type;
    }

    public int getTurn(){ // how much time has been used
        return turn;
    }

    public int getCost(){
        return cost;
    }

    public void addMod(int mod){ //when complements are used
        this.mod += mod;
    }

    public void addCuantity(){ // to accumulate in the inventory
        cuantity++;
    }

    public void subCuantity(){
        cuantity--;
        if(cuantity < 0)
            cuantity = 0;
    }

    public void decreaseTime(){ 
        turn--;
        if(turn < 0){
            delete();
        }
    }

    public int getCuantity(){
        return cuantity;
    }

    public String getName(){
        return name;
    }

    public void assignCharacter(Character c){ // to later modify hero stats, items know the hero
        this.c = c;
    }

    
    // To implement in different item type classes
    public abstract void use(); 
    public abstract void revert();
    public abstract Item createInstanceOf(String s);

    @Override
    public abstract String toString();

    public boolean isUsed(){
        return used;
    }

    public void delete(){
        revert();
    }

    //Matches a complement, uses addMod() from before to modify
    public String addComplement(Complement c){

        StringBuilder sb = new StringBuilder();

        if(c.getType() == type){
            complement = c;
            c.addMod(this);
            sb.append(Messages.MATCHED_ITEM).append(Messages.NEW_LINE);
        }
        else{
            sb.append(Messages.NON_MATCHED_ITEM).append(Messages.NEW_LINE);
        }

        return sb.toString();
    }

    public Complement removeComplement(){ // erase complement and return the erased complement
        if(complement != null){
            Complement c = complement;
            complement = null;
            c.subMod(this);
            return c;
        }
        return null;
    }
    
    // Acts like a down counter
    public void update(){
        decreaseTime();
    }

}
