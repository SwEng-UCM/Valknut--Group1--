package me.model;

import java.util.ArrayList;
import java.util.List;
import me.model.items.DamageItem;
import me.model.items.HealingItem;
import me.model.items.ItemType;
import me.model.items.ResistanceItem;

public class HeroBuilder {

    static public Hero buildHero(HeroEnum he, int id){
        List<Integer> element = new ArrayList<>();
        Hero new_hero;
        switch (he) {
            case GERSEMI -> {
                new_hero = new Hero("Gersemi", 100, 100, "Beloved Child of Freya", id);
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/gersemi.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_gersemi.png");
            }
            case VALI -> {
                new_hero = new Hero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/vali.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_vali.png");
            }
            case JORUNN -> {
                new_hero = new Hero("Jorunn", 100, 100, "Silent Child of Skadi", id);
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/jorunn.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_jorunn.png");
            }
            case VIGGO -> {
                new_hero = new Hero("Viggo", 100, 100, "Furious Child of Vidar", id);
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/viggo.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_viggo.png");
            }
            case MAGNI -> {
                new_hero = new Hero("Magni", 100, 100, "Mortal Child", id);
                element.add(1); element.add(2); element.add(2); element.add(3); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/magni.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_magni.png");
            }
            default -> {
                new_hero = new Hero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                new_hero.setSprite("resources/images/Characters/newvali.png");
                new_hero.setElementStats(element);
            }
        }

        addTestItems(new_hero);

        return new_hero;
    }
    
    public static void setUserHero(HeroEnum he, Hero e){
        List<Integer> element = new ArrayList<>();
        switch (he) {
            case GERSEMI -> {
                e.setHero("Gersemi", 100, 100, "Beloved Child of Freya");
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/newgersemi.png");
            }
            case VALI -> {
                e.setHero("Váli", 100, 100, "Forgotten Child of Loki");
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/newvali.png");
            }
            case JORUNN -> {
                e.setHero("Jorunn", 100, 100, "Silent Child of Skadi");
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/jorunn.png");
            }
            case VIGGO -> {
                e.setHero("Viggo", 100, 100, "Furious Child of Vidar");
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                e.setElementStats(element);
                e.setSprite("resources/images/Characters/viggo.png");
            }
            case MAGNI -> {
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

        addTestItems(e);
    }

    private static void addTestItems(Hero new_hero){
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
    }
}


