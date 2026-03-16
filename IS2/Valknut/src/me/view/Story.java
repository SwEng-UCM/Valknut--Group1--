package me.view;

import me.model.Hero;

public class Story {

	public static final String IntroLines = Messages.INTRO_LINES;
	public static final String ChapterOneBegins = Messages.CHAPTER_ONE_BEGINING;
	public String Welcome;
	public Hero h1;
	public Hero h2; 
			
	public Story(){

	}

	public String getIntro(){
		return IntroLines;
	}

	public void addHeroes(Hero h1, Hero h2){
		this.h1 = h1;
		this.h2 = h2;
	}

	public String startFirstChapter(){
		Welcome = "Welcome " + h1.name().toUpperCase() + " and " + h2.name().toUpperCase() + "! ";
		return Welcome + ChapterOneBegins;
	}

}
