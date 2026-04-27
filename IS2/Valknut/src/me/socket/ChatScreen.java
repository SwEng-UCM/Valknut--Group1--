package me.socket;
import java.awt.*;
import javax.swing.*;

public class ChatScreen extends JPanel{

    private final MultiplayerManager test;
    private final UserObject user;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton, exit;
    
    public ChatScreen(MultiplayerManager test, UserObject user){
        this.test = test;
        this.user = user;
        initGUI();
    }

    private void initGUI(){

        this.setLayout(null);
        this.setSize(600, 500);
        this.setBackground(Color.LIGHT_GRAY);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);    
        chatArea.setWrapStyleWord(true);
        if(user.getId() == 1)
            chatArea.append("Connected as Server..." + '\n');

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(15, 20, 560, 380); 
        this.add(scrollPane);

        exit = new JButton("E");
        exit.setBounds(25, 420, 50, 30);
        exit.addActionListener(e ->{
            SwingUtilities.invokeLater(() -> test.killUser());
            // test.menu();
        });
        this.add(exit);

        inputField = new JTextField();
        inputField.setBounds(85, 420, 380, 30);
        this.add(inputField);

        sendButton = new JButton("Send");
        sendButton.setBounds(475, 420, 100, 30);
        sendButton.addActionListener(e ->{
            String message = inputField.getText();
            Request rq = new Request(Request.RequestType.MESSAGE, user.getId());
            rq.addParameter(message);
            user.send(rq);
            if(user.getId() == 1)
                writeInScreen("You: " + message);
            inputField.setText("");
        });

        inputField.addActionListener(e ->{
            String message = inputField.getText();
            Request rq = new Request(Request.RequestType.MESSAGE, user.getId());
            rq.addParameter(message);
            user.send(rq);
            if(user.getId() == 1)
                writeInScreen("You: " + message);
            inputField.setText("");
        });

        this.add(sendButton);
    }

    public void writeInScreen(String message){
        chatArea.append(message + '\n');
    }

}
