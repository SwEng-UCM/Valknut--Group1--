package me.model.items;

import java.util.HashMap;
import java.util.Map;
import me.model.Character;
import me.view.Messages;

public class Inventory {

       private final static String EMPTY_INV = "Your inventory is empty.";
       private final static int INIT_INV_CAP = 10;
       private final Map <ItemType, Map<Item, Integer>> inventory;
       private int inventory_cap;

       public Inventory(){
              inventory_cap = INIT_INV_CAP;
              inventory = new HashMap<>(inventory_cap);
       }

       public boolean isEmpty(){
              return inventory.isEmpty();
       }
       public int size(){
              int acc = 0;
              for(Map.Entry <ItemType, Map<Item, Integer>> entry : inventory.entrySet()){
                     acc += entry.getValue().size();
              }
              return acc;
       }

       public boolean addItem(Item i){
              if(!isFull()){
                     ItemType it = i.getType();
                     if(!inventory.containsKey(it))
                            inventory.get(it).put(i, 1);
                     else  {
                            Integer j = inventory.get(it).get(i);
                            inventory.get(it).put(i, j + 1);
                     }
                     return true;
              }
              return false;
       }

       public void useFirstOfType(ItemType it){
              Item i = inventory.get(it).keySet().iterator().next();
              i.use();
              int aux = inventory.get(it).get(i);
              if(aux <= 1){
                     inventory.get(it).remove(i);
              }
              else{
                     inventory.get(it).put(i, aux - 1);
              }
       }

       public boolean useItem(Item i, Character c){  
              if(!inventory.isEmpty()){
                     i.use();
                     ItemType it = i.getType();
                     if(inventory.get(it).get(i) < 2)
                            inventory.remove(it);
                     else{
                            Integer j = inventory.get(it).get(i);
                            inventory.get(it).put(i, j - 1);
                     }
                     return true;
              }
              return false;
       }

       public boolean isFull(){
              return inventory.size() == inventory_cap;
       }

       public boolean dropItem(Item i){
              if(!inventory.isEmpty()){
                     ItemType it = i.getType();
                     if(inventory.get(it).get(i) < 2)
                            inventory.remove(it);
                     else{
                            Integer j = inventory.get(it).get(i);
                            inventory.get(it).put(i, j - 1);
                     }
                     return true;
              }
              return false;
       }

       public boolean containsType(ItemType it){
              return inventory.containsKey(it);
       }

       public boolean constains(Item i){
              return inventory.get(i.getType()).containsKey(i);
       }

       public String getInfo() {
              if (inventory.isEmpty()) {
                     return EMPTY_INV;
              }

              StringBuilder sb = new StringBuilder();

              for(Map.Entry<ItemType, Map<Item, Integer>> i : inventory.entrySet()){
                     for(Map.Entry<Item, Integer> it : i.getValue().entrySet()){
                            sb.append("> ").append(it.getKey().toString());
                            sb.append("  Cuantity: ").append(it.getValue()).append(Messages.NEW_LINE);
                     }
              }

              return sb.toString();
       }

       public void increaseCapacity(int mod){
              inventory_cap += mod;
       }
}
