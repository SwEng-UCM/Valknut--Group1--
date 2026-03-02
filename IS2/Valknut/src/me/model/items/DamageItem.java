package me.model.items;

import me.model.Character;
import me.view.Messages;

public class DamageItem extends Item{

    private int mod;

    public DamageItem(String name, int cost, int mod){
        super(name, cost);
        this.mod = mod;
    }

    public void use(Character c) {
        c.changeStrength(mod);
    }

    public void revert(Character c) {
        c.changeStrength(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
