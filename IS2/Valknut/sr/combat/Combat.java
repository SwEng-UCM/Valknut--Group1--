package combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Combat {
    private List<Hero> heroes;
    private List<Enemy> enemies;
    private CombatOption combOpt;
    private int turn;
    private boolean exit;

    public Combat(){
        heroes = new ArrayList<>(4);
        enemies = new ArrayList<>(5);
        combOpt = CombatOption.parseCommand("X"); // Set wait as default 
        turn = 1; 
        exit = false;
    }

    public void addHero(Hero e){
        heroes.add(e);
    }

    public void addEnemy(Enemy e){
        enemies.add(e);
    }

    private void rmvEnemies(){
        for(int i = 0; i < enemies.size(); i++){
            if(!enemies.get(i).isAlive()){
                enemies.remove(i);
                i--;
            }
        }
    }

    private void printTurn(Character c){
        System.out.println(c.name().toUpperCase() + "'s TURN...");
        System.out.println();
        combOpt.print();
        System.out.println();
        System.out.print("Option: ");
    }

    private void printEnemyTurn(){
        System.out.println("ENEMIES's TURN...");
            System.out.println();
    }

    public CombatOption selectAction(Scanner sc, Character c){
        printTurn(c);
        String aux = sc.next();
        System.out.println();
        return CombatOption.parseCommand(aux);
    }

    public void playTurn(Scanner sc) {
        if (turn == 1) {
            for(Hero e: heroes){
                if(e.isAlive() && !e.escaped()){
                    combOpt = selectAction(sc, e);
                    action(combOpt, sc);
                    update();
                }
                turn++;
            }
        } else {
            printEnemyTurn();
            for(Enemy e: enemies){
                attack(sc);
                turn++;
            }
            update();
            turn = 1;
        }
    }

    private void action(CombatOption co, Scanner sc){
        switch(co){
            case ATTACK:
                attack(sc);
                break;
            case DEFEND:
                defend();
                break;
            case USE_ITEM:
                break;
            case RUN:
                run();
                break;
            default:
                break;
        }
    }

    public boolean exit() {
        return exit;
    }

    private void printHeroTarjets(){
        System.out.print("Tarjets... ");
            int i = 1;
            for(Enemy e: enemies){
                System.out.print(i + ". " + e.name().toUpperCase() + " ");
                i++;
            }
            System.out.println();
            System.out.println();
            System.out.print("Select: ");
    }

    public void attack(Scanner sc){
        if(turn < 3){
            printHeroTarjets();
            int i = sc.nextInt();
            Hero h = heroes.get(turn - 1);
            h.attack(enemies.get(i - 1), h.getMainElement(), null, 20);
            System.out.println(); System.out.println();
        }
        else{
            Enemy e = enemies.get(turn - 3);
            Hero h = e.selectTarjet(heroes);
            if(h != null){
                System.out.println(e.name().toUpperCase() + " attacks " + h.name().toUpperCase());
                int damage;
                if(h.isDefending()){damage = 10;}else{damage = 20;}
                e.attack(h, e.getMainElement(), null, damage);
                System.out.println();
            }
            else{
                System.out.println(e.name().toUpperCase() + " MISS: NO HERO FIGHTING");
                System.out.println();
            }
        }
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
            System.out.println("THE HEROES WIN. GREAT TEAM.");
            exit = true;
        }
        else if(allEscaped()){
            System.out.println("THE HEROES ESCAPED THE FIGTH.");
            exit = true;
        }
        else if(heroesLoose()){
            System.out.println("THE HEROES LOOSE. KEEP TRYING.");
            exit = true;
        }
    }

    public void run(){
        double i = Math.random();
        if(i > 0.5){
            System.out.println("YOU ESCAPED LUCKY HERO");
            System.out.println();
            heroes.get(turn - 1).setEscaped(true);
        }
        else{
            System.out.println("YOU FAILED IN YOUR ESCAPE");
            System.out.println();
        }
    }
}