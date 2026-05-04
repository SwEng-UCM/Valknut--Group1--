package me.control;

import java.util.List;

import me.model.*;
import me.model.Game.GameMode;
import me.view.CtrlPanel;

public class Controller {

    private static Controller instance;
    private final CtrlPanel controlPanel;
    private final Game game;

    private Controller() {
        this.game = new Game(this);
        controlPanel = new CtrlPanel(this, game);
    }
    

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public void run() {
        startGame();
    }

    public void startGame() {
        // AudioManager.getInstance().stopMusic();
        // AudioManager.getInstance().playMusic("resources/sounds/titleMusic.wav");
        game.initCmb();
        controlPanel.onGameStart();
    }

    public void setGameMode(me.model.Game.GameMode m){
        game.setMode(m);
    }

    public void addHero(Hero e){
        game.addHero(e);
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
        // AudioManager.getInstance().stopMusic();
        // AudioManager.getInstance().playMusic("resources/sounds/internetMusic.wav");
        multiplayerScreen();
    }

    public void multiplayerScreen() {
        controlPanel.multiplayerScreen();
    }

    public void startSelectedGame() { //TODO
        game.setStoryHeroes();
    	game.next();
        //controlPanel.onCombat(game);
    }

    public void saveGame() {
        game.saveGame();
    }

    public void loadGame() {
        game.loadGame();
    }
    
    public void onStory(String story) {
    	controlPanel.onStory(game, story);
    }
    
    public void onEnd() {
    	controlPanel.onEnd();
    }
    
    public void onCombat() {
    	controlPanel.onCombat(game);
    }

    public void exit() {
        controlPanel.onQuit();
    }

    public void next(){
        game.next();
    }
    
    public boolean isMultiplayer() {
    	return game.isMultiplayer();
    }
    
    public Game getGame() {
    	return game;
    }
    
    public void selectCharacter(HeroEnum h, int player) {
    	game.selectCharacter(h, player);
    }
    
    public void setMode(GameMode m) {
    	game.setMode(m);
    }
}
