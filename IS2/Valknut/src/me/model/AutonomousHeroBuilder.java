/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model;

import java.util.ArrayList;
import java.util.List;
import me.model.items.DamageItem;
import me.model.items.HealingItem;
import me.model.items.ItemType;
import me.model.items.ResistanceItem;

public class AutonomousHeroBuilder {
    
    static public AutonomousHero buildAutonomousHero(HeroEnum he, int id){
        List<Integer> element = new ArrayList<>();
        AutonomousHero new_hero;
        switch (he) {
            case GERSEMI -> {
                new_hero = new AutonomousHero("Gersemi", 100, 100, "Beloved Child of Freya", id);
                element.add(1); element.add(2); element.add(3); element.add(1); element.add(2);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/gersemi.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_gersemi.png");
            }
            case VALI -> {
                new_hero = new AutonomousHero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/vali.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_vali.png");
            }
            case JORUNN -> {
                new_hero = new AutonomousHero("Jorunn", 100, 100, "Silent Child of Skadi", id);
                element.add(3); element.add(1); element.add(2); element.add(2); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/jorunn.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_jorunn.png");
            }
            case VIGGO -> {
                new_hero = new AutonomousHero("Viggo", 100, 100, "Furious Child of Vidar", id);
                element.add(1); element.add(2); element.add(1); element.add(2); element.add(3);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/viggo.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_viggo.png");
            }
            case MAGNI -> {
                new_hero = new AutonomousHero("Magni", 100, 100, "Mortal Child", id);
                element.add(1); element.add(2); element.add(2); element.add(3); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/magni.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_magni.png");
            }
            default -> {
                new_hero = new AutonomousHero("Váli", 100, 100, "Forgotten Child of Loki", id);
                element.add(2); element.add(3); element.add(2); element.add(1); element.add(1);
                new_hero.setElementStats(element);
                new_hero.setSprite("resources/images/Characters/vali.png");
                new_hero.setInfectedSprite("resources/images/Characters/cursed_vali.png");
            }
        }

        addTestItems(new_hero);

        return new_hero;
    }

    private static void addTestItems(Hero new_hero){
        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
        new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, ItemType.DAMAGE));

    }

}
