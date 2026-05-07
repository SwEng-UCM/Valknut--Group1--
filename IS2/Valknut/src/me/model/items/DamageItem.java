/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model.items;

import me.view.Messages;

public class DamageItem extends Item{
    
    public DamageItem(String name, int cost, int mod, int time,  ItemType type){
        super(name, cost, mod, time, type);
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

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Mod: +").append(mod).append(" dmg");;
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

    @Override
    public Item createInstanceOf(String s){
        switch (s) {
            case "Fire Gantlet" -> {
                return new DamageItem(s, 100, 2, 4, ItemType.DAMAGE);
            }
            case "Uru Gantlet" -> {
                return new DamageItem(s, 1000, 5, 8, ItemType.DAMAGE);
            }
            case "Mistletoe's Essence" -> {
                return new DamageItem(s, 500, 4, 2, ItemType.DAMAGE);
            }
            case "Gungnir's Piece" -> {
                return new DamageItem(s, 500, 4, 8, ItemType.DAMAGE);
            }
            default -> throw new AssertionError();
        }
    }

}
