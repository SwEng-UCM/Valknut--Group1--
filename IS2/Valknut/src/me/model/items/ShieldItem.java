package me.model.items;

import me.model.Character;
import me.view.Messages;

public class ShieldItem extends Item{

    private int mod;

    public ShieldItem(String name, int value, int shield){
        super(name, value);
        this.mod = shield;
    }

    @Override
    public void use(Character c) {
       c.changeShield(mod);
    }

    @Override
    public void revert(Character c) {
        c.changeShield(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod).append(Messages.NEW_LINE);

        return sb.toString();
    }
    
}
