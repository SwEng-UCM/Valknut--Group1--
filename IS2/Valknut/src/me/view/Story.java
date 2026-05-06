package me.view;

import java.util.ArrayList;
import java.util.List;

import me.model.Hero;
//@author Gadea Domínguez. AI-assisted: yes, chatGPT for the formatNames() function exclusively

public class Story {

	public static final String IntroLines = Messages.INTRO_LINES;
	public static final String ChapterOneBegins = Messages.CHAPTER_ONE_BEGINING;
	public static List<Hero> heroes;
	public static List<Hero> infected;
	public static List<Hero> healthy;
			
	public Story(){
		heroes = new ArrayList<>();
		infected = new ArrayList<>();
		healthy = new ArrayList<>();
	}

	public String getIntro(){
		return IntroLines;
	}
	
	public List<Hero> getInfected(){
		return infected;
	}

	public void addHeroes(List<Hero> hs){
		heroes = new ArrayList<>(hs);
		healthy = new ArrayList<>(hs);
	}
	
	public void setInfected(int infectedHero) {
    	infected.add(heroes.get(infectedHero));
    	healthy.remove(heroes.get(infectedHero));
    }
	
	public void addHero(Hero h) {
		heroes.add(h);
		healthy.add(h);
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

	public static String startFirstChapter(){
		StringBuilder sb = new StringBuilder();
		sb.append("Welcome ").append(formatNames(getHeroNames(heroes))).append(Messages.NEW_LINE);
		sb.append(ChapterOneBegins).append(Messages.NEW_LINE);
		return sb.toString();
	}
	
	public static String endFirstChapter() {
		return Messages.CHAPTER_ONE_END;
	}
	
	
	// Chapter 2
	
	public static String startSecondChapter() {
		return Messages.CHAPTER_TWO_BEGINING;
	}
	
	public static String middleSecondChapter() {
		return String.format(Messages.CHAPTER_TWO_MIDDLE, formatNames(getHeroNames(heroes)));
	}
	
	public static String endSecondChapter() {
		return Messages.CHAPTER_TWO_END;
	}
	
	//Chapter 3
	
	public static String startThirdChapter() {
		return Messages.CHAPTER_THREE_BEGGINING;
	}
 	
	public static String conflictThirdChapter() { //Decide how to announce whitch player gets transformed
		return "";
	}
	
	public static String endThirdChapter() { //healthy ask, infected reply
		String asks = "asks";
		if(infected.size() > 1) {
			asks = "ask";
		}
		
		String reply = "replies";
		if(infected.size() > 1) {
			reply = "reply";
		}
		return String.format(Messages.CHAPTER_THREE_END, formatNames(getHeroNames(healthy)), asks,formatNames(getHeroNames(infected)), reply);

	}
	
	//Chapter 4 
	
	public static String startFourthChapter() {
		String mutter = "mutters";
		if(infected.size() > 1) {
			mutter = "mutter";
		}
		return String.format(Messages.CHAPTER_FOUR_BEGGINING, formatNames(getHeroNames(infected)), mutter);
	}
	
	public static String endFourthChapter() {
		String say = "says";
		if(infected.size() > 1) {
			say = "say";
		}
		return String.format(Messages.CHAPTER_FOUR_END, formatNames(getHeroNames(infected)), say, formatNames(getHeroNames(healthy)), formatNames(getHeroNames(healthy)));
	}
	
	//Chapter 5
	
	public static String chapterFith() {//infected, healthy, infected,  healthy, infected, healthy
		return String.format(Messages.CHAPTER_FIVE, formatNames(getHeroNames(infected)), formatNames(getHeroNames(healthy)), formatNames(getHeroNames(infected)), formatNames(getHeroNames(healthy)), formatNames(getHeroNames(infected)), formatNames(getHeroNames(healthy)));
	}
	
	
	//Final chapter
	public static String chapterFinal(List<Hero> lost) {
		return String.format(Messages.CHAPTER_FINAL, formatNames(getHeroNames(lost)));
	}

}
