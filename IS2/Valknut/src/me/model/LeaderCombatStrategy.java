/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model;

import java.util.List;

public class LeaderCombatStrategy implements CombatStrategy {

    @Override
    public int execute(Element element, List<Enemy> enemies, int tarjet) {
        boolean first = true;
        int idx = 0;
        Enemy eTarjet = enemies.get(tarjet);
        for(Enemy e : enemies){
            if(e.isWeak(element)){
                if(first){
                    eTarjet = e;
                    tarjet = idx;
                    first = false;
                }
                else{
                    if(eTarjet.getLife() < e.getLife())
                        tarjet = idx;
                }
                
            }
            idx++;
        }

        int size = enemies.size();
        if(tarjet < 0 ) tarjet = 0;
        if(tarjet >= size)
            tarjet = size - 1;

        return tarjet + 1;
    }
    
}
