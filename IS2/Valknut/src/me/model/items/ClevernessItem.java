package me.model.items;

import me.model.Character;
import me.model.Element;

public class ClevernessItem extends Item{

    int mod;
    Element element;

    public ClevernessItem(String name, int cost, int mod){
        super(name, cost);
        this.mod = mod;
    }

    public void use(Character c) {
        c.changeElement(element, mod);
    }

    public void revert(Character c) {
        c.changeElement(element, -mod);
    }

}
