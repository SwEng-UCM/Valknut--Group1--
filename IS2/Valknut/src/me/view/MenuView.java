package me.view;

public class MenuView  extends ConsoleIO{

    private static MenuView mv;

    private MenuView(){
        super();
    }

    public static MenuView getInstance(){

        if(mv == null){
            mv = new MenuView();
        }

        return mv;
    }
}
