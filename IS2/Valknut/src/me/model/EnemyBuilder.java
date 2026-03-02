package me.model;

import java.util.ArrayList;
import java.util.List;

public class EnemyBuilder {
    static public List <Enemy> enemies;

    public EnemyBuilder(){
        enemies = new ArrayList<>(5);
    }

    static public Enemy buildEnemy(String str){
        List<Integer> element = new ArrayList<>();
        switch (str.toLowerCase()) {
            case "ice":
                Enemy iceGiant = new Enemy("Ice Giant", 100, 100);
                element.add(2); element.add(1); element.add(1); element.add(1); element.add(1);
                iceGiant.setElementStats(element);
                return iceGiant;
            case "fire":
                Enemy loki = new Enemy("Fire Giant", 100, 100);
                element.add(1); element.add(1); element.add(1); element.add(1); element.add(2);
                loki.setElementStats(element);
                return loki;
            default:
                Enemy df = new Enemy("Ice Giant", 100, 100);
                element.add(2); element.add(1); element.add(1); element.add(1); element.add(1);
                df.setElementStats(element);
                return df;
        }
    }
}
