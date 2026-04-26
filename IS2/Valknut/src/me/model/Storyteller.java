package me.model;

import java.util.ArrayList;
import java.util.List;
import me.model.EnemyBuilder;

public class Storyteller {

	//*Players select characters

	//Story starts Story.getIntro() Story.startFisrtChapter()

	//First combat call combat with the first gigants

	//Continiue story Story.middleFirstChapter()

	//No matter what the player chooses Story.startSecondChapter(); Story.middleSecondChapter();

	//Second combat call combat against the Dragurs

	//Story.endSecondChapter()
	
	//Story.StartThirdChapter()
	
	//*Combat 3: start combat against the Dragon
	
	// --> Set infected/heathy heroes here
	//Story.endThridChapter()
	
	//Story.startFourthChapter()
	
	//Combat 4: against the two wolves
	
	//Story.endFourthChapter()
	
	//Story.chapterFith()
	
	//Combat 5: final combat, battle againt eachother
	
	//Story.chapterFinal()
	
	private final List<Enemy> combat1 = new ArrayList<>();
	private final List<Enemy> combat2= new ArrayList<>();
	private final List<Enemy> combat3= new ArrayList<>();
	private final List<Enemy> combat4= new ArrayList<>();
	private List<Hero> infected = new ArrayList<>();
	private List<Hero> healthy = new ArrayList<>();
	private List<Hero> heroes = new ArrayList<>();
	
	public Storyteller() {
		//1st Combat enemies
		combat1.add(EnemyBuilder.buildEnemy("ice"));
		combat1.add(EnemyBuilder.buildEnemy("ice"));
		combat1.add(EnemyBuilder.buildEnemy("fire"));
		combat1.add(EnemyBuilder.buildEnemy("fire"));
		
		//2nd Combat enemies
		combat2.add(EnemyBuilder.buildEnemy("draug"));
		combat2.add(EnemyBuilder.buildEnemy("draug"));
		combat2.add(EnemyBuilder.buildEnemy("draug"));
		if(heroes.size() > 2) {
			combat2.add(EnemyBuilder.buildEnemy("draug"));
			combat2.add(EnemyBuilder.buildEnemy("draug"));
		}
		
		//3rd Combat enemies
		combat3.add(EnemyBuilder.buildEnemy("fafnir"));
		
		//4rth Combat enemies
		combat4.add(EnemyBuilder.buildEnemy("skoll"));
		combat4.add(EnemyBuilder.buildEnemy("hati"));
		
		
		
	}
	
	
}
