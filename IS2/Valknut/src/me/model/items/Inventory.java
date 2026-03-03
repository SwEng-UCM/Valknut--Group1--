package me.model.items;

import java.util.HashMap;
import java.util.Map;
import me.model.Character;
import me.view.Messages;

public class Inventory {

       private final static String EMPTY_INV = "Your inventory is empty.";
       private final static int INIT_INV_CAP = 10;
       private Map <String, Item> inventory;
       private int inventory_cap;

       public Inventory(){
              inventory_cap = INIT_INV_CAP;
              inventory = new HashMap<>(inventory_cap);
       }

       public boolean addItem(Item i){
              if(!isFull()){
                     if(!inventory.containsKey(i.getName()))
                            inventory.put(i.getName(), i);  
                     inventory.get(i.getName()).addCuantity();
                     return true;
              }
              return false;
       }

       public boolean useItem(Item i, Character c){  
              if(!inventory.isEmpty()){
                     i.use();
                     if(inventory.get(i.getName()).getCuantity() < 2)
                            inventory.remove(i.getName());
                     else{
                            inventory.put(i.getName(), i);
                            inventory.get(i.getName()).subCuantity();
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
                     if(inventory.get(i.getName()).getCuantity() < 2)
                            inventory.remove(i.getName());
                     else
                            inventory.put(i.getName(), i);
                     return true;
              }
              return false;
       }

       public String getInfo() {
              if (inventory.isEmpty()) {
                     return EMPTY_INV;
              }

              StringBuilder sb = new StringBuilder();

              for(Map.Entry<String, Item> i : inventory.entrySet()){
                     sb.append("> ").append(i.getValue().toString());
                     sb.append("  Cuantity: ").append(i.getValue().getCuantity()).append(Messages.NEW_LINE);
              }

              return sb.toString();
       }

       public void increaseCapacity(int mod){
              inventory_cap += mod;
       }
}
