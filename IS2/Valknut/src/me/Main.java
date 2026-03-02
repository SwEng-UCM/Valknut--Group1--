package me;

import me.model.*;
import me.view.ConsoleIO;
import me.view.Messages;
import me.view.Story;

public class Main {

	public static ConsoleIO io = new ConsoleIO();
	public static void main(String[] args) {
		tellIntro();
		Combat cmb = initCmb();
		while(!cmb.exit()){
			cmb.playTurn(io);
		}
	}

	static public Hero selectCharacter(){
		HeroBuilder hb = new HeroBuilder();
		io.printLine(hb.getPossibleHeroes());
		io.print("Select: ");
		Integer i = io.parseIntInRange(0, hb.getHeroes().size());
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

	static public Combat initCmb(){
		Combat cmb = new Combat();
		for(int i = 1; i < 3; i++){
			io.printLine("Player " + i + " selects..." + Messages.NEW_LINE);
			cmb.addHero(selectCharacter());
			io.printLine("");
			cmb.addEnemy(firstEnemies(i));
		}
		return cmb;
	}
	
	static public void tellIntro() {
		System.out.println(Story.IntroLines);
		System.out.println();
		System.out.println();
	}
	
}
