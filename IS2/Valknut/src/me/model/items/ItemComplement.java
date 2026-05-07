/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model.items;

public class ItemComplement extends Item implements Complement{

    public ItemComplement(String name, int cost, int mod, int time, ItemType type){
        super(name, cost, mod, time, type);
    }

    @Override
    public void addMod(Item i){
        i.addMod(mod);
    }

    @Override
    public void subMod(Item i) {
        i.addMod(-mod);
    }

    @Override
    public void use() {
       
    }

    @Override
    public void revert() {
        
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    public Item createInstanceOf(String s){
        switch (s) {
            case "Mistletoe's Essence" -> {
                return new DamageItem(s, 500, 4, 2, ItemType.DAMAGE);
            }
            case "Gungnir's Piece" -> {
                return new DamageItem(s, 500, 4, 8, ItemType.DAMAGE);
            }
                        case "Chaos Essence" -> {
                return new ClevernessItem(s, 200, 2, 8, ItemType.ELEMENTAL);
            }
            case "Fire Essence" -> {
                return new ClevernessItem(s, 200, 2, 8, ItemType.ELEMENTAL);
            }
            case "Ice Essence" -> {
                return new ClevernessItem(s, 200, 2, 8, ItemType.ELEMENTAL);
            }
            case "Nature Essence" -> {
                return new ClevernessItem(s, 200, 2, 8, ItemType.ELEMENTAL);
            }
            case "Blood Essence" -> {
                return new ClevernessItem(s, 200, 2, 8, ItemType.ELEMENTAL);
            }
                        case "Svalinn's Fragment" -> {
                return new ResistanceItem(s, 500, 4, 1, ItemType.RESITANCE);
            }
            case "Megingjörð's Piece" -> {
                return new ResistanceItem(s, 500, 4, 1, ItemType.RESITANCE);
            }
            default -> throw new AssertionError();
        }
    }

}
