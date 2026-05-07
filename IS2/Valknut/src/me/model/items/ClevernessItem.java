/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model.items;

import me.model.Element;
import me.view.Messages;

public class ClevernessItem extends Item{
    
    Element element;

    public ClevernessItem(String name, int cost, int mod, int time,  ItemType type){
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
        sb.append(" coins  Mod: +").append(mod).append(" cl");;
        sb.append("  Turns: ").append(getTurn()).append("  Element: ").append(element.toString().toUpperCase()).append(Messages.NEW_LINE);

        return sb.toString();
    }

    @Override
    public Item createInstanceOf(String s){
        switch (s) {
            case "Brief Unknown Parchment" -> {
                return new ClevernessItem(s, 10, 1, 2, ItemType.ELEMENTAL);
            }
            case "Breeze's Words" -> {
                return new ClevernessItem(s, 70, 2, 2, ItemType.ELEMENTAL);
            }
            case "Edda's Fragment" -> {
                return new ClevernessItem(s, 300, 3, 2, ItemType.ELEMENTAL);
            }
            default -> throw new AssertionError();
        }
    }
    

}
