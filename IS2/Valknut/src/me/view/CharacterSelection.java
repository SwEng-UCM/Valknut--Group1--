package me.view;

import javax.swing.JPanel;
import me.control.Controller;

public class CharacterSelection extends JPanel{

    private static CharacterSelection instance;
    private Controller _ctrl;

    private CharacterSelection(Controller ctrl){
        _ctrl = ctrl;
    }

    public static CharacterSelection getInstace(Controller ctrl){
        if(instance == null)
            instance = new CharacterSelection(ctrl);
        return instance;
    }

}
