package me.model;

 import java.util.ArrayDeque;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Queue;
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
 	private Queue<Object[]> toDo;
	
 	public Storyteller(Game game) {
 		this.game = game;
 		this.toDo = new ArrayDeque<>(90);
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
		
// 		//4rth Combat enemies
// 		combat4.add(EnemyBuilder.buildEnemy("skoll"));
// 		combat4.add(EnemyBuilder.buildEnemy("hati"));
		
 		List<String> story = new ArrayList<>();
 		List<List<Enemy>> combats = new ArrayList<>();
 		String[] index = {"s", "s", "c", "s", "s", "s", "c", "s", "s", "c", "s", "s", "c", "s", "s", "c", "s"};
 		

		story.add(Story.IntroLines);
		
		story.add(Story.startFirstChapter());
		
		combats.add(combat1);

 		story.add(Story.middleFirstChapter());

 		story.add(Story.startSecondChapter());

		
 		story.add( Story.middleSecondChapter());

 		combats.add( combat2);
 		
 		story.add(Story.endSecondChapter());
	
 		story.add(Story.startThirdChapter());

 		combats.add(combat3);

 		story.add(Story.endThirdChapter());

 		story.add(Story.startFourthChapter());

 		combats.add(combat4);

 		story.add(Story.endFourthChapter());
	
 		story.add(Story.chapterFith());
	
 		combats.add(null);
 		story.add(Story.chapterFinal(null));
		
  	}
	
	
 	public void narrate() {
 		game.startNewCmb(combat1);
 	}


 	public void next(Combat cb) {
 		Object[] n = toDo.remove();
 		if(n[0] == "story") {
 			game.displayStory((String) n[1]);
 		}
		
 		else if(n[0] == "combat") {
 			game.setEnemies((List<Enemy>) n[1]);
 		}
 		// else if (n == null) {
			
 		// }
		
 	}
	
 }
