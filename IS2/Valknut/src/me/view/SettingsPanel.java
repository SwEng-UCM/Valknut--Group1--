package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class SettingsPanel extends JPanel{
    private static SettingsPanel instance;
    private Controller _ctrl;
    private Image backGround;
    private JButton exit;
    private JButton stop;
    private JButton increase;
    private JButton decrease;
    private int volume = 50;
    private JLabel lblVolumen;
    private String previousScreen;

    private SettingsPanel(Controller ctrl){
         _ctrl = ctrl;
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

    public void setPreviousScreen(String s){
        previousScreen = s;
    }

    public static SettingsPanel getInstance(Controller ctrl){
        if(instance == null)
            instance = new SettingsPanel(ctrl);
        return instance;
    }

    private void initGUI(){
        this.backGround = new ImageIcon("resources/images/MainMenu.png").getImage();
        this.setVisible(true);
        this.setOpaque(false);
    }

    private void setComponents(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        lblVolumen = new JLabel("Music: " + volume);
        lblVolumen.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(100, 10, 200, 10);
        this.add(lblVolumen, gbc);


        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbcPanel = new GridBagConstraints();

        gbcPanel.gridx = 0; 
        gbcPanel.gridy = 0;
        gbcPanel.fill = GridBagConstraints.HORIZONTAL; 
        gbcPanel.weightx = 1.0; 
        
        increase = ViewUtils.createButton("resources\\images\\Buttons\\addButton_NS.png", "resources\\images\\Buttons\\addButton_S.png");
        increase.addActionListener(e ->{
            volume += 5;
            volume = Math.min(volume, 100);
            AudioManager.getInstance().setVolume(volume);
            String s = (volume == 100 ? "Music:" : volume > 9 ? "Music: " : "Music:  ");
            lblVolumen.setText(s + volume);
        });
        decrease = ViewUtils.createButton("resources\\images\\Buttons\\subButton_NS.png", "resources\\images\\Buttons\\subButton_S.png");
        decrease.addActionListener(e ->{
            volume -= 5;
            volume = Math.max(0, volume);
            AudioManager.getInstance().setVolume(volume);
            String s = (volume == 100 ? "Music:" : volume > 9 ? "Music: " : "Music:  ");
            lblVolumen.setText(s + volume);
        });
        stop = ViewUtils.createButton("resources\\images\\Buttons\\stopButton_NS.png", "resources\\images\\Buttons\\stopButton_S.png");
        stop.addActionListener(e ->{
            volume = 50;
            AudioManager.getInstance().setVolume(volume);
            AudioManager.getInstance().stopMusic();
            lblVolumen.setText("Music: " + volume);
        });

        gbcPanel.gridx = 0; buttonPanel.add(increase, gbcPanel);
        gbcPanel.gridx = 1; buttonPanel.add(decrease, gbcPanel);
        gbcPanel.gridx = 2; buttonPanel.add(stop, gbcPanel);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTH; 

        this.add(buttonPanel, gbc);

        exit = ViewUtils.createButton("resources\\images\\Buttons\\exitButton_NS.png", "resources\\images\\Buttons\\exitButton_S.png");
        exit.addActionListener(e ->{
            AudioManager.getInstance().sound("resources/sounds/selection_click.wav");
            switch (previousScreen) {
                case "MENU" -> _ctrl.menuScreen();
                case "MULTIPLAYER" -> _ctrl.multiplayerScreen();
                default -> _ctrl.menuScreen();
            }
        });
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(100, 20, 0, 20);
        gbc.weightx = 0.1; 
        this.add(exit, gbc);
    }
    
}
