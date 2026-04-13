package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class CombatScreen {
    private static CombatScreen instance;
    private AudioManager am;
    private Controller _ctrl;
    private Image backGround;

    private CombatScreen(Controller ctrl){
         _ctrl = ctrl;
        am = AudioManager.getInstance();
        initGUI();
        setComponents();
    }

    public static CombatScreen getInstace(Controller ctrl){
        if(instance == null)
            instance = new CombatScreen(ctrl);
        return instance;
    }

    private void initGUI(){
        this.backGround = new ImageIcon("resources/images/MainMenu.png").getImage();
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setOpaque(false);
    }

    private void setComponents(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbcMenu = new GridBagConstraints();
        gbcMenu.gridx = 0;
        gbcMenu.fill = GridBagConstraints.NONE; 
        gbcMenu.anchor = GridBagConstraints.NORTH;
    }
}
