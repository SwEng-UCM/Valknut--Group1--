package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class CharacterSelection extends JPanel{

    private static CharacterSelection instance;
    private AudioManager am;
    private Controller _ctrl;
    private Image backGround;

    private CharacterSelection(Controller ctrl){
         _ctrl = ctrl;
        am = AudioManager.getInstance();
        initGUI();
        setComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static CharacterSelection getInstance(Controller ctrl){
        if(instance == null)
            instance = new CharacterSelection(ctrl);
        return instance;
    }

    private void initGUI(){
        this.backGround = new ImageIcon("resources/images/MainMenu.png").getImage();
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
