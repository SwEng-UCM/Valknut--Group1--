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
		Combat cmb = new Combat(cv);
		for(int i = 1; i < 3; i++){
			cv.printLine("Player " + i + " selects..." + Messages.NEW_LINE);
			Hero e = selectCharacter();
			e.addItem(new ShieldItem("Iron Armor Piece", 5, 1, 8));
			e.addItem(new ShieldItem("Iron Armor Piece", 5, 1, 8));
			e.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20,1));
			e.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1));
			e.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1));
			e.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1));
			e.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8));
			cmb.addHero(e);
			cv.printLine("");
			cmb.addEnemy(firstEnemies(i));
			cmb.addEnemy(EnemyBuilder.buildEnemy("Ice"));
		}
		return cmb;
	}

	public void playTurn() {
        CombatOption co;
        if (cb.turn() == 1) {
            for(Hero e: cb.getHeroes()){
                if(e.isAlive() && !e.escaped() && !cb.getEnemies().isEmpty()){
                    co = cv.selectAction(e);
                    action(e, co);
                    cb.update();
                }
                cb.setTurn(cb.turn() + 1); //following this idea of turns, sometimes yoou will want to know the turn, to increase the turn by one or to set the turn
                //to a certain value. So, functions turn() and setTurn() are needed. Function incTurn() is considered to be setTurn(turn() + 1);
            }
        } else {
            cv.printLine(cb.enemyTurnToString());
            for(Enemy e: cb.getEnemies()){
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }
            cb.update();
            cb.setTurn(1);
        }
    }

    public void action(Hero h, CombatOption co){
        switch(co){
            case ATTACK -> cb.attack(cv.selectTarject(cb.heroTargetsToString(), cb.getEnemies().size()));
            case DEFEND -> cb.defend();
            case USE_ITEM -> cb.useItem(h);
            case RUN -> cv.printLine(cb.run());
            case STATS -> cv.printLine(cb.showStats(h));
            default -> {
            }
        }
    }

    public Hero selectCharacter(){
		Integer i = cv.selectCharacter();
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
