package me.socket;
import javax.swing.*;
import me.control.*;

public class MultiplayerManager extends JFrame{

    ChatScreen cs;
    Controller ctrl;
    JPanel mainPanel;
    UserObject user;
    Dispatcher dispatcher;

    public MultiplayerManager(Controller ctrl){
        this.ctrl = ctrl;
        this.dispatcher = new Dispatcher(ctrl);
    }

    public void recieveNotification(int id, String ip) {
        System.out.println((id == 1 ? "Server" : "Client"));
        switch (id) {
            case 1 -> { 
                user = new Server(this); 
                System.out.println("Server created");
                user.set();
            }
            case 2 -> { 
                user = new Client(ip, this); 
                System.out.println("Client created");
                user.set(); 
                System.out.println("Client seted");
            }
            default ->{}
        }
    }

    public User getUser(){
        return user;
    }

    public void treatRequest(Request rq){
        dispatcher.dispatch(rq, this);
    }

    public void write(String message){
        cs.writeInScreen(message);
    }

    public void openChat(){
        mainPanel.removeAll();

        this.setSize(600, 500); 
        this.setLocationRelativeTo(this);

        cs = new ChatScreen(this, user);
        mainPanel.add(cs);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void killUser(){
        user.disconnect();
    }

    public void start(){
        ctrl.charactersScreen();
    }
}
