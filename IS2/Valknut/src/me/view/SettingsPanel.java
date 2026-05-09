/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
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
    private JButton ah;
    private int volume = 50;
    private JLabel lblVolumen;
    private String previousScreen;

    private SettingsPanel(Controller ctrl){
         _ctrl = ctrl;
        initGUI();
        setComponents();
    }

    public void setBackground(String s){
        backGround = new ImageIcon(getClass().getResource(s)).getImage();
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
        this.backGround = new ImageIcon(getClass().getResource(Messages.MAINMENU)).getImage();
        this.setVisible(true);
        this.setOpaque(false);
    }

    private void setComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; // Cambiado de NORTHWEST a CENTER
        gbc.weightx = 1.0; // Permite que la celda se expanda horizontalmente
        gbc.weighty = 0.0; // Controlaremos el vertical fila por fila

        gbc.gridy = 0;
        gbc.weighty = 0.2; 
        this.add(new Box.Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(0,0)), gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbcPanel = new GridBagConstraints();
        
        gbcPanel.insets = new Insets(5, 5, 5, 5); 
        gbcPanel.anchor = GridBagConstraints.WEST;

        lblVolumen = new JLabel("Music: " + volume);
        lblVolumen.setForeground(Color.WHITE);
        gbcPanel.gridx = 0; buttonPanel.add(lblVolumen, gbcPanel);

        increase = ViewUtils.createButton(getClass().getResource("/resources/images/Buttons/addButton_NS.png"), getClass().getResource("/resources/images/Buttons/addButton_S.png"));
        increase.addActionListener(e ->{
            volume += 5;
            volume = Math.min(volume, 100);
            AudioManager.getInstance().setVolume(volume);
            String s = (volume == 100 ? "Music:" : volume > 9 ? "Music: " : "Music:  ");
            lblVolumen.setText(s + volume);
        });
        gbcPanel.gridx = 1; buttonPanel.add(increase, gbcPanel);

        decrease = ViewUtils.createButton(getClass().getResource("/resources/images/Buttons/subButton_NS.png"), getClass().getResource("/resources/images/Buttons/subButton_S.png"));
        decrease.addActionListener(e ->{
            volume -= 5;
            volume = Math.max(0, volume);
            AudioManager.getInstance().setVolume(volume);
            String s = (volume == 100 ? "Music:" : volume > 9 ? "Music: " : "Music:  ");
            lblVolumen.setText(s + volume);
        });
        gbcPanel.gridx = 2; buttonPanel.add(decrease, gbcPanel);

        stop = ViewUtils.createButton(getClass().getResource("/resources/images/Buttons/stopButton_NS.png"), getClass().getResource("/resources/images/Buttons/stopButton_S.png"));
        stop.addActionListener(e ->{
            volume = 50;
            AudioManager.getInstance().setVolume(volume);
            AudioManager.getInstance().stopMusic();
            lblVolumen.setText("Music: " + volume);
        });
        gbcPanel.gridx = 3; buttonPanel.add(stop, gbcPanel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(100, 10, 5, 10); 
        this.add(buttonPanel, gbc);

        // AH Button
        gbc.gridy = 2;
        ah = ViewUtils.createButton(getClass().getResource("/resources/images/Buttons/aHButton_NS.png"),getClass().getResource( "/resources/images/Buttons/aHButton_S.png"));
        ah.addActionListener(e -> {
            AHSetterScreen ass = new AHSetterScreen(_ctrl);
            ass.setVisible(true);
        });
        this.add(ah, gbc);

        //Exit Button
        gbc.gridy = 3;
        exit = ViewUtils.createButton(getClass().getResource("/resources/images/Buttons/exitButton_NS.png"), getClass().getResource("/resources/images/Buttons/exitButton_S.png"));
        exit.addActionListener(e ->{
            AudioManager.getInstance().sound(getClass().getResource("/resources/sounds/selection_click.wav"));
            switch (previousScreen) {
                case "MENU" -> _ctrl.menuScreen();
                case "MULTIPLAYER" -> _ctrl.multiplayerScreen();
                case "COMBAT" -> _ctrl.onCombat();
                default -> _ctrl.menuScreen();
            }
        });
        this.add(exit, gbc);

        gbc.gridy = 4;
        gbc.weighty = 0.2; 
        this.add(new Box.Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(0,0)), gbc);
    }
    
}
