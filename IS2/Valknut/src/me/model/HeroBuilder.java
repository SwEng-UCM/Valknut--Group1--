package me.model;

import java.util.ArrayList;
import java.util.List;

public class HeroBuilder {
    static public List<Hero> heroes;

    public HeroBuilder(){
        heroes = new ArrayList<>();
        heroes.add(buildHero("Freya"));
        heroes.add(buildHero("Loki"));
    }

    static public Hero buildHero(String str){
        List<Integer> element = new ArrayList<>();
        switch (str.toLowerCase()) {
            case "freya":
                Hero freya = new Hero("Froilan", 100, "Beloved Child of Freya");
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                freya.setElementStats(element);
                return freya;
            case "loki":
                Hero loki = new Hero("Laki", 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                loki.setElementStats(element);
                return loki;
            default:
                Hero hero = new Hero("Laki", 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                hero.setElementStats(element);
                return hero;
        }
    }

    public void getPossibleHeroes(){
        int i = 1;
        for(Hero e: heroes){
            System.out.print(i++ + " --> ");
            e.print();
            e.printElements();
            System.out.println();
        }
    }
}
