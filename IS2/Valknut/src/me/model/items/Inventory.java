package me.model.items;

import java.util.ArrayList;
import java.util.List;
import me.model.Character;

public class Inventory {

       private final static String EMPTY_INV = "Your inventory is empty.";
       private final static int INIT_INV_CAP = 10;
       private List <Item> inventory;
       private int inventory_cap;

       public Inventory(){
              inventory_cap = INIT_INV_CAP;
              inventory = new ArrayList<>(inventory_cap);
       }

       public boolean addItem(Item i){
              if(inventory.size() < inventory_cap){
              inventory.add(i);
              return true;
              }
              return false;
       }

       public boolean useItem(int idx, Character c){ //This is thought to use after selecting the item from a visual inventory 
       // where the user enters the number position in the inventory 
              if(!inventory.isEmpty()){
                     inventory.get(idx).use(c);
                     inventory.remove(idx);
                     return true;
              }
              return false;
       }

       public boolean deleteItem(int idx){
              if(!inventory.isEmpty()){
                     inventory.remove(idx);
                     return true;
              }
              return false;
       }

       public String getInfo() {
              if (inventory.isEmpty()) {
                     return EMPTY_INV;
              }

              StringBuilder sb = new StringBuilder();
              int j = 1;
              for (Item i : inventory) {
                     sb.append(j)
                     .append(". ")
                     .append(i.getName())
                     .append(" - ")
                     .append(i.getValue())
                     .append(System.lineSeparator()); 
                     j++;
              }
              return sb.toString();
       }

       public void increaseCapacity(int mod){
              inventory_cap += mod;
       }
}
