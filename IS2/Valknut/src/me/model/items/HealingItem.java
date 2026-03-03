package me.model.items;

import me.view.Messages;


public class HealingItem extends Item {

    private int heal;

    public HealingItem(String name, int cost, int heal, int time){
        super(name, cost, time);
        this.heal = heal;
    }

    @Override
    public void use() {
        c.changeLife(heal);
    }

    @Override
    public void revert() {
        //nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Heal: +").append(heal);
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
