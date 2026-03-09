package me.view;

import me.model.HeroBuilder;

public class StoryView extends ConsoleIO{

    private static StoryView sv;

    private StoryView(){
        super();
    }

    public static StoryView getInstance(){

        if(sv == null){
            sv = new StoryView();
        }

        return sv;
    }

    public void tellIntro() {
		printLine(Story.IntroLines);
		printLine(Messages.NEW_LINE);
	}

    public Integer selectCharacter(){
		HeroBuilder hb = new HeroBuilder();
		print(hb.getPossibleHeroes());
		print("Select: ");
		Integer i = parseIntInRange(1, hb.getHeroes().size());
        return i;
    }

}
