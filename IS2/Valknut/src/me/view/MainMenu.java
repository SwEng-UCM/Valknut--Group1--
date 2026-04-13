package me.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        title = new JLabel(rescalate(1100, 600, new ImageIcon("resources/images/valknut_logo.png")));
        gbcMenu.gridy = 0;
        gbcMenu.weighty = 0.0; 
        gbcMenu.insets = new Insets(0, 0, -60, 0);
        this.add(title, gbcMenu);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        
        gbcMenu.gridy = 1;
        gbcMenu.weighty = 0.1; 
        gbcMenu.anchor = GridBagConstraints.NORTH; // Horizontal stretch

        GridBagConstraints gbc = new GridBagConstraints();

        btnPlay = createButton("resources/images/playButton_NS.png", "resources/images/playButton_S.png");
        btnPlay.addActionListener(e -> {
            _ctrl.charactersScreen();
        });
        btnMP = createButton("resources/images/multiButton_NS.png", "resources/images/multiButton_S.png");
        btnSettings = createButton("resources/images/settingsButton_NS.png", "resources/images/settingsButton_S.png");

        JButton btnLoad = new JButton("LOAD GAME");

        btnLoad.setPreferredSize(new Dimension(400, 80));
        btnLoad.setFocusPainted(false);
        btnLoad.setContentAreaFilled(false);
        btnLoad.setBorderPainted(true);

        btnLoad.addActionListener(e -> {
            _ctrl.loadGame();
        });

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(-20, 0, 0, 0);
        gbc.weighty = 0.0;

        gbc.gridy = 0; buttonPanel.add(btnPlay, gbc);
        gbc.gridy = 1; buttonPanel.add(btnLoad, gbc);
        gbc.gridy = 2; buttonPanel.add(btnMP, gbc);
        gbc.gridy = 3; buttonPanel.add(btnSettings, gbc);

        this.add(buttonPanel, gbcMenu);

    }

    private JButton createButton(String path, String over){
        JButton jb = new JButton();
        jb.setIcon(rescalate(400, 150, new ImageIcon(path)));
        jb.setContentAreaFilled(false); 
        jb.setBorderPainted(false);     
        jb.setFocusPainted(false);      
        jb.setOpaque(false);
        jb.setRolloverEnabled(true);
        jb.setRolloverIcon(rescalate(400, 150, new ImageIcon(over)));
        jb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                am.sound("resources/sounds/selection.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        return jb;
    }

    private ImageIcon rescalate(int width, int height, ImageIcon icon){
		ImageIcon scalated_icon;
		Image im_icon = icon.getImage();
		Image scalated_im = im_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		scalated_icon = new ImageIcon(scalated_im);
		return scalated_icon;
	}
}
