package me.model;

import java.util.ArrayList;
import java.util.List;
import me.view.Messages;


public class Combat {
    private final List<Hero> heroes;
    private final List<Enemy> enemies;
    private int turn;
    private boolean exit;

    public Combat(){
        heroes = new ArrayList<>(4);
        enemies = new ArrayList<>(5);
        turn = 1; 
        exit = false;
    }

    public List<Hero> getHeroes(){
        return heroes;
    }

    public List<Enemy> getEnemies(){
        return enemies;
    }

    public void addHero(Hero e){
        heroes.add(e);
    }

    public void addEnemy(Enemy e){
        enemies.add(e);
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
            sb.append("Select: ");

            return sb.toString();
    }

    public String attack(int i){
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.NEW_LINE);

        if(turn < 3){
            Hero h = heroes.get(turn - 1);
            sb.append(h.attack(enemies.get(i - 1), h.getMainElement(), 20));
        }
        else{
            Enemy e = enemies.get(turn - 3);
            Hero h = e.selectTarjet(heroes);
            if(h != null){
                sb.append(e.name().toUpperCase()).append(" attacks ").append(h.name().toUpperCase()).append(Messages.NEW_LINE);
                sb.append(e.getAttack()).append(Messages.NEW_LINE);
                int damage;
                if(h.isDefending()){damage = 10;}else{damage = 20;}
                sb.append(e.attack(h, e.getMainElement(), damage)).append(Messages.NEW_LINE);
            }
            else{
                sb.append(e.name().toUpperCase()).append(Messages.ENEMY_MISS).append(Messages.NEW_LINE);
            }
        }

        return sb.toString();
    }

    public String showStats(Character c){
        StringBuilder sb = new StringBuilder();

        sb.append("Life: ").append(c.getLife()).append(" hp").append(Messages.NEW_LINE);
        sb.append(c.getStringElements());

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

    public String useItem(Hero h){ 
        StringBuilder sb = new StringBuilder();
        sb.append("INVENTORY... ").append(Messages.NEW_LINE);
        sb.append(h.displayInventory()).append(Messages.NEW_LINE);

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
        }
        else if(allEscaped()){
            sb.append(Messages.BATTLE_ESCAPE);
            exit = true;
        }
        else if(heroesLoose()){
            sb.append(Messages.BATTLE_LOSS);
            exit = true;
        }

        return sb.toString();
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