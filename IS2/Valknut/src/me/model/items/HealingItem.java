package me.model.items;

import me.model.Attribute;
import me.view.Messages;


public class HealingItem extends Item {

    public HealingItem(String name, int cost, int mod, int time, Attribute type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void use() {
        c.changeLife(mod);
    }

    @Override
    public void revert() {
        //nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Heal: +").append(mod).append(" hp");;
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
