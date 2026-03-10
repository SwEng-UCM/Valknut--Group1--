package me.model.items;

import me.model.Attribute;
import me.view.Messages;

public class ShieldItem extends Item{

    private int mod;

    public ShieldItem(String name, int value, int shield, int time, Attribute type){
        super(name, value, time, type);
        this.mod = shield;
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

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod);
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }
    
}
