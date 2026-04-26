package me.control;

import java.io.IOException;
import java.util.List;
import me.command.Command;
import me.command.CommandFactory;
import me.model.*;
import me.model.items.*;
import me.model.save.*;
import me.socket.MultiplayerManager;
import me.view.AudioManager;
import me.view.CombatView;
import me.view.CtrlPanel;
import me.view.Messages;
import me.view.StoryView;
import me.view.ViewUtils;

public class Controller {

    private static StoryView sv;
    private static CombatView cv;
    private static Controller instance;
    private MultiplayerManager player;
    private boolean multiplayer = false;
    private Combat cb;
    private Command lastUndoableCommand;
    private final CtrlPanel controlPanel;
    private int num_enemies;
    private Storyteller st;

    private Controller() {
        sv = StoryView.getInstance();
        cv = CombatView.getInstance();
        controlPanel = new CtrlPanel(this);
        cb = new Combat();
    }
    

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public void initMultiplayerMode(int i, String ip){
        player = new MultiplayerManager(instance);
        try{
            switch (i) {
                case 0 -> player.connectServer();
                case 1 -> player.connectClient(ip);
                default -> throw new AssertionError();
            }
            multiplayer = true;
        }catch(IOException e){
            ViewUtils.showErrorMsg("Aborted Conexion");
        }
        
    }

    public void killServer(){
        try {
            player.endServer();
        } catch (IOException ex) {
               
        }
    }

    public void killClient(){ // just in case
        try {
            player.endClient();
        } catch (IOException ex) {
            ViewUtils.showErrorMsg(ex.getMessage());
        }
    }

    public int getNumEnemies() {
        return num_enemies;
    }

    public int getTurn(){
        return cb != null ? cb.turn() : -1;
    }

    public void run() {
//    	startGame();
//    	st = new Storyteller(this);
//    	st.narrate();
//        //cb = initCmb();
     cb = initCmb();
     startGame();
    }

    public void startGame() {
        AudioManager.getInstance().stopMusic();
        AudioManager.getInstance().playMusic("resources/sounds/titleMusic.wav");
        controlPanel.onGameStart();
    }

    public void menuScreen() {
        controlPanel.showMainMenu();
    }

    public void charactersScreen() {
        controlPanel.onSelection();
    }

    public void setPreviousScreenToSettings(String s, String b) {
        controlPanel.setPreviousScreenToSettings(s, b);
    }

    public void settingScreen() {
        controlPanel.settingScreen();
    }

    public void startMultiplayer() {
        AudioManager.getInstance().stopMusic();
        AudioManager.getInstance().playMusic("resources/sounds/internetMusic.wav");
        multiplayerScreen();
    }

    public void multiplayerScreen() {
        controlPanel.multiplayerScreen();
    }

    public void startStory() {
        sv.tellIntro();
    }

    public void tellFirstLinesChapterOne() {
        sv.clear();
        sv.tellFirstLinesChapterOne(cb.getHeroes().get(0), cb.getHeroes().get(1));
    }
    
    public List<Enemy> getEnemies(){
        return (cb == null ? null : cb.getEnemies());
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
    	num_enemies = newEnemies.size();
    	//controlPanel.onCombat();
    	
    }
    
    public Combat initCmb() {
        Combat cmb = new Combat();
        cmb.addEnemy(firstEnemies(1));
        cmb.addEnemy(firstEnemies(2));
        cmb.addEnemy(firstEnemies(1));
        cmb.addEnemy(firstEnemies(2));
        num_enemies = 4;
        return cmb;
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
            cv.print(cb.enemyTurnToString());

            for (Enemy e : cb.getEnemies()) {
                cv.print(cb.attack(0));
                cb.setTurn(cb.turn() + 1);
            }

            cb.setTurn(1);
            cv.printLine(cb.update());
        }
    }

    public boolean action(CombatOption combatOption, int target,Item item) {
        boolean finishedAction = false;

        if (combatOption == CombatOption.UNDO) {
            if (lastUndoableCommand != null && lastUndoableCommand.undo()) {
                lastUndoableCommand = null;
            }
            controlPanel.onCombat();
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
        	controlPanel.onCombat();
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
            controlPanel.onCombat();
            System.out.println("Game loaded successfully.");
        }
    }

    public String selectCharacter(int i, int player) {
        StringBuilder sb = new StringBuilder();
        Hero new_hero;

        if (i == 0) {
            new_hero = HeroBuilder.buildHero("Freya");
            sb.append("GERSEMI");
        } else {
            new_hero = HeroBuilder.buildHero("Loki");
            sb.append("VÁLI");
        }

        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new ResistanceItem("Iron Armor Piece", 5, 1, 3, ItemType.RESITANCE));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Seidr's Herb Sprouts", 10, 20, 1, ItemType.HEAL));
        new_hero.addItem(new HealingItem("Curing Crystal Stone", 200, 80, 1, ItemType.HEAL));
        new_hero.addItem(new DamageItem("Uru Gantlet", 1000, 5, 8, ItemType.DAMAGE));

        cb.addHero(new_hero);
        new_hero.setCombat(cb);
        sb.append(Messages.NEW_LINE);

        return sb.toString();
    }

    /**
     * Starts a new game after character selection and opens the combat screen.
    */
    public void startSelectedGame() {
        tellFirstLinesChapterOne();
        controlPanel.onCombat();
    }

    public void exit() {
        controlPanel.onQuit();
    }
}