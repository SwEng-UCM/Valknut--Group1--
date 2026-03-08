package me.view;

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

}
