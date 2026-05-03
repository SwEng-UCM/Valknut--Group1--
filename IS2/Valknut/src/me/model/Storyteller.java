package me.model;

 import java.util.ArrayDeque;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Queue;
import java.util.Random;

import me.view.Story;

 public class Storyteller {

 	//*Players select characters

 	//Story starts Story.getIntro() Story.startFisrtChapter()

// 	//First combat call combat with the first gigants

// 	//Continiue story Story.middleFirstChapter()

// 	//No matter what the player chooses Story.startSecondChapter(); Story.middleSecondChapter();

// 	//Second combat call combat against the Dragurs

// 	//Story.endSecondChapter()
	
// 	//Story.StartThirdChapter()
	
// 	//*Combat 3: start combat against the Dragon
	
// 	// --> Set infected/heathy heroes here
// 	//Story.endThridChapter()
	
// 	//Story.startFourthChapter()
	
// 	//Combat 4: against the two wolves
	
// 	//Story.endFourthChapter()
	
// 	//Story.chapterFith()
	
// 	//Combat 5: final combat, battle againt eachother
	
// 	//Story.chapterFinal()
	
 	private Game game;
 	private final List<Enemy> combat1 = new ArrayList<>();
 	private final List<Enemy> combat2= new ArrayList<>();
 	private final List<Enemy> combat3= new ArrayList<>();
 	private final List<Enemy> combat4= new ArrayList<>();
 	private List<Hero> infected = new ArrayList<>();
 	private List<Hero> healthy = new ArrayList<>();
 	private List<Hero> heroes = new ArrayList<>();
 	private List<String> story;
 	private List<List<Enemy>> combats;
 	private final String[] index = {"s", "s", "c", "s", "s", "c", "s", "s", "c", "s", "s", "c", "s", "s", "fc", "s"};
 	private Story s;
 	private int bookmark = 0;
 	private Random rand = new Random();
	
 	public Storyteller(Game game) {
 		this.game = game;
 		s = new Story();
		
  	}
 	
 	public void writeStory(List<Hero> heroes) {
 		this.heroes = heroes;
 		s.addHeroes(heroes);
 		story = new ArrayList<>();
 		combats = new ArrayList<>();
 		
 		//1st Combat enemies
 		combat1.add(EnemyBuilder.buildEnemy("ice"));
 		combat1.add(EnemyBuilder.buildEnemy("fire"));
 		if(heroes.size() > 2) {
 			combat1.add(EnemyBuilder.buildEnemy("ice"));
 			combat1.add(EnemyBuilder.buildEnemy("fire"));
 		}
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
		
 		

		story.add(s.IntroLines);
		
		story.add(s.startFirstChapter());
		
		combats.add(combat1);

 		story.add(s.startSecondChapter());

		
 		story.add( s.middleSecondChapter());

 		combats.add(combat2);
 		
 		story.add(s.endSecondChapter());
	
 		story.add(s.startThirdChapter());

 		combats.add(combat3);

 		// Obtain a number between [0 - 49].
 		int n = rand.nextInt(heroes.size());
 		game.setInfected(n);
 		
 		if (heroes.size() == 4) {
 			int y = rand.nextInt(4);
 			while (y == n) y = rand.nextInt(4);
 			game.setInfected(n);
 		}

 		story.add(s.endThirdChapter());

 		story.add(s.startFourthChapter());

 		combats.add(combat4);

 		story.add(s.endFourthChapter());
	
 		story.add(s.chapterFith());
	
 		combats.add(null);
 		story.add(s.chapterFinal(new ArrayList<>()));
 	}
	
	
// 	public void narrate() {
// 		game.startNewCmb(combat1);
// 	}
 	
 	public void addHero(Hero h) {
 		heroes.add(h);
 	}

 	public void next(Combat cb) {
 		String story_or_combat = index[bookmark];
 		bookmark++;
 		if(story_or_combat == "s") {
 			game.displayStory(story.removeFirst());
 		}
		
 		else if(story_or_combat == "c") {
 			game.setEnemies(combats.removeFirst());
 			game.startNewCmb();
 		}
 		
 		else if(story_or_combat == "fc") {
 			game.finalCombat();
 			game.startNewCmb();
 		}
 		// else if (n == null) {
			
 		// }
		
 	}
	
 }
