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

    public void rmvEnemies(){
        int aux = enemies.size();
        for(int i = 0; i < aux; i++){
            if(!enemies.get(i).isAlive())
                enemies.remove(i);
        }
    }

    public CombatOption selectAction(Scanner sc, Character c){
        System.out.println(c.name().toUpperCase() + "'s TURN...");
        System.out.println();
        combOpt.print();
        System.out.println();
        System.out.print("Option: ");
        String aux = sc.next();
        System.out.println();
        return CombatOption.parseCommand(aux);
    }

    public void playTurn(Scanner sc) {
        if (turn == 1) {
            for(Hero e: heroes){
                if(e.isAlive()){
                    combOpt = selectAction(sc, e);
                    action(combOpt, sc);
                }
                turn++;
                update();
            }
        } else {
            System.out.println("ENEMIES's TURN...");
            System.out.println();
            for(Enemy e: enemies){
                attack(sc);
                turn++;
            }
            turn = 1;
        }
    }

    private void action(CombatOption co, Scanner sc){
        switch(co){
            case ATTACK:
                attack(sc);
            case DEFEND:
                ;
            case USE_ITEM:
                ;
            case RUN:
                exit();
            default:
                ;
        }
    }

    public boolean exit() {
        return exit;
    }

    public void attack(Scanner sc){
        if(turn < 3){
            System.out.print("Tarjets... ");
            int i = 1;
            for(Enemy e: enemies){
                System.out.print(i + ". " + e.name().toUpperCase() + " ");
                i++;
            }
            System.out.println();
            System.out.println();
            System.out.print("Select: ");
            i = sc.nextInt();
            Hero h = heroes.get(turn - 1);
            h.attack(enemies.get(i - 1), h.getMainElement(), null);
            System.out.println();
            System.out.println();
        }
        else{
            Enemy e = enemies.get(turn - 3);
            Hero h = e.selectTarjet(heroes);
            System.out.println(e.name().toUpperCase() + " attacks " + h.name().toUpperCase());
            e.attack(h, e.getMainElement(), null);
            System.out.println();
        }
    }

    public void update() {
        rmvEnemies();
        if(enemies.isEmpty()){
            System.out.println("YOU WIN. GREAT TEAM.");
            exit = true;
        }
        else if(heroes.isEmpty()){
            System.out.println("YOU LOOSE. KEEP TRYING.");
            exit = true;
        }
    }
}