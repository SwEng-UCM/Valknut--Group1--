package me.model.items;

import me.view.Messages;

public class DamageItem extends Item{

    private int mod;

    public DamageItem(String name, int cost, int mod, int time){
        super(name, cost, time);
        this.mod = mod;
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
