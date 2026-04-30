package me.model;

import java.util.ArrayList;
import java.util.List;
import me.model.items.DamageItem;
import me.model.items.HealingItem;
import me.model.items.ItemType;
import me.model.items.ResistanceItem;
import me.view.Messages;

public class HeroBuilder {
    static public List<Hero> heroes;

    public HeroBuilder(){
        heroes = new ArrayList<>();
        heroes.add(buildHero("Freya", 0));
        heroes.add(buildHero("Loki", 0));
    }

    static public Hero selectCharacter(int i, int player) {
        Hero new_hero;

        if (i == 0)
            new_hero = HeroBuilder.buildHero("Freya", player);
        else 
            new_hero = HeroBuilder.buildHero("Loki", player);

        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
        new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, ItemType.DAMAGE));

        // cb.addHero(new_hero);
        // new_hero.setCombat(cb);

        return new_hero;
    }

    static public Hero buildHero(String str, int id){
        List<Integer> element = new ArrayList<>();
        switch (str.toLowerCase()) {
            case "freya" -> {
                Hero freya = new Hero("Gersemi", 100, 100, "Beloved Child of Freya", id);
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                freya.setElementStats(element);
                freya.setSprite("resources/images/Characters/newgersemi.png");
                return freya;
            }
            case "loki" -> {
                Hero loki = new Hero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                loki.setElementStats(element);
                loki.setSprite("resources/images/Characters/newvali.png");
                return loki;
            }
            case "skadi" -> {
                Hero skadi = new Hero("Jorunn", 100, 100, "Silent Child of Skadi", id);
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                skadi.setElementStats(element);
                skadi.setSprite("resources/images/Characters/jorunn.png");
                return skadi;
            }
            case "vidar" -> {
                Hero vidar = new Hero("Viggo", 100, 100, "Furious Child of Vidar", id);
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                vidar.setElementStats(element);
                vidar.setSprite("resources/images/Characters/viggo.png");
                return vidar;
            }
            case "mortal" -> {
                Hero mortal = new Hero("Magni", 100, 100, "Mortal Child", id);
                element.add(1); element.add(2); element.add(2); element.add(3); element.add(1);
                mortal.setElementStats(element);
                mortal.setSprite("resources/images/Characters/magni.png");
                return mortal;
            }
            default -> {
                Hero hero = new Hero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                hero.setElementStats(element);
                return hero;
            }
        }
    }

    static public AutonomousHero buildAutonomousHero(String str, int id){
        List<Integer> element = new ArrayList<>();
        switch (str.toLowerCase()) {
            case "freya" -> {
                AutonomousHero freya = new AutonomousHero("Gersemi", 100, 100, "Beloved Child of Freya", id);
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                freya.setElementStats(element);
                freya.setSprite("resources/images/Characters/newgersemi.png");
                return freya;
            }
            case "loki" -> {
                AutonomousHero loki = new AutonomousHero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                loki.setElementStats(element);
                loki.setSprite("resources/images/Characters/newvali.png");
                return loki;
            }
            case "skadi" -> {
                AutonomousHero skadi = new AutonomousHero("Jorunn", 100, 100, "Silent Child of Skadi", id);
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                skadi.setElementStats(element);
                skadi.setSprite("resources/images/Characters/jorunn.png");
                return skadi;
            }
            case "vidar" -> {
                AutonomousHero vidar = new AutonomousHero("Viggo", 100, 100, "Furious Child of Vidar", id);
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                vidar.setElementStats(element);
                vidar.setSprite("resources/images/Characters/viggo.png");
                return vidar;
            }
            case "mortal" -> {
                AutonomousHero mortal = new AutonomousHero("Magni", 100, 100, "Mortal Child", id);
                element.add(1); element.add(2); element.add(2); element.add(3); element.add(1);
                mortal.setElementStats(element);
                mortal.setSprite("resources/images/Characters/magni.png");
                return mortal;
            }
            default -> {
                AutonomousHero hero = new AutonomousHero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                hero.setElementStats(element);
                return hero;
            }
        }
    }
    
    public static void setUserHero(Hero e, String s){
        List<Integer> element = new ArrayList<>();
        switch (s.toLowerCase()) {
            case "freya" -> {
                e.setHero("Gersemi", 100, 100, "Beloved Child of Freya");
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/newgersemi.png");
            }
            case "loki" -> {
                e.setHero("Váli", 100, 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/newvali.png");
            }
            case "skadi" -> {
                e.setHero("Jorunn", 100, 100, "Silent Child of Skadi");
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/jorunn.png");
            }
            case "vidar" -> {
                e.setHero("Viggo", 100, 100, "Furious Child of Vidar");
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/viggo.png");
            }
            case "mortal" -> {
                e.setHero("Magni", 100, 100, "Mortal Child");
                element.add(1); element.add(2); element.add(2); element.add(3); element.add(1);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/magni.png");
            }
            default -> {
                e.setHero("Váli", 100, 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/newvali.png");
            }
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
