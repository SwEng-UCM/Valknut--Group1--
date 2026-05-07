/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model;

import java.util.List;

public class FollowerCombatStrategy implements CombatStrategy{

    @Override
    public int execute(Element element, List<Enemy> enemies, int tarjet) {
        int size = enemies.size();
        if(tarjet < 0 ) tarjet = 0;
        if(tarjet >= size)
            tarjet = size - 1;
       return tarjet + 1;
    }
    
}
