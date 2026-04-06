package me.model.items;

public interface Complement {
    public void addMod(Item i); //Modificates the mod item
    public void subMod(Item i);
    public ItemType getType();
}
