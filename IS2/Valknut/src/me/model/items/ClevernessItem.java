package me.model.items;

import me.model.Attribute;
import me.model.Element;
import me.view.Messages;

public class ClevernessItem extends Item{
    
    Element element;

    public ClevernessItem(String name, int cost, int mod, int time,  Attribute type){
        super(name, cost, mod, time, type);
    }

    public void updateWithComplement(){
        revert();
        use();
    }

    @Override
    public void use() {
        c.changeElement(element, mod);
    }

    @Override
    public void revert() {
        c.changeElement(element, -mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost());
        sb.append(" coins  Mod: +").append(mod);
        sb.append("  Turns: ").append(getTurn()).append("  Element: ").append(element.toString().toUpperCase()).append(Messages.NEW_LINE);

        return sb.toString();
    }

}
