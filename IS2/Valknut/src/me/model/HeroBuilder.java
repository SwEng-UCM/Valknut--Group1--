package me.model;

import java.util.ArrayList;
import java.util.List;
import me.view.Messages;

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
                Hero freya = new Hero("Gersemi", 100, 100, "Beloved Child of Freya");
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                freya.setElementStats(element);
                freya.setSprite("resources/images/Characters/newgersemi.png");
                return freya;
            case "loki":
                Hero loki = new Hero("Váli", 100, 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                loki.setElementStats(element);
                loki.setSprite("resources/images/Characters/newvali.png");
                return loki;
            case "skadi":
                Hero skadi = new Hero("Jorunn", 100, 100, "Silent Child of Skadi");
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                skadi.setElementStats(element);
                skadi.setSprite("resources/images/Characters/jorunn.png");
                return skadi;
            case "vidar":
                Hero vidar = new Hero("Viggo", 100, 100, "Furious Child of Vidar");
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                vidar.setElementStats(element);
                vidar.setSprite("resources/images/Characters/viggo.png");
                return vidar;
            case "mortal":
                Hero mortal = new Hero("Magni", 100, 100, "Mortal Child");
                element.add(1); element.add(2); element.add(2); element.add(3); element.add(1);
                mortal.setElementStats(element);
                mortal.setSprite("resources/images/Characters/magni.png");
                return mortal;
            default:
                Hero hero = new Hero("Váli", 100, 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                hero.setElementStats(element);
                return hero;
        }
    }

    public List<Hero> getHeroes(){
        return heroes;
    }

    public String getPossibleHeroes(){
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for(Hero e: heroes)
            sb.append(i++).append(" --> ").append(e.toString()).append(Messages.NEW_LINE).append(e.getStringElements()).append(Messages.NEW_LINE);

        return sb.toString();
    }
}
