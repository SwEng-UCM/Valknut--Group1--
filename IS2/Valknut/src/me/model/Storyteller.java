

package me.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.control.Controller;
import me.model.save.SaveGameData;
import me.view.Story;


 /**
 * @author Gadea Domínguez. AI-assisted: no
 */
public class Storyteller implements Serializable {

 	private static final long serialVersionUID = 1L;

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
	
 	private transient Game game;
 	private final List<Enemy> combat1 = new ArrayList<>();
 	private final List<Enemy> combat2= new ArrayList<>();
 	private final List<Enemy> combat3= new ArrayList<>();
 	private final List<Enemy> combat4= new ArrayList<>();
 	private List<Hero> infected = new ArrayList<>();
 	private List<Hero> healthy = new ArrayList<>();
 	private List<Hero> heroes = new ArrayList<>();
 	private List<String> story;
 	private List<List<Enemy>> combats;
 	private final String[] index = {"s", "s", "c", "s", "s", "c", "s", "s", "c", "s", "s", "c", "s", "s", "fc", "s", "end"}; //The index with the order of the different parts
 	private transient Story s;
 	private int bookmark = 0; //Keeps where in the index we are
 	private Random rand = new Random();
	
 	public Storyteller(Game game) {
 		this.game = game;
 		s = new Story();
		
  	}

 	public void restore(SaveGameData data, Controller ctrl_) {
 		Storyteller restored = data.getStoryteller();
 		if (restored == null) {
 			return;
 		}

 		this.s = new Story();

 		copyEnemies(combat1, restored.combat1);
 		copyEnemies(combat2, restored.combat2);
 		copyEnemies(combat3, restored.combat3);
 		copyEnemies(combat4, restored.combat4);

 		infected = new ArrayList<>(restored.infected);
 		healthy = new ArrayList<>(restored.healthy);
 		heroes = new ArrayList<>(restored.heroes);
 		story = restored.story == null ? null : new ArrayList<>(restored.story);
 		combats = copyCombatList(restored.combats);
 		bookmark = restored.bookmark;
 		rand = restored.rand == null ? new Random() : restored.rand;
 	}

 	private void copyEnemies(List<Enemy> target, List<Enemy> source) {
 		target.clear();
 		target.addAll(source);
 	}

 	private List<List<Enemy>> copyCombatList(List<List<Enemy>> source) {
 		if (source == null) {
 			return null;
 		}

 		List<List<Enemy>> copy = new ArrayList<>();
 		for (List<Enemy> combat : source) {
 			copy.add(combat == null ? null : new ArrayList<>(combat));
 		}
 		return copy;
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
 		combat2.add(EnemyBuilder.buildEnemy("draug"));
 		if(heroes.size() > 2) {
 			combat2.add(EnemyBuilder.buildEnemy("draug"));
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

 		int n = rand.nextInt(heroes.size());
 		game.setInfected(n);
 		
 		if (heroes.size() == 4) {
 			int y = rand.nextInt(4);
 			while (y == n) y = rand.nextInt(4);
 			game.setInfected(y);
 		}

 		story.add(s.endThirdChapter());

 		story.add(s.startFourthChapter());

 		combats.add(combat4);

 		story.add(s.endFourthChapter());
	
 		story.add(s.chapterFith());
	
 		combats.add(null);
 		story.add(s.chapterFinal(new ArrayList<>()));
 	}
 	
 	public void addHero(Hero h) {
 		heroes.add(h);
 	}
 	
 	public void setInfected(int infectedHero) {
    	infected.add(heroes.get(infectedHero));
    	s.setInfected(infectedHero);
    }
 	
 	public void next(Combat cb) { //calls the corresponding functions in combat with the next part of the plot
 		
 		if (bookmark == index.length) game.end();
	 		else {
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
	 		else if(story_or_combat == "end") {
	 			game.end();
	 		}
 		}
 		// else if (n == null) {
			
 		// }
		
 	}
	
 }
