package me.model.items;

import me.model.Attribute;
import me.model.Character;
import me.view.Messages;

public abstract class Item {

    private final String name;
    protected int mod;
    protected boolean used;
    private int cuantity;
    private final int cost;
    private int turn;
    protected Attribute type;
    protected Character c;
    protected Complement complement;

    public Item(String name, int cost, int mod, int time, Attribute type){
        this.name = name;
        this.mod = mod;
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

    public void addMod(int mod){
        this.mod += mod;
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

    @Override
    public abstract String toString();

    public boolean isUsed(){
        return used;
    }

    public void delete(){
        revert();
    }

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

    public Complement removeComplement(){
        if(complement != null){
            Complement c = complement;
            complement = null;
            c.subMod(this);
            return c;
        }
        return null;
    }
    

}
