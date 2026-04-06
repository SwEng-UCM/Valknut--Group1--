package me.model.items;

public class ItemComplement extends Item implements Complement{

    public ItemComplement(String name, int cost, int mod, int time, ItemType type){
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
    public ItemType getType() {
        return type;
    }

}
