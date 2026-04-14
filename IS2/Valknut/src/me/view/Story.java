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
	
	

}
