package me.model.items;

import java.io.Serializable;

public interface Complement extends Serializable {
    public void addMod(Item i); //Modificates the mod item
    public void subMod(Item i);
    public ItemType getType();
}
