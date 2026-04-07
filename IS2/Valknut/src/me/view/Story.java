package me.view;

import me.model.Hero;

public class Story {

	public static final String IntroLines = Messages.INTRO_LINES;
	public static final String ChapterOneBegins = Messages.CHAPTER_ONE_BEGINING;
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
		StringBuilder sb = new StringBuilder();
		sb.append("Welcome ").append(h1.name().toUpperCase()).append(" and ").append(h2.name().toUpperCase()).append("! ").append(Messages.NEW_LINE);
		sb.append(ChapterOneBegins).append(Messages.NEW_LINE);
		return sb.toString();
	}

}
