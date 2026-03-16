package me.control;

import me.model.*;
import me.model.items.*;
import me.view.*;

public class Controller {

    private static StoryView sv;
	private static CombatView cv;
	private static MenuView mv;
    private static Controller instance;
    private Combat cb;

    private Controller(){
        sv = StoryView.getInstance();
		cv = CombatView.getInstance();
		mv = MenuView.getInstance();
    }

    public void run(){
        tellIntro();
        enterCombat();
        
    }

    public static Controller getInstance(){

        if(instance == null){
            instance = new Controller();
        }

        return instance;
    }

    private void enterCombat(){
        cb = initCmb();
        while(!cb.exit()){
			playTurn();
		}
    }

    public Combat initCmb(){
		Combat cmb = new Combat();
		for(int i = 1; i < 3; i++){
			cv.printLine("Player " + i + " selects..." + Messages.NEW_LINE);
			Hero e = selectCharacter();
			e.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, Attribute.RESISTANCE));
			e.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, Attribute.RESISTANCE));
			e.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, null));
			e.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, null));
			e.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, null));
			e.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, null));
			e.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, Attribute.STRENGTH));
			cmb.addHero(e);
			cv.printLine("");
			cmb.addEnemy(firstEnemies(i));
		}
		return cmb;
	}

	public void playTurn() {
        if (cb.turn() == 1) {
            boolean finished = false;
            cb.updateItems();
            for(Hero e: cb.getHeroes()){
                cv.turnHeader(e);
                CombatOption co;
                while(!finished){
                    if(e.isAlive() && !e.escaped() && !cb.getEnemies().isEmpty()){
                        co = cv.selectAction(e);
                        finished = action(e, co);
                        cv.print(cb.update());
                    }
                }
                cb.setTurn(cb.turn() + 1); //following this idea of turns, sometimes you will want to know the turn, 
                    //to increase the turn by one or to set the turn
                    //to a certain value. So, functions turn() and setTurn() are needed
                    //Function incTurn() is considered to be setTurn(turn() + 1);
            }
        } else {
            cv.pause();
            cv.print(cb.enemyTurnToString());
            for(Enemy e: cb.getEnemies()){
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }
            cv.print(cb.update());
            cb.setTurn(1);
        }
    }

    public boolean action(Hero h, CombatOption co){
        boolean f = false;
        switch(co){
            case ATTACK -> {cv.printLine(cb.attack(cv.selectTarject(cb.heroTargetsToString(), cb.getEnemies().size()))); f = true;}
            case DEFEND -> {cv.printLine(cb.defend()); f = true;}
            case USE_ITEM -> cb.useItem(h, cv.selectItem(h.displayInventory(), h));
            case RUN -> {cv.printLine(cb.run()); f = true;}
            case STATS -> cv.print(cb.showStats(h));
            default -> {
            }
        }
        return f;
    }

    public Hero selectCharacter(){
		Integer i = sv.selectCharacter();
		if(i == 1)
			return HeroBuilder.buildHero("Freya");
		else
			return HeroBuilder.buildHero("Loki");
	}

    public Enemy firstEnemies(Integer i){
		if(i == 1)
			return EnemyBuilder.buildEnemy("Ice");
		else
			return EnemyBuilder.buildEnemy("Fire");
	}

    public void tellIntro() {
		sv.tellIntro();
	}
}
