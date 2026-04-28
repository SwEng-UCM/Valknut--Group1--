package me.model.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.model.Character;
import me.model.CombatOption;
import me.view.Messages;

public class Inventory implements Serializable {
       private static final long serialVersionUID = 1L;

       private final static String EMPTY_INV = "Your inventory is empty.";
       private final static int INIT_INV_CAP = 10;
       private final Map <Item, Map<String, Integer>> inventory;
       private int inventory_cap;

       public Inventory(){
              inventory_cap = INIT_INV_CAP;
              inventory = new HashMap<>(5);
       }

       public boolean isEmpty(){
              return inventory.isEmpty();
       }
       public int size(){
              int acc = 0;
              for(Map.Entry <Item, Map<String, Integer>> entry : inventory.entrySet()){
                     acc += entry.getValue().size();
              }
              return acc;
       }

       public boolean addItem(Item i){
              if(!isFull()){
                     String name = i.getName();
                     if(!inventory.containsKey(i)){
                            Map <String, Integer> x = new HashMap<>();
                            x.put(name, 1);
                            inventory.put(i, x);
                     }
                     else  {
                            Integer j = inventory.get(i).get(name);
                            inventory.get(i).put(name, j + 1);
                     }
                     return true;
              }
              return false;
       }

       public boolean useFirstOfType(Item it){
              String name = it.getName();
              boolean used = inventory.get(it).containsKey(name);
              if(used){
                     Item item = it.createInstanceOf(name);
                     item.use();
                     int aux = inventory.get(it).get(name);
                     if(aux <= 1){
                            inventory.get(it).remove(name);
                     }
                     else{
                            inventory.get(it).put(name, aux - 1);
                     }
              }

              return used;
       }

       public boolean useItem(Item i, Character c){  
              if(!inventory.isEmpty()){
                     i.use();
                     String name = i.getName();
                     if(inventory.get(i).get(name) < 2)
                            inventory.remove(i);
                     else{
                            Integer j = inventory.get(i).get(name);
                            inventory.get(i).put(name, j - 1);
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
                     String name = i.getName();
                     if(inventory.get(i).get(name) < 2)
                            inventory.remove(i);
                     else{
                            Integer j = inventory.get(i).get(name);
                            inventory.get(i).put(name, j - 1);
                     }
                     return true;
              }
              return false;
       }

       public boolean containsType(Item i){
              return inventory.containsKey(i);
       }

       public boolean containsItem(Item i, String s){
              return inventory.get(i).containsKey(s);
       }

       public String getInfo() {
              if (inventory.isEmpty()) {
                     return EMPTY_INV;
              }

              StringBuilder sb = new StringBuilder();

              for(Map.Entry<Item, Map<String, Integer>> i : inventory.entrySet()){
                     for(Map.Entry<String, Integer> it : i.getValue().entrySet()){
                            sb.append("> ").append(i.toString());
                            sb.append("  Cuantity: ").append(it.getValue()).append(Messages.NEW_LINE);
                     }
              }

              return sb.toString();
       }
       
       public List<Item> getItems() {
    	   List<Item> items = new ArrayList<>(inventory.size());
    	   
    	   for(Item i : inventory.keySet()){
               items.add(i);
    	   }
    	   
    	   return items;
       }

       public void increaseCapacity(int mod){
              inventory_cap += mod;
       }
}
