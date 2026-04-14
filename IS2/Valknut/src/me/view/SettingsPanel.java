package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class SettingsPanel extends JPanel{
    private static SettingsPanel instance;
    private JSlider js;
    private Controller _ctrl;
    private Image backGround;
    private JButton exit;

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
        JLabel lblVolumen = new JLabel("Volume:");
        lblVolumen.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(100, 10, 100, 10);
        this.add(lblVolumen, gbc);

        js = new JSlider(0, 100, 50);
        js.setMajorTickSpacing(2);
        js.setPaintTicks(true);     
        js.setPaintLabels(true);    
        js.setOpaque(false);
        js.addChangeListener(e -> {
            int valor = js.getValue();
            AudioManager.getInstance().setVolume(valor);
        });
        gbc.gridx = 1; 
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0; 
        gbc.weighty = 2.0;
        this.add(js, gbc);

        exit = ViewUtils.createButton("resources/images/menuButtons.png", "resources/images/menuButtons.png");
        exit.addActionListener(e ->{
            _ctrl.menuScreen();
        });
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(100, 20, 0, 20);
        gbc.weightx = 0.1; 
        this.add(exit, gbc);
    }

    
}
