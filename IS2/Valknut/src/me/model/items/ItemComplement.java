package me.model.items;

import me.model.Attribute;

public class ItemComplement extends Item implements Complement{

    public ItemComplement(String name, int cost, int mod, int time, Attribute type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void addMod(Item i){
        i.addMod(mod);
    }

    @Override
    public void subMod(Item i) {
        i.addMod(-mod);
    }

    @Override
    public void use() {
       
    }

    @Override
    public void revert() {
        
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public Attribute getType() {
        return type;
    }

}
