package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class MainMenu extends JPanel{

    private static MainMenu instance;
    private AudioManager am;
    private final Controller _ctrl;
    private Image backGround;

    //Components
    private JButton btnPlay;
    private JButton btnSettings;
    private JButton btnMP;
    private JLabel title;

    private MainMenu(Controller ctrl){
        _ctrl = ctrl;
        am = AudioManager.getInstance();
        initGUI();
        setComponents();
    }

    public static MainMenu getInstance(Controller ctrl){
        if(instance == null)
            instance = new MainMenu(ctrl);
        return instance;
    }

    private void initGUI() {
        this.backGround = new ImageIcon("resources/images/MainMenu.png").getImage();
        this.setVisible(true);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void setComponents(){

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbcMenu = new GridBagConstraints();
        gbcMenu.gridx = 0;
        gbcMenu.fill = GridBagConstraints.NONE; 
        gbcMenu.anchor = GridBagConstraints.NORTH;

        title = new JLabel(ViewUtils.rescalate(1200, 600, new ImageIcon("resources/images/valknut_logo.png")));
        gbcMenu.gridy = 0;
        gbcMenu.weighty = 0.0; 
        gbcMenu.insets = new Insets(-20, 0, -60, 0);
        this.add(title, gbcMenu);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        
        gbcMenu.gridy = 1;
        gbcMenu.weighty = 0.1; 
        gbcMenu.anchor = GridBagConstraints.NORTH; // Horizontal stretch

        GridBagConstraints gbc = new GridBagConstraints();

        btnPlay = ViewUtils.createButton("resources/images/playButton_NS.png", "resources/images/playButton_S.png");
        btnPlay.addActionListener(e -> {
            _ctrl.charactersScreen();
        });
        btnMP = ViewUtils.createButton("resources/images/multiButton_NS.png", "resources/images/multiButton_S.png");
        btnSettings = ViewUtils.createButton("resources/images/settingsButton_NS.png", "resources/images/settingsButton_S.png");
        btnSettings.addActionListener(e -> {
            _ctrl.settingScreen();
        });

        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(250, 20, 0, 20);
        gbc.weightx = 0.1; 

        gbc.gridx = 1; buttonPanel.add(btnPlay, gbc);
        gbc.gridx = 0; buttonPanel.add(btnMP, gbc);
        gbc.gridx = 2; buttonPanel.add(btnSettings, gbc);

        this.add(buttonPanel, gbcMenu);

    }
    
}
