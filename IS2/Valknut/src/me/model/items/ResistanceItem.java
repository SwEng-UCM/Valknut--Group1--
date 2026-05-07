/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model.items;

import me.view.Messages;

public class ResistanceItem extends Item{

    public ResistanceItem(String name, int value, int mod, int time, ItemType type){
        super(name, value, mod, time, type);
    }

    @Override
    public void use() {
       c.changeShield(mod);
    }

    @Override
    public void revert() {
        c.changeShield(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod).append(" rst");
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

    @Override
    public Item createInstanceOf(String s){
        switch (s) {
            case "Iron Armor Piece" -> {
                return new ResistanceItem(s, 5, 1, 3, ItemType.RESITANCE);
            }
            case "Protection Ring" -> {
                return new ResistanceItem(s, 70, 2, 3, ItemType.RESITANCE);
            }
            case "Thor's Shield" -> {
                return new ResistanceItem(s, 300, 4, 5, ItemType.RESITANCE);
            }
            default -> throw new AssertionError();
        }
    }
    
}
