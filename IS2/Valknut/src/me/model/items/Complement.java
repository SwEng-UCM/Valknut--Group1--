package me.model.items;

import me.model.Attribute;

public interface Complement {
    public void addMod(Item i); //Modificates the mod item
    public void subMod(Item i);
    public Attribute getType();
}
