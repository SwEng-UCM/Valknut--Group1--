package me.model.items;

import me.model.Attribute;
import me.view.Messages;

public class DamageItem extends Item{
    
    public DamageItem(String name, int cost, int mod, int time,  Attribute type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void use() {
        c.changeStrength(mod);
    }

    @Override
    public void revert() {
        c.changeStrength(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod);
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
