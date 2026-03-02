package me.model.items;

import me.model.Character;
import me.model.Element;
import me.view.Messages;

public class ClevernessItem extends Item{

    int mod;
    Element element;

    public ClevernessItem(String name, int cost, int mod){
        super(name, cost);
        this.mod = mod;
    }

    @Override
    public void use(Character c) {
        c.changeElement(element, mod);
    }

    @Override
    public void revert(Character c) {
        c.changeElement(element, -mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost());
        sb.append(" coins  Mod: +").append(mod).append("  Element: ").append(element.toString().toUpperCase()).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
