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
    // private final Storyteller st;
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
        // st = new Storyteller(this);
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
    	//controlPanel.onCombat();
    }

    public Enemy firstEnemies(Integer i) {
        if (i == 1) {
            return EnemyBuilder.buildEnemy("Ice");
        } else {
            return EnemyBuilder.buildEnemy("Fire");
        }
    }

    /**
     * Returns the hero whose turn is currently active.
     *
     * @return the current hero or null if the turn is not valid
     */
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

        return null;
    }

    /**
     * Executes the enemy phase when both heroes have finished their turns.
     */
    private void executeEnemyTurn() {
        if (cb.turn() == 3) {
            // cv.print(cb.enemyTurnToString());

            for (Enemy e : cb.getEnemies()) {
                // cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }

            cb.setTurn(1);
            String s = cb.update();
            // cv.printLine(s);
        }
    }

    public boolean action(CombatOption combatOption, int target,Item item) {
        boolean finishedAction = false;

        if (combatOption == CombatOption.UNDO) {
            if (lastUndoableCommand != null && lastUndoableCommand.undo()) {
                lastUndoableCommand = null;
            }
            // controlPanel.onCombat();
            return false;
        }

        cb.updateItems();
        Hero currentHero = getCurrentHero();

        // Build the command that corresponds to the selected action.
        Command command = CommandFactory.createCommand(cb, cv, currentHero, combatOption, target, item);

        if (command != null) {
            finishedAction = command.execute();

            if (finishedAction && command.canUndo()) {
                lastUndoableCommand = command;
            }

            // Preserve the original behavior:
            // only commands that advance the turn should move combat forward.
            if (command.advancesTurn()) {
                cb.setTurn(cb.turn() + 1);
            }
        }

        executeEnemyTurn();
        
        //if (combatOption == CombatOption.ATTACK) {
        	// controlPanel.onCombat();
        //}
        
//        else {
//        	controlPanel.onSelection();
//        }
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

        if(mode == GameMode.MULTIPLAYER){
            new_hero = players.get(player);
            switch (h) {
                case HeroEnum.GERSEMI -> HeroBuilder.setUserHero(new_hero, "freya");
                case HeroEnum.VALI -> HeroBuilder.setUserHero(new_hero, "loki");
                case HeroEnum.JORUNN -> HeroBuilder.setUserHero(new_hero, "vkadi");
                case HeroEnum.VIGGO -> HeroBuilder.setUserHero(new_hero, "vidar");
                case HeroEnum.MAGNI -> HeroBuilder.setUserHero(new_hero, "mortal");
                default -> HeroBuilder.setUserHero(new_hero, "freya");
            }
        }
        else{
            switch (h) {
                case HeroEnum.GERSEMI -> new_hero = HeroBuilder.buildHero("freya", player);
                case HeroEnum.VALI -> new_hero = HeroBuilder.buildHero("loki", player);
                case HeroEnum.JORUNN -> new_hero = HeroBuilder.buildHero("skadi", player);
                case HeroEnum.VIGGO -> new_hero = HeroBuilder.buildHero("vidar", player);
                case HeroEnum.MAGNI -> new_hero = HeroBuilder.buildHero("mortal", player);
                default -> {new_hero = HeroBuilder.buildHero("Freya", player);}
            }

            new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
            new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
            new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
            new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
            new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
            new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
            new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, ItemType.DAMAGE));

            if(mode == GameMode.SOLO && player == 2)
                new_hero.setAutonomous(true);

            addHero(new_hero);
            
            cb.addHero(new_hero);
            new_hero.setCombat(cb);
        }
    }

    // public void next() {
	// 	st.next(cb);
	// }


	public void displayStory(String string) {
		
	}
}
