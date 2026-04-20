package me.view;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;

public class JoinDialog extends JDialog{

    JTextField tf;

    public JoinDialog(){
        initGUI();
    }

    private void initGUI(){
        this.setTitle("Multiplayer");
    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel ipRow = new JPanel();
    ipRow.setLayout(new BoxLayout(ipRow, BoxLayout.X_AXIS));

    JLabel text = new JLabel("IP: ");
    tf = new JTextField(15); 
    tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25)); 

    ipRow.add(text);
    ipRow.add(tf);

    mainPanel.add(ipRow);
    
    mainPanel.add(Box.createVerticalStrut(10)); 
    JButton connectBtn = new JButton("Connect");
    connectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    connectBtn.addActionListener(e -> {
        this.dispose();
    });
    mainPanel.add(connectBtn);

    this.setContentPane(mainPanel);
    this.setModal(true);
    this.pack();
    this.setLocationRelativeTo(null);
    }

    public String getIP(){
        return tf.getText().trim();
    }
}
