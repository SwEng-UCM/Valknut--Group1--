package me.model.items;

import me.model.Attribute;
import me.view.Messages;

public class AgilityItem extends Item{

    public AgilityItem(String name, int cost, int mod, int time,  Attribute type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void use() {
        if(c != null)
            c.changeAgility(mod);
    }

    @Override
    public void revert() {
        c.changeAgility(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coinds  Mod: +").append(mod);
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
