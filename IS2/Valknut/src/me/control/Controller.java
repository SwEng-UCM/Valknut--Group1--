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
    private CtrlPanel controlPanel = new CtrlPanel(this);
    private int num_enemies;

    private Controller(){
        sv = StoryView.getInstance();
		cv = CombatView.getInstance();
		mv = MenuView.getInstance();
    }
    
    public int getNumEnemies() {
    	return num_enemies;
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
    
    public Combat initCmb(){
		Combat cmb = new Combat();
		for(int i = 1; i < 3; i++){
			cv.printLine("Player " + i + " selects..." + Messages.NEW_LINE);
			cv.printLine("");
			cmb.addEnemy(firstEnemies(i));
            cmb.addEnemy(firstEnemies(i));
            num_enemies += 2;
		}
		return cmb;
	}

    public boolean action(int combatOption, int target){
    	boolean f = false;
        CombatOption co;
    	
    	cb.updateItems();
        
        Hero current_hero;
        if (cb.turn() == 1) {
        	current_hero = cb.getHeroes().get(0);
        }
        else {
        	current_hero = cb.getHeroes().get(1);
        }

        if(current_hero.isAutonomous()){
            AutonomousHero au = (AutonomousHero) current_hero;
            co = CombatOption.parseCommand(au.selectAction());
            switch(co){
                case ATTACK -> {cv.printLine(cb.attack(target)); f = true;}
                case DEFEND -> {cv.printLine(cb.defend()); f = true;}
                case USE_ITEM -> cv.printLine(cb.useItem(current_hero, cv.selectItem(current_hero.displayInventory(), current_hero)));
                case RUN -> {cv.printLine(cb.run()); f = true;}
                case STATS -> cv.print(cb.showStats(current_hero));
                default -> {
                }
            }
        }
        else if (current_hero.isAlive() && !current_hero.escaped() && !cb.getEnemies().isEmpty())
        switch(combatOption){
            case 1 -> {cv.printLine(cb.attack(target)); f = true;}
            case 2 -> {cv.printLine(cb.defend()); f = true;}
            case 3 -> cv.printLine(cb.useItem(current_hero, cv.selectItem(current_hero.displayInventory(), current_hero)));
            case 4 -> {cv.printLine(cb.run()); f = true;}
            case 5 -> cv.print(cb.showStats(current_hero));
            default -> {
            }
        }
        
        if (combatOption != 5){} cb.setTurn(cb.turn() + 1);
        
        if (cb.turn() == 3) {
        	cv.print(cb.enemyTurnToString());
            for(Enemy e: cb.getEnemies()){
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }
            cb.setTurn(1);
            cv.printLine(cb.update());
        }
        
        controlPanel.onSelection();
        
        return f;
    }

    public void selectCharacter(int i){
    	Hero new_hero;
		if(i == 0)
			new_hero = HeroBuilder.buildHero("Freya");
		else
			new_hero = HeroBuilder.buildHero("Loki");
		new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
		new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
		new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
		new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
		new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
		new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
		new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, ItemType.DAMAGE));
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
