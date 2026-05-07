/**
 * @author Gadea Domínguez (for the story-related functions). AI-assisted: no
// @author Pablo Cabello Canales (combat log stuff) AI-assisted: no
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model;

import java.util.ArrayList;
import java.util.List;
import me.command.Command;
import me.command.CommandFactory;
import me.model.items.*;
import me.model.save.*;

public class Game {

    // Game has a storyteller to follow the story flow, a controller reference, list of players,
    // last commando to undo, a combat, the last command, if it's time for 
    // the final battle and a combatLog to move text from game to the view
    // via the controller
    
    private final Storyteller st;
    private me.control.Controller ctrl;
    private List<Hero> players;
    private Command lastUndoableCommand;
    private Combat cb;
    private Command lastCommand;
    private boolean finalBattle = false;
    private StringBuilder combatLog = new StringBuilder();

    public enum GameMode{
        //Three posibilities for playing
        SOLO, LOCAL, MULTIPLAYER;
    }

    private GameMode mode;

    public Game(me.control.Controller ctrl){
        this.ctrl = ctrl;
        players = new ArrayList<>(4);
        mode = GameMode.LOCAL;
        st = new Storyteller(this);
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
    	}
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

    //Based on the turn and if its final battle or regular
    private Hero getCurrentHero() {
        if (cb == null || cb.getHeroes().isEmpty()) {
            return null;
        }
        
        if (!finalBattle) {
	        return cb.getHeroes().get(cb.turn() - 1);
        }
        
        else {
        	if (cb.turn() - 1 < cb.getHeroes().size()) return cb.getHeroes().get(cb.turn() - 1);
        	
        	else if (cb.turn() - cb.getHeroes().size() - 1 < cb.getInfected().size()) return cb.getInfected().get(cb.turn() - 1 - cb.getHeroes().size());
        }
        
        return null;
    }

    //Attacks for enemies
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

            cb.setTurn(1);
        }
        
        if(cb.getExit() && !cb.heroesLose()) {
            next();
        }
        
        else if (cb.getExit() && cb.heroesLoose()) {
        	ctrl.onGameOver();
        }
    }
    
    //Attack for heroes
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
    	st.setInfected(infected);
    }

    //This uses the command pattern to execute actions
    public boolean action(CombatOption combatOption, int target,Item item) {
        boolean finishedAction = false;
        
        String debug = (item == null ? "None" : item.toString());
        System.err.println("I'm doing " + combatOption.toString() + " with tarjet: " + target + " and item: " + debug);
        
        
        if (combatOption == CombatOption.UNDO) {
            Command undoCommand = CommandFactory.createCommand(cb, getCurrentHero(), combatOption, target, item, lastUndoableCommand);

            if (undoCommand != null) {
                undoCommand.execute(combatLog);
                lastUndoableCommand = null;
                lastCommand = null;
            }

            return false;
        }

        //Update items to deleted expired ones
        cb.updateItems();
        Hero currentHero = getCurrentHero();
        
        //If no heroes, heroes lose
        if (currentHero == null) ctrl.onGameOver();

        Command command = CommandFactory.createCommand(cb, currentHero, combatOption, target, item, lastCommand);
        
        lastCommand = command;

        if (command != null) {
            finishedAction = command.execute(combatLog);
            if (command.canUndo()) {
                lastUndoableCommand = command;
            }

            if (command.advancesTurn()) {
                cb.setTurn(cb.turn() + 1);
            }
        }

        //Add the autonomous hero action to the log if there is an AH
        combatLog.append(cb.checkAutonomousTurn());
        
        if (!finalBattle) executeEnemyTurn();
        
        else if (cb.turn() >= cb.getHeroes().size() + cb.getInfected().size()) cb.setTurn(1);
        
        return finishedAction;
    }
    
    public String showStats() {
    	return "Hero: " + getCurrentHero().getName() + "\n" + cb.showStats(getCurrentHero());
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
        }
    }

    private void restore(SaveGameData data) {
        cb.restore(data);
        cb.setGame(this);
        st.restore(data, ctrl);
        finalBattle = data.isFinalBattle();
        mode = data.getMode();
        combatLog = new StringBuilder(data.getCombatLog());
        players = new ArrayList<>(cb.getHeroes());
    }

    //Before entering the game, the logic for select which character the player is
    public void selectCharacter(HeroEnum h, int player) {

        Hero new_hero;

        //Depending on the mode
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

        //adds to the combat that will be the first combat
        cb.addHero(new_hero);
        //also the hero know the combat
        new_hero.setCombat(cb);
    }

    //next on the story
     public void next() {
        lastUndoableCommand = null;
        lastCommand = null;
        cb.rstEnemies();
        st.next(cb);
    }

	public void displayStory(String string) {
		ctrl.onStory(string);
	}
    
	public void startNewCmb() {
        lastUndoableCommand = null;
        lastCommand = null;
        combatLog.setLength(0);

        ctrl.onCombat();
    }
	
	public void end() {
		ctrl.onEnd();
	}
}
