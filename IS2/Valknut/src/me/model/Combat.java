package me.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import me.model.items.Item;
import me.model.save.SaveGameData;
import me.model.save.SaveGameManager;
import me.view.Messages;


public class Combat implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Hero> heroes;
    volatile private List<Enemy> enemies;
    private List<Item> items;
    private int turn;
    private boolean exit;
    private int lastTarjet;
    private Game game;

    public Combat(Game game){
        heroes = new ArrayList<>(); //initial values are almost random
        enemies = new ArrayList<>();
        items = new ArrayList<>(5);
        turn = 1; 
        exit = false;
        this.game = game;
    }

    public List<Hero> getHeroes(){
        return heroes;
    }

    public List<Enemy> getEnemies(){
        return enemies;
    }
    
    public List<Item> getItems(){
        return items;
    }
    
    public boolean getExit() {
    	return exit;
    }
    
    public void SetEnemies(List<Enemy> newEnemies) {
    	this.enemies = new ArrayList<>();
        this.enemies.addAll(newEnemies);
        
        String s = "New enemies (combat): " + enemies.size();
        for(Enemy e: enemies) {
        	s = s +" "+e.name();
        }
        System.out.println(s);
        exit = false;
        turn = 1;
    }

    public int getLastTarjet(){
        return lastTarjet;
    }

    public void addHero(Hero e){
        heroes.add(e);
    }

    public void addEnemy(Enemy e){
        enemies.add(e);
    }
    
    public void addItem(Item e) {
    	items.add(e);
    }
    
    public void rstEnemies() {
    	rmvEnemies();
    }

    private String rmvEnemies(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            if (!enemy.isAlive()) {
                sb.append(enemy.name().toUpperCase()).append(" was defeated!").append(Messages.NEW_LINE);

                // Give XP to heroes who participated (alive + not escaped)
                for (Hero h : heroes) {
                    if (h.isAlive() && !h.escaped()) {
                        sb.append(h.gainXp(enemy.getXpReward())).append(Messages.NEW_LINE);
                    }
                }
                enemies.remove(i);
                i--;
            }
        }
        return sb.toString();
    }

    public int turn(){
        return turn;
    }

    public void setTurn(int i){
        turn = i;
    }

    public String enemyTurnToString(){
        StringBuilder sb = new StringBuilder();

        sb.append(Messages.NEW_LINE);
        sb.append(Messages.ENEMY_TURN).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public boolean exit() {
        return exit;
    }

    public String heroTargetsToString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Targets... ").append(Messages.NEW_LINE);
            int i = 1;
            for(Enemy e: enemies){
                sb.append(i).append(". ").append(e.name().toUpperCase()).append(" ");
                i++;
            }
            sb.append(Messages.NEW_LINE).append(Messages.NEW_LINE);

            return sb.toString();
    }

    public String attack(int i){
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.NEW_LINE);

        if (turn < heroes.size() + 1) {
            if(i == 0){
                sb.append("NO TARJET").append(Messages.NEW_LINE);
                return sb.toString();
            }
            Hero h = heroes.get(turn - 1);
            sb.append(h.name().toUpperCase()).append(" ATTACKS").append(Messages.NEW_LINE);
            sb.append(h.attack(enemies.get(i - 1), h.getMainElement(), 20));
            lastTarjet = i - 1;
            System.err.println(lastTarjet);
        }
        else{
            Enemy e = enemies.get(turn - (heroes.size() + 1));
            Hero h = e.selectTarjet(heroes);
            if(h != null){
                sb.append(e.name().toUpperCase()).append(" attacks ").append(h.name().toUpperCase()).append(Messages.NEW_LINE);
                sb.append(e.getAttack()).append(Messages.NEW_LINE);
                int damage;
                if(h.isDefending()){damage = 10;}else{damage = 20;}
                sb.append(e.attack(h, e.getMainElement(), damage)).append(Messages.NEW_LINE);
                if(!h.isAlive())
                    heroes.remove(h);
            }
            else{
                sb.append(e.name().toUpperCase()).append(Messages.ENEMY_MISS).append(Messages.NEW_LINE);
            }
        }

        return sb.toString();
    }

    public void checkAutonomousTurn(){
        if(turn == 2){
            Hero h = heroes.get(1);
            if(h.isAutonomous()){
                h.does(null);
                turn++;
            }
        }
    }

    public String showStats(Character c){
        StringBuilder sb = new StringBuilder();

        sb.append("Life: ").append(c.getLife()).append(" hp").append(Messages.NEW_LINE);
        sb.append(c.getStringAttributes());
        sb.append(c.getStringElements()).append(Messages.NEW_LINE);

        return  sb.toString();
    }

    public String defend(){
        StringBuilder sb = new StringBuilder();
        Hero e = heroes.get(turn - 1);
        e.defend();
        sb.append(e.name().toUpperCase()).append(Messages.DEFEND).append(Messages.NEW_LINE);
        return sb.toString();
    }

    public boolean allEscaped(){
        boolean yes = true;

        for(int i = 0; i < heroes.size() && yes; i++){
            if(!heroes.get(i).escaped())
                yes = false;
        }

        if(yes){
            for(Hero e: heroes)
                e.setEscaped(false);
        }

        return yes;
    }

    public String useItem(Hero h, Item i){ 
        StringBuilder sb = new StringBuilder();
        if(i != null){
            sb.append(h.useItem(i));
            items.add(i);
        }

        return sb.toString();
    }

    public boolean heroesLoose(){
        boolean yes = true;

        for(int i = 0; i < heroes.size(); i++){
            if(!heroes.get(i).escaped() && heroes.get(i).isAlive()){
                yes = false;
            }
        }

        return yes;
    }

    public String update() {
        //Implement a fucntion set escaped with one turn of delay so if The Boss with the Odin's Spear (Gungnir) uses the special attacks, ,it hits the heroes that just escpaed
        StringBuilder sb = new StringBuilder();
        sb.append(rmvEnemies());
        if(enemies.isEmpty()){
            sb.append(Messages.BATTLE_WIN);
            exit = true;
            //SaveGameManager.saveGame(save(), "autosave.dat"); TODO
        }
        else if(allEscaped()){
            sb.append(Messages.BATTLE_ESCAPE);
            exit = true;
            SaveGameManager.saveGame(save(), "autosave.dat");
        }
        else if(heroesLoose()){
            sb.append(Messages.BATTLE_LOSS);
            exit = true;
            SaveGameManager.saveGame(save(), "autosave.dat");
        }

        return sb.toString();
    }

    public void updateItems(){
        for(Item i : items){
            i.update();
        }
    }

    public SaveGameData save() {
        return new SaveGameData(this);
    }

    public void restore(SaveGameData data) {
        Combat restored = data.getCombat();

        this.heroes.clear();
        this.heroes.addAll(restored.getHeroes());

        this.enemies.clear();
        this.enemies.addAll(restored.getEnemies());

        this.items.clear();
        this.items.addAll(restored.items);

        this.turn = restored.turn();
        this.lastTarjet = restored.getLastTarjet();
        this.exit = restored.exit();
    }

    public String run(){
        double i = Math.random();
        if(i > 0.5){
            heroes.get(turn - 1).setEscaped(true);
            return Messages.PLAYER_RUNS + Messages.NEW_LINE;
        }
        else{
            return Messages.PLAYER_RUNFAIL + Messages.NEW_LINE;
        }
    }
}