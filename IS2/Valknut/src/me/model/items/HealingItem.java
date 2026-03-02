package me.model.items;

import me.model.Character;
import me.view.Messages;


public class HealingItem extends Item {

    private int heal;

    public HealingItem(String name, int cost, int heal){
        super(name, cost);
        this.heal = heal;
    }

    @Override
    public void use(Character c) {
        c.changeLife(heal);
    }

    @Override
    public void revert(Character c) {
        //nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Heal: +").append(heal).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
