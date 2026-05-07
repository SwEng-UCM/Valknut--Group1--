/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.view;

import java.util.ArrayList;
import java.util.List;
import me.model.Hero;

public class StoryView extends ConsoleIO{

    private static StoryView sv;
    private Story st;

    private StoryView(Story st){
        super();
        this.st = st;
    }

    public static StoryView getInstance(){

        if(sv == null){
            sv = new StoryView(new Story());
        }

        return sv;
    }

    public void tellIntro() {
        printLine(Messages.NEW_LINE);
		printLine(st.getIntro());
		print(Messages.NEW_LINE);
        printLine(Messages.CHOOSE);
        print(Messages.NEW_LINE);
	}

    public void tellFirstLinesChapterOne(Hero h1, Hero h2){
        List<Hero> aux = new ArrayList<>();
        aux.add(h1); aux.add(h2);
        st.addHeroes(aux);
        printLine(st.startFirstChapter());
        print(Messages.NEW_LINE);
    }
}
