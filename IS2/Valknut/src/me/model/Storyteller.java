package me.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import me.control.Controller;
import me.model.EnemyBuilder;
import me.view.Story;

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
	
	private Controller ctrl;
	private final List<Enemy> combat1 = new ArrayList<>();
	private final List<Enemy> combat2= new ArrayList<>();
	private final List<Enemy> combat3= new ArrayList<>();
	private final List<Enemy> combat4= new ArrayList<>();
	private List<Hero> infected = new ArrayList<>();
	private List<Hero> healthy = new ArrayList<>();
	private List<Hero> heroes = new ArrayList<>();
	private Queue<Object> toDo;
	
	public Storyteller(Controller ctrl) {
		this.ctrl = ctrl;
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
		
		toDo.add(Story.IntroLines);
		toDo.add(Story.startFirstChapter());
		toDo.add(combat1);
		toDo.add(Story.middleFirstChapter());
		toDo.add(Story.startSecondChapter());
		toDo.add(Story.middleSecondChapter());
		toDo.add(combat2);
		toDo.add(Story.endSecondChapter());
		toDo.add(Story.startThirdChapter());
		toDo.add(combat3);
		toDo.add(Story.endThirdChapter());
		toDo.add(Story.startFourthChapter());
		toDo.add(combat4);
		toDo.add(Story.endFourthChapter());
		toDo.add(Story.chapterFith());
		toDo.add(null);
		toDo.add(Story.chapterFinal(null));
		
		
	}
	
	
	public void narrate() {
		ctrl.startNewCmb(combat1);
	}


	public void next(Combat cb) {
		if(toDo.isEmpty()) {
			
		}
		Object n = toDo.remove();
		if(n.getClass() == "".getClass()) {
			ctrl.displayStory((String) n);
		}
		else if(n.getClass() == combat1.getClass()) {
			ctrl.setEnemies((List<Enemy>) n);
		}
		else if (n == null) {
			
		}
		
	}
	
}
