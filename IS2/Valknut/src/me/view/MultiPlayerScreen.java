package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class MultiPlayerScreen extends JPanel{
    private static MultiPlayerScreen instance;
    private AudioManager am;
    private Controller _ctrl;
    private Image backGround;
    private JButton join;
    private JButton host;

    private MultiPlayerScreen(Controller ctrl){
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

    public static MultiPlayerScreen getInstance(Controller ctrl){
        if(instance == null)
            instance = new MultiPlayerScreen(ctrl);
        return instance;
    }

    private void initGUI(){
        this.backGround = new ImageIcon(Messages.MULTISCREEN).getImage();
        this.setVisible(true);
        this.setOpaque(false);
    }

    private void setComponents(){
        this.setLayout(new GridBagLayout());
        
        join = ViewUtils.createButton("resources/images/Buttons/joinButton_NS.png", "resources/images/Buttons/joinButton_S.png");
        host = ViewUtils.createButton("resources/images/Buttons/hostButton_NS.png", "resources/images/Buttons/hostButton_S.png");
        JButton exit = ViewUtils.createButton("resources/images/Buttons/exitButton_NS.png", "resources/images/Buttons/exitButton_S.png");
        exit.addActionListener(e -> {
            AudioManager.getInstance().sound("resources/sounds/selection_click.wav");
            _ctrl.startGame();
        });
        JButton settings = ViewUtils.createButton("resources/images/Buttons/settingsButton_NS.png", "resources/images/Buttons/settingsButton_S.png");
        settings.addActionListener(e -> {
            AudioManager.getInstance().sound("resources/sounds/selection_click.wav");
            _ctrl.setPreviousScreenToSettings("MULTIPLAYER"); 
            _ctrl.settingScreen();
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; 
        this.add(host, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        this.add(join, gbc);

        gbc.gridx = 0;     
        gbc.gridy = 1;      
        gbc.gridwidth = 1;  
        this.add(settings, gbc);

        gbc.gridx = 1;     
        gbc.gridy = 1;      
        gbc.gridwidth = 1;  
        this.add(exit, gbc);

        this.add(exit, gbc);

    }
}
