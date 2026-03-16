package me.model.items;

import me.model.Attribute;
import me.view.Messages;

public class ResistanceItem extends Item{

    public ResistanceItem(String name, int value, int mod, int time, Attribute type){
        super(name, value, mod, time, type);
    }

    @Override
    public void use() {
       c.changeShield(mod);
    }

    @Override
    public void revert() {
        c.changeShield(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod).append(" rst");
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }
    
}
