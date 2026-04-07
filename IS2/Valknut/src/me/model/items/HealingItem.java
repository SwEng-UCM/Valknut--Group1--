package me.model.items;

import me.view.Messages;


public class HealingItem extends Item {

    public HealingItem(String name, int cost, int mod, int time, ItemType type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void use() {
        c.changeLife(mod);
    }

    @Override
    public void revert() {
        //nothing
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName().toUpperCase()).append("  Cost: ").append(getCost()).append(" coins  Heal: +").append(mod).append(" hp");;
        sb.append("  Turns: ").append(getTurn()).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public Item createInstanceOf(String s){
        switch (s) {
            case "Seidr's Herb Sprouts" -> {
                return new HealingItem(s, 10, 20, 1, ItemType.HEAL);
            }
            case "Ancient Yarrow" -> {
                return new HealingItem(s, 60, 40, 1, ItemType.HEAL);
            }
            case "Curing Crystal Stone" -> {
                return new HealingItem(s, 200, 80, 1, ItemType.HEAL);
            }
            case "Drop of Eitr's Elixir" -> {
                return new HealingItem(s, 300, 0, 1, ItemType.HEAL);
            }
            default -> throw new AssertionError();
        }
    }

}
