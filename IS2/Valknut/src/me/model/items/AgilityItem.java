package me.model.items;

import me.view.Messages;

public class AgilityItem extends Item{

    public AgilityItem(String name, int cost, int mod, int time,  ItemType type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void use() {
        if(c != null)
            c.changeAgility(mod);
    }

    @Override
    public void revert() {
        c.changeAgility(-mod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coinds  Mod: +").append(mod).append(" ag");
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public Item createInstanceOf(String s){
        switch (s) {
            case "Tied Shoe" -> {
                return new AgilityItem(s, 150, 2, 8, ItemType.AGILITY);
            }
            case "Evasive Ring" -> {
                return new AgilityItem(s, 500, 4, 8, ItemType.AGILITY);
            }
            case "Huliðshjálmr's Fragment" -> {
                return new AgilityItem(s, 1000, 8, 8, ItemType.AGILITY);
            }
            default -> throw new AssertionError();
        }
    }

}
