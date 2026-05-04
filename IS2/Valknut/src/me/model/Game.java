package me.model;

import java.util.ArrayList;
import java.util.List;
import me.command.Command;
import me.command.CommandFactory;
import me.model.items.*;
import me.model.save.*;
import me.socket.MultiplayerManager;
import me.view.CombatView;

public class Game {
    
    private static CombatView cv;
    private final Storyteller st;
    private final MultiplayerManager mpm;
    private me.control.Controller ctrl;
    private List<Hero> players;
    private Command lastUndoableCommand;
    private Combat cb;
    private Command lastCommand;
    private boolean finalBattle = false;
    private StringBuilder combatLog = new StringBuilder();

    public enum GameMode{
        SOLO, LOCAL, MULTIPLAYER;
    }

    private GameMode mode;

    public Game(me.control.Controller ctrl){
        cv = CombatView.getInstance(); //Temporally
        this.ctrl = ctrl;
        players = new ArrayList<>(4);
        mode = GameMode.LOCAL;
        st = new Storyteller(this);
        mpm = MultiplayerManager.getInstacne(ctrl, this);
        cb = new Combat(this);
    }

     public void initCmb() {
        cb.addEnemy(firstEnemies(1));
        cb.addEnemy(firstEnemies(2));
        cb.addEnemy(firstEnemies(1));
        cb.addEnemy(firstEnemies(2));
    }

    public void setMode(GameMode m){
        mode = m;
    }

    public boolean isMultiplayer(){
        return mode == GameMode.MULTIPLAYER;
    }
    
    public String consumeCombatLog() {
        String text = combatLog.toString();
        combatLog.setLength(0);
        return text;
    }

    public void addHero(Hero e){
        players.add(e);
        st.addHero(e);
    }
    
    public void setStoryHeroes() {
    	st.writeStory(getHeroes());
    }
    public int getNumEnemies() {
        return cb.getEnemies().size();
    }

    public int getTurn(){
        return cb != null ? cb.turn() : -1;
    }

    public List<Enemy> getEnemies(){
    	if(cb == null) {
    		System.out.println("cb null");
    	}
        return (cb == null ? null : cb.getEnemies());
    }
    
    public Inventory getHeroItems(){
        return (cb == null ? null : getCurrentHero().getInventory());
    }
    
    public void setEnemies(List<Enemy> newEnemies){
    	System.out.println("I'm game setting enemies");
    	System.out.println();
    	if(cb != null) {
    		System.out.println("I'm game setting enemies in cb" + newEnemies.getFirst().name());
    		cb.SetEnemies(newEnemies);
    	}
    }
    
    public List<Hero> getHeroes(){
        return (cb == null ? null : cb.getHeroes());
    }
    
    public List<Hero> getInfected(){
        return (cb == null ? null : cb.getInfected());
    }
    
    public boolean getFinalBattle() {
    	return finalBattle;
    }

    public GameMode getMode() {
        return mode;
    }

    public Combat getCombat() {
        return cb;
    }

    public Storyteller getStoryteller() {
        return st;
    }

    public String getCombatLogSnapshot() {
        return combatLog.toString();
    }

    public Enemy firstEnemies(Integer i) {
        if (i == 1) {
            return EnemyBuilder.buildEnemy("Ice");
        } else {
            return EnemyBuilder.buildEnemy("Fire");
        }
    }

    private Hero getCurrentHero() {
        if (cb == null || cb.getHeroes().isEmpty()) {
            return null;
        }
        
        if (!finalBattle) {
	        if (cb.turn() == 1 && cb.getHeroes().size() >= 1) {
	            return cb.getHeroes().get(0);
	        }
	
	        if (cb.turn() == 2 && cb.getHeroes().size() >= 2) {
	            return cb.getHeroes().get(1);
	        }
	
	        if (cb.turn() == 3 && cb.getHeroes().size() >= 3) {
	            return cb.getHeroes().get(2);
	        }
	        
	        if (cb.turn() == 4 && cb.getHeroes().size() >= 4) {
	            return cb.getHeroes().get(3);
	        }
        }
        
        else {
        	if (cb.turn() - 1 < cb.getHeroes().size()) return cb.getHeroes().get(cb.turn() - 1);
        	
        	else if (cb.turn() - cb.getHeroes().size() - 1< cb.getInfected().size()) return cb.getInfected().get(cb.turn() - 1 - cb.getHeroes().size());
        }
        
        return null;
    }

