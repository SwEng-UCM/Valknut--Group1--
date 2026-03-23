package me.control;

import java.util.List;

import me.model.*;
import me.model.items.*;
import me.view.*;


public class Controller {

    private static StoryView sv;
	private static CombatView cv;
	private static MenuView mv;
    private static Controller instance;
    private Combat cb;
    private CtrlPanel controlPanel = new CtrlPanel(this);
    int turn = 1;

    private Controller(){
        sv = StoryView.getInstance();
		cv = CombatView.getInstance();
		mv = MenuView.getInstance();
    }

    public void run(){
    	cb = initCmb();
    	controlPanel.onGameStart();
    }
    
    public void startStory() {
        tellIntro();
        
        tellFirstLinesChapterOne();
        
        //enterCombat();
    }

    public static Controller getInstance(){

        if(instance == null){
            instance = new Controller();
        }

        return instance;
    }

    private void enterCombat(){
        while(!cb.exit()){
			playTurn();
		}
    }
    
    

    public Combat initCmb(){
		Combat cmb = new Combat();
		for(int i = 1; i < 3; i++){
			cv.printLine("Player " + i + " selects..." + Messages.NEW_LINE);
			cv.printLine("");
			cmb.addEnemy(firstEnemies(i));
            cmb.addEnemy(firstEnemies(i));
		}
		return cmb;
	}

	public void playTurn() {
        if (cb.turn() == 1) {
            boolean finished = false;
            cb.updateItems();
            for(Hero e: cb.getHeroes()){
                if(e.isAlive() && !e.escaped() && !cb.getEnemies().isEmpty()){
                    cv.turnHeader(e);
                    CombatOption co;
                    while(!finished){
                        co = cv.selectAction(e);
                        cv.printLine(cb.update());
                    }
                }
                finished = false;
                cb.setTurn(cb.turn() + 1); //following this idea of turns, sometimes you will want to know the turn, 
                    //to increase the turn by one or to set the turn
                    //to a certain value. So, functions turn() and setTurn() are needed
                    //Function incTurn() is considered to be setTurn(turn() + 1);
            }
        } else {
            cv.print(cb.enemyTurnToString());
            for(Enemy e: cb.getEnemies()){
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }
            cv.printLine(cb.update());
            cb.setTurn(1);
        }
        cv.pause();
    }

    public boolean action(int combatOption, int target){
        boolean f = false;
        Hero current_hero;
        if (turn % 2 == 1) {
        	current_hero = cb.getHeroes().get(0);
        }
        else {
        	current_hero = cb.getHeroes().get(1);
        }
        
        switch(combatOption){
            case 1 -> {cv.printLine(cb.attack(target)); f = true;}
            case 2 -> {cv.printLine(cb.defend()); f = true;}
            case 3 -> cv.printLine(cb.useItem(current_hero, cv.selectItem(current_hero.displayInventory(), current_hero)));
            case 4 -> {cv.printLine(cb.run()); f = true;}
            case 5 -> cv.print(cb.showStats(current_hero));
            default -> {
            }
        }
        
        if (combatOption != 5) turn++;
        
        controlPanel.onSelection();
        
        return f;
    }

    public void selectCharacter(int i){
    	Hero new_hero;
		if(i == 0)
			new_hero = HeroBuilder.buildHero("Freya");
		else
			new_hero = HeroBuilder.buildHero("Loki");
		new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, Attribute.RESISTANCE));
		new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, Attribute.RESISTANCE));
		new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, null));
		new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, null));
		new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, null));
		new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, null));
		new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, Attribute.STRENGTH));
		cb.addHero(new_hero);
		
		if (cb.getHeroes().size() > 1) {
			controlPanel.onSelection();
			startStory();
		}
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

    public void tellFirstLinesChapterOne(){
        sv.clear();
        sv.tellFirstLinesChapterOne(cb.getHeroes().get(0), cb.getHeroes().get(1));
    }
}
