package me.socket;
import javax.swing.*;
import me.control.*;
import me.model.Game;
import me.model.Hero;

public class MultiplayerManager extends JFrame{

    private static MultiplayerManager instance;
    private ChatScreen cs;
    private final Controller ctrl;
    private final Game game;
    private JPanel mainPanel;
    private UserObject user;
    private final Dispatcher dispatcher;

    private MultiplayerManager(Controller ctrl, Game game){
        this.ctrl = ctrl;
        this.game = game;
        this.dispatcher = new Dispatcher(ctrl, game);
    }

    public static MultiplayerManager getInstacne(Controller ctrl, Game game){
        if(instance == null){
            instance = new MultiplayerManager(ctrl, game);
        }
        return instance;
    }

    public UserObject recieveNotification(int id, String ip) {
        System.out.println((id == 1 ? "Server" : "Client"));
        switch (id) {
            case 1 -> { 
                user = new Server(null, 0, 0, null, this); 
                System.out.println("Server created");
                user.set();
            }
            case 2 -> { 
                user = new Client(null, 0, 0, null, ip, this); 
                System.out.println("Client created");
                user.set(); 
                System.out.println("Client seted");
            }
            default ->{}
        }

        return user;
    }

    public User getUser(){
        return user;
    }

    public void send(Request rq){
        user.send(rq);
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
        int id = user.getId();
        ctrl.setGameMode(me.model.Game.GameMode.MULTIPLAYER);
        if(id == 2)
            ctrl.addHero(new Hero(" ", 0, 0, " ", 1));
        ctrl.addHero(user);
        if(id == 1)
            ctrl.addHero(new Hero(" ", 0, 0, " ", 2));
        ctrl.charactersScreen();
    }
}
