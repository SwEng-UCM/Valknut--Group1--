package me.model;

import java.util.ArrayList;
import java.util.List;
import me.command.Command;
import me.command.CommandFactory;
import me.model.items.*;
import me.model.save.*;
import me.socket.MultiplayerManager;
import me.view.CombatView;
import me.model.Storyteller; 

public class Game {
    
    private static CombatView cv;
    private final Storyteller st;
    private final MultiplayerManager mpm;
    private me.control.Controller ctrl;
    private List<Hero> players;
    private Command lastUndoableCommand;
    private Combat cb;

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
        cb = new Combat();
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

    public void addHero(Hero e){
        players.add(e);
    }

    public int getNumEnemies() {
        return cb.getEnemies().size();
    }

    public int getTurn(){
        return cb != null ? cb.turn() : -1;
    }

    public List<Enemy> getEnemies(){
        return (cb == null ? null : cb.getEnemies());
    }
    
    public Inventory getHeroItems(){
        return (cb == null ? null : getCurrentHero().getInventory());
    }
    
    public void setEnemies(List<Enemy> newEnemies){
    	if(cb != null) {
    		cb.SetEnemies(newEnemies);
    	}
    }
    
    public List<Hero> getHeroes(){
        return (cb == null ? null : cb.getHeroes());
    }
    
    public void startNewCmb(List<Enemy> newEnemies) {
    	cb.SetEnemies(newEnemies);
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
        
        return null;
    }

    private void executeEnemyTurn() {
        if (cb.turn() == cb.getHeroes().size() + 1) {	
            cv.print(cb.enemyTurnToString());

            for (Enemy e : cb.getEnemies()) {
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }

            cb.setTurn(1);
            String s = cb.update();
            cv.printLine(s);
        }
    }

    public boolean action(CombatOption combatOption, int target,Item item) {
        boolean finishedAction = false;

        if (combatOption == CombatOption.UNDO) {
            if (lastUndoableCommand != null && lastUndoableCommand.undo())
                lastUndoableCommand = null;

            return false;
        }

        cb.updateItems();
        Hero currentHero = getCurrentHero();

        Command command = CommandFactory.createCommand(cb, cv, currentHero, combatOption, target, item);

        if (command != null) {
            finishedAction = command.execute();

            if (finishedAction && command.canUndo()) {
                lastUndoableCommand = command;
            }

            if (command.advancesTurn()) {
                cb.setTurn(cb.turn() + 1);
            }
        }

        cb.checkAutonomousTurn();

        executeEnemyTurn();
        
        return finishedAction;
    }

    public void saveGame() {

        if (cb != null) {
            SaveGameData data = cb.save();
            SaveGameManager.saveGame(data);
        }
    }

    public void loadGame() {
        SaveGameData data = SaveGameManager.loadGame();

        if (data != null && cb != null) {
            cb.restore(data);
            // controlPanel.onCombat();
            System.out.println("Game loaded successfully.");
        }
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
	 	st.next(cb);
	 }

	public void displayStory(String string) {
		
	}
}
