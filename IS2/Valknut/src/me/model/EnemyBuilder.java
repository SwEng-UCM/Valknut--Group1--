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
            case "ice" -> {
                Enemy iceGiant = new Enemy(Messages.ICEGIANT, 50, 200, 60, Messages.ICEGIANT_ATTACK);
                element.add(2); element.add(1); element.add(1); element.add(1); element.add(1);
                iceGiant.setElementStats(element);
                iceGiant.setSprite("resources/images/Creatures/ice_giant.png");
                return iceGiant;
            }
            case "fire" -> {
                Enemy fireGiant = new Enemy(Messages.FIREGIANT, 50, 200, 70, Messages.FIREGIANT_ATTACK);
                element.add(1); element.add(1); element.add(1); element.add(1); element.add(2);
                fireGiant.setElementStats(element);
                fireGiant.setSprite("resources/images/Creatures/fire_giant.png");
                return fireGiant;
            }
            case "berserk" -> {
            	Enemy berserk = new Enemy(Messages.BERSERKER, 100, 150, 55, Messages.BERSERKER_ATTACK);
                element.add(1); element.add(1); element.add(1); element.add(2); element.add(1);
                berserk.setElementStats(element);
                berserk.setSprite("resources/images/Creatures/berserker.png");
                return berserk;
            }
            case "shifter" -> {
            	Enemy shifter = new Enemy(Messages.SHAPESHIFTER, 200, 250, 65, Messages.SHAPESHIFTER_ATTACK);
                element.add(1); element.add(2); element.add(1); element.add(1); element.add(1);
                shifter.setElementStats(element);
                shifter.setSprite("resources/images/Creatures/bear_animal_shifter.png");
                return shifter;
            }
            case "elf" -> {
            	Enemy elf = new Enemy(Messages.ELF, 125, 150, 55, Messages.ELF_ATTACK);
                element.add(1); element.add(1); element.add(2); element.add(1); element.add(1);
                elf.setElementStats(element);
                elf.setSprite("resources/images/Creatures/elf.png");
                return elf;
            }
            case "draug" -> {
            	Enemy draugr = new Enemy(Messages.DRAUGR, 100, 120, 60, Messages.DRAUGR_ATTACK);
                element.add(1); element.add(3); element.add(1); element.add(1); element.add(1);
                draugr.setElementStats(element);
                draugr.setSprite("resources/images/Creatures/dragur.png");
                return draugr;
            }
            case "fafnir" -> {
            	Enemy fafnir = new Enemy(Messages.FAFNIR, 450, 550, 300, Messages.FAFNIR_ATTACK);
                element.add(1); element.add(1); element.add(1); element.add(1); element.add(5);
                fafnir.setElementStats(element);
                fafnir.setSprite("resources/images/Creatures/fafnir.png");
                return fafnir;
            }
            case "skoll" -> {
            	Enemy skoll = new Enemy(Messages.SKOLL, 350, 450, 250, Messages.SKOLL_ATTACK);
                element.add(1); element.add(4); element.add(1); element.add(1); element.add(1);
                skoll.setElementStats(element);
                skoll.setSprite("resources/images/Creatures/skoll.png");
                return skoll;
            }
            case "hati" -> {
            	Enemy hati = new Enemy(Messages.HATI, 350, 450, 250, Messages.HATI_ATTACK);
                element.add(1); element.add(4); element.add(1); element.add(1); element.add(1);
                hati.setElementStats(element);
                hati.setSprite("resources/images/Creatures/hati.png");
                return hati;
            }
            default -> {
                Enemy df = new Enemy(Messages.ICEGIANT, 150, 200, 60, Messages.ICEGIANT_ATTACK);
                element.add(2); element.add(1); element.add(1); element.add(1); element.add(1);
                df.setElementStats(element);
                return df;
            }
        }
    }
}
