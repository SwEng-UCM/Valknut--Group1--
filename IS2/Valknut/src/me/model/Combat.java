package me.model;

import java.util.ArrayList;
import java.util.List;
import me.view.ConsoleIO;
import me.view.Messages;


public class Combat {
    private final List<Hero> heroes;
    private final List<Enemy> enemies;
    private static CombatOption combOpt;
    private int turn;
    private boolean exit;
    private final ConsoleIO io; //Combat has many messages on its own that decided to pass the console as an attribute

    public Combat(ConsoleIO io){
        heroes = new ArrayList<>(4);
        enemies = new ArrayList<>(5);
        combOpt = CombatOption.parseCommand("wait"); // Set wait as default 
        turn = 1; 
        exit = false;
        this.io = io;
    }

    public void addHero(Hero e){
        heroes.add(e);
    }

    public void addEnemy(Enemy e){
        enemies.add(e);
    }

    private void rmvEnemies(){
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);

            if (!enemy.isAlive()) {
                io.printLine(enemy.name().toUpperCase() + " was defeated!");

                // Give XP to heroes who participated (alive + not escaped)
                for (Hero h : heroes) {
                    if (h.isAlive() && !h.escaped()) {
                        io.printLine(h.gainXp(enemy.getXpReward()));
                    }
                }
                io.printLine("");
                enemies.remove(i);
                i--;
            }
        }
    }

    private String turnToString(Character c){
        StringBuilder sb = new StringBuilder();
        sb.append(c.name().toUpperCase()).append("'s TURN...").append(Messages.NEW_LINE).append(Messages.NEW_LINE);
        sb.append(CombatOption.display()).append(Messages.NEW_LINE).append("Option: ");

        return sb.toString();
    }

    private String enemyTurnToString(){
        StringBuilder sb = new StringBuilder();

        sb.append(Messages.ENEMY_TURN).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public CombatOption selectAction(Character c){
        CombatOption co;
        io.print(turnToString(c));
        String aux = io.readPrompt();
        io.printLine("");
        co = CombatOption.parseCommand(aux);
        while(co == null || co == CombatOption.WAIT){
            io.printLine(Messages.ENTER_VV + Messages.NEW_LINE);
            io.print(turnToString(c));
            aux = io.readPrompt();
            io.printLine("");
            co = CombatOption.parseCommand(aux);
        }

        return co;
    }

    public void playTurn() {
        if (turn == 1) {
            for(Hero e: heroes){
                if(e.isAlive() && !e.escaped() && !enemies.isEmpty()){
                    combOpt = selectAction(e);
                    action(e, combOpt);
                    update();
                }
                turn++;
            }
        } else {
            enemyTurnToString();
            for(Enemy e: enemies){
                attack();
                turn++;
            }
            update();
            turn = 1;
        }
    }

    private void action(Hero h, CombatOption co){
        switch(co){
            case ATTACK -> attack();
            case DEFEND -> defend();
            case USE_ITEM -> useItem(h);
            case RUN -> io.printLine(run());
            case STATS -> io.printLine(showStats(h));
            default -> {
            }
        }
    }

    public boolean exit() {
        return exit;
    }

    private String heroTargetsToString(){
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

    public void attack(){
        if(turn < 3){
            io.print(heroTargetsToString());
            int i = io.parseIntInRange(1, enemies.size());
            Hero h = heroes.get(turn - 1);
            h.attack(enemies.get(i - 1), h.getMainElement(), 20);
            io.printLine(Messages.NEW_LINE); //double '\n'
        }
        else{
            Enemy e = enemies.get(turn - 3);
            Hero h = e.selectTarjet(heroes);
            if(h != null){
                io.printLine(e.name().toUpperCase() + " attacks " + h.name().toUpperCase());
                io.printLine(e.getAttack());
                int damage;
                if(h.isDefending()){damage = 10;}else{damage = 20;}
                e.attack(h, e.getMainElement(), damage);
                io.printLine(" ");
            }
            else{
                io.printLine(e.name().toUpperCase() + Messages.ENEMY_MISS);
                io.printLine("");
            }
        }
    }

    private String showStats(Character c){
        StringBuilder sb = new StringBuilder();

        sb.append("Life: ").append(c.getLife()).append(" hp").append(Messages.NEW_LINE);
        sb.append(c.getStringElements());

        return  sb.toString();
    }

    private void defend(){
        heroes.get(turn - 1).defend();
    }

    private boolean allEscaped(){
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

    private void useItem(Hero h){
        io.printLine("INVENTORY... ");
        io.printLine(h.displayInventory());
    }

    private boolean heroesLoose(){
        boolean yes = true;

        for(int i = 0; i < heroes.size(); i++){
            if(!heroes.get(i).escaped() && heroes.get(i).isAlive()){
                yes = false;
            }
        }

        return yes;
    }

    public void update() {
        rmvEnemies();
        if(enemies.isEmpty()){
        	
            io.printLine(Messages.BATTLE_WIN);
            exit = true;
        }
        else if(allEscaped()){
            io.printLine(Messages.BATTLE_ESCAPE);
            exit = true;
        }
        else if(heroesLoose()){
            io.printLine(Messages.BATTLE_LOSS);
            exit = true;
        }
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