package me.view;

import java.util.ArrayList;
import java.util.List;

import me.model.Hero;

public class Story {

	public static final String IntroLines = Messages.INTRO_LINES;
	public static final String ChapterOneBegins = Messages.CHAPTER_ONE_BEGINING;
	public List<Hero> heroes;
	public List<Hero> infected;
	public List<Hero> healthy;
			
	public Story(){

	}

	public String getIntro(){
		return IntroLines;
	}

	public void addHeroes(List<Hero> hs){
		heroes = new ArrayList<>(hs);
	}
	
	public static String[] getHeroNames(List<Hero> hl) {
	    String[] hs = new String[hl.size()]; 
	    int i = 0;

	    for (Hero h : hl) {
	        hs[i] = h.name();
	        i++;
	    }

	    return hs;
	}
	
	public static String formatNames(String[] names) {
	    if (names == null || names.length == 0) {
	        return "";
	    }

	    if (names.length == 1) {
	        return names[0];
	    }

	    if (names.length == 2) {
	        return names[0] + " and " + names[1];
	    }

	    StringBuilder result = new StringBuilder();

	    for (int i = 0; i < names.length; i++) {
	        if (i == names.length - 1) {
	            result.append("and ").append(names[i]);
	        } else {
	            result.append(names[i]);
	            if (i < names.length - 2) {
	                result.append(", ");
	            } else {
	                result.append(" ");
	            }
	        }
	    }

	    return result.toString();
	}
	
	//Chapter 1

	public String startFirstChapter(){
		StringBuilder sb = new StringBuilder();
		sb.append("Welcome ").append(formatNames(getHeroNames(heroes))).append(Messages.NEW_LINE);
		sb.append(ChapterOneBegins).append(Messages.NEW_LINE);
		return sb.toString();
	}
	
	public String middleFirstChapter() {		
		return Messages.CHAPTER_ONE_MIDDLE;
	}
	
	public String endFirstChapter() {
		return Messages.CHAPTER_ONE_END;
	}
	
	
	// Chapter 2
	
	public String startSecondChapter() {
		return Messages.CHAPTER_TWO_BEGINING;
	}
	
	public String middleSecondChapter() {
		return String.format(Messages.CHAPTER_TWO_MIDDLE, formatNames(getHeroNames(heroes)));
	}
	
	public String endSecongChapter() {
		return Messages.CHAPTER_TWO_END;
	}
	
	//Chapter 3
	
	public String startThridChapter() {
		return Messages.CHAPTER_THREE_BEGGINING;
	}
 	
	public String conflictThridChapter() { //Decide how to announce whitch player gets transformed
		return "";
	}
	
	public String endThridChapter() {
		return Messages.CHAPTER_THREE_END;
	}
	
	//Chapter 4 
	
	public String startFourthChapter() {
		String mutter = "mutters";
		if(infected.size() > 1) {
			mutter = "mutter";
		}
		return String.format(Messages.CHAPTER_FOUR_BEGGINING, formatNames(getHeroNames(infected)), mutter);
	}
	
	public String endFourthChapter() {
		String say = "says";
		if(infected.size() > 1) {
			say = "say";
		}
		return String.format(Messages.CHAPTER_FOUR_END, formatNames(getHeroNames(infected)), say, formatNames(getHeroNames(healthy)), formatNames(getHeroNames(healthy)));
	}
	
	//Chapter 5
	
	public String chapterFith() {//infected, healthy, infected,  healthy, infected, healthy
		return String.format(Messages.CHAPTER_FIVE, formatNames(getHeroNames(infected)), formatNames(getHeroNames(healthy)), formatNames(getHeroNames(infected)), formatNames(getHeroNames(healthy)), formatNames(getHeroNames(infected)), formatNames(getHeroNames(healthy)));
	}
	
	
	//Final chapter
	public String chapterFinal(List<Hero> lost) {
		return String.format(Messages.CHAPTER_FINAL, formatNames(getHeroNames(lost)));
	}

}
