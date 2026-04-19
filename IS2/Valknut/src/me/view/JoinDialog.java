package me.view;

import javax.swing.*;

public class JoinDialog extends JDialog{

    public JoinDialog(){
        initGUI();
    }

    private void initGUI(){
        setTitle("Multiplayer");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);
        this.setLocationRelativeTo(null);
    }
}
