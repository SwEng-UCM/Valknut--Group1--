package me.model;

import java.util.ArrayList;
import java.util.List;
import me.view.Messages;

public class EnemyBuilder {
    static public List <Enemy> enemies;

    public EnemyBuilder(){
        enemies = new ArrayList<>(5);
    }

    static public Enemy buildEnemy(String str){
        List<Integer> element = new ArrayList<>();
        switch (str.toLowerCase()) {
            case "ice":
                Enemy iceGiant = new Enemy(Messages.ICEGIANT, 100, 100, 60, Messages.ICEGIANT_ATTACK);
                element.add(2); element.add(1); element.add(1); element.add(1); element.add(1);
                iceGiant.setElementStats(element);
                return iceGiant;
            case "fire":
                Enemy fireGiant = new Enemy(Messages.FIREGIANT, 100, 100, 70, Messages.FIREGIANT_ATTACK);
                element.add(1); element.add(1); element.add(1); element.add(1); element.add(2);
                fireGiant.setElementStats(element);
                return fireGiant;
            default:
                Enemy df = new Enemy(Messages.ICEGIANT, 100, 100, 60, Messages.ICEGIANT_ATTACK);
                element.add(2); element.add(1); element.add(1); element.add(1); element.add(1);
                df.setElementStats(element);
                return df;
        }
    }
}
