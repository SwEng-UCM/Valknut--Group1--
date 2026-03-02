package me.model.items;

import me.model.Character;
import me.view.Messages;

public class AgilityItem extends Item{

    int mod;

    public AgilityItem(String name, int cost, int mod){
        super(name, cost);
        this.mod = mod;
    }

    @Override
    public void use(Character c) {
       c.changeAgility(mod);
    }

    @Override
    public void revert(Character c) {
        c.changeAgility(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coinds  Mod: +").append(mod).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
