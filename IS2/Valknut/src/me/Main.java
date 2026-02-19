package me;

import java.util.Scanner;
import me.model.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Combat cmb = initCmb(sc);
		while(!cmb.exit()){
			cmb.playTurn(sc);
		}
	}

	static public Hero selectCharacter(Scanner sc){
		HeroBuilder hb = new HeroBuilder();
		hb.getPossibleHeroes();
		System.out.print("Select: ");
		Integer i = sc.nextInt();
		if(i == 1)
			return HeroBuilder.buildHero("Freya");
		else
			return HeroBuilder.buildHero("Loki");
	}

	static public Enemy firstEnemies(Integer i){
		if(i == 1)
			return EnemyBuilder.buildEnemy("Ice");
		else
			return EnemyBuilder.buildEnemy("Fire");
	}

	static public Combat initCmb(Scanner sc){
		Combat cmb = new Combat();
		for(int i = 1; i < 3; i++){
			System.out.println("Player " + i + " selects...");
			System.out.println();
			cmb.addHero(selectCharacter(sc));
			cmb.addEnemy(firstEnemies(i));
			System.out.println();
		}
		return cmb;
	}
	
}
