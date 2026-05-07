package me.socket;

import java.net.*;
import javax.swing.*;
import me.control.*;
import me.view.ViewUtils;

public class ServerListScreen extends JDialog {
    private DefaultListModel<String> model;
    private Controller ctrl;
    private JList<String> list;
    private DatagramSocket socket;
    private MultiplayerManager test;

    public ServerListScreen(MultiplayerManager test, Controller ctrl) {
        this.test = test;
        this.ctrl = ctrl;
        initGUI();
    }

    private void initGUI(){
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(ViewUtils.getWindow(this)); 
        this.setLayout(null);

        model = new DefaultListModel<>();
        list = new JList<>(model);

        JTextField textFile = new JTextField();
        textFile.setBounds(100, 100, 200, 30);
        this.add(textFile);

        JButton btnConectar = new JButton("Connect");
        btnConectar.setBounds(230, 215, 100, 30);
        btnConectar.addActionListener(e -> {
            String selected = textFile.getText();
            System.err.println(selected);
            if(selected != null){
                System.out.println("[ServerListScreen:35] Conecting to: " + selected);
                test.recieveNotification(2, selected);
                this.dispose();
                ctrl.charactersScreen();
            }
        });
        this.add(btnConectar);
        JButton exit = new JButton("Exit");
        exit.setBounds(60, 215, 100, 30);
        exit.addActionListener(e -> {
            dispose();
        });
        this.add(exit);
    }
}