    private void executeEnemyTurn() {
    	String s = cb.update();
        combatLog.append(s);
        
        if (cb.turn() == cb.getHeroes().size() + 1) {	
            combatLog.append(cb.enemyTurnToString());
            
            int numEnemies = cb.getEnemies().size();
            for (int i = 0; i < numEnemies; i++) {
                // Set turn so cb.attack() knows which enemy is acting
                cb.setTurn(cb.getHeroes().size() + 1 + i);
                combatLog.append(cb.attack(0)); // 0 = enemy turn, target selected internally
            }

//            for (Enemy e : cb.getEnemies()) {
//                combatLog.append(cb.attack(0));
//                cb.setTurn(cb.turn() + 1);
//            }
            
            //System.out.println("FULL LOG:\n" + combatLog.toString());

            cb.setTurn(1);
        }
        
        if(cb.getExit()) {
            next();
        }
    }
    
    public void attackHero(Hero defender) {
    	combatLog.append(cb.attackHero(getCurrentHero(), defender));
    	cb.setTurn(cb.turn() + 1);
    	
    	if (cb.turn() >= cb.getHeroes().size() + cb.getInfected().size() + 1) cb.setTurn(1);
    }
    
    public void finalCombat() {
    	cb.setFinalCombat();
    	this.finalBattle = true;
    }
    
    public void setInfected(int infected) {
    	cb.setInfected(infected);
    }

    public boolean action(CombatOption combatOption, int target,Item item) {
        boolean finishedAction = false;

        if (combatOption == CombatOption.UNDO) {
            Command undoCommand = CommandFactory.createCommand(cb, cv, getCurrentHero(), combatOption, target, item, lastUndoableCommand);

            if (undoCommand != null && undoCommand.execute()) {
                lastUndoableCommand = null;
            }

            return false;
        }

        cb.updateItems();
        Hero currentHero = getCurrentHero();

        Command command = CommandFactory.createCommand(cb, cv, currentHero, combatOption, target, item, lastCommand);
        
        lastCommand = command;

        if (command != null) {
            finishedAction = command.execute(combatLog);

            if (finishedAction && command.canUndo()) {
                lastUndoableCommand = command;
            }

            if (command.advancesTurn()) {
                cb.setTurn(cb.turn() + 1);
            }
        }

        cb.checkAutonomousTurn();
        
        if (!finalBattle) executeEnemyTurn();
        
        else if (cb.turn() >= cb.getHeroes().size() + cb.getInfected().size()) cb.setTurn(1);
        
        return finishedAction;
    }

    public void saveGame() {

        if (cb != null) {
            SaveGameData data = new SaveGameData(this);
            SaveGameManager.saveGame(data);
        }
    }

    public void loadGame() {
        SaveGameData data = SaveGameManager.loadGame();

        if (data != null && cb != null) {
            restore(data);
            if (ctrl != null) {
                ctrl.onCombat();
            }
            System.out.println("Game loaded successfully.");
        }
    }

    private void restore(SaveGameData data) {
        cb.restore(data);
        cb.setGame(this);
        st.restore(data, this);
        finalBattle = data.isFinalBattle();
        mode = data.getMode();
        combatLog = new StringBuilder(data.getCombatLog());
        players = new ArrayList<>(cb.getHeroes());
    }

    public void selectCharacter(HeroEnum h, int player) {

        Hero new_hero;

        switch (mode) {
            case MULTIPLAYER -> {
                new_hero = players.get(player - 1);
                HeroBuilder.setUserHero(h, new_hero);
            }
            case SOLO -> new_hero = AutonomousHeroBuilder.buildAutonomousHero(h, player);
            default -> new_hero = HeroBuilder.buildHero(h, player);
        }

        if(mode == GameMode.SOLO && player == 2)
            new_hero.setAutonomous(true);

        cb.addHero(new_hero);
        new_hero.setCombat(cb);
    }

     public void next() {
    		System.out.println("caling next in Game");
    		cb.rstEnemies();
    		st.next(cb);
	 
	 }

	public void displayStory(String string) {
		ctrl.onStory(string);
		System.out.println("Displaying story: " + string);
	}
	public void startNewCmb() {
		System.out.println("calling on combat ");
	   	ctrl.onCombat();
	}
	
	public void end() {
		ctrl.exit();
	}
}
