/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.socket;
import javax.swing.*;
import me.control.*;
import me.model.Game;
import me.model.Hero;
import me.model.HeroEnum;
import me.view.CharacterSelection;
import me.view.CombatScreen;

public class MultiplayerManager extends JFrame{

    private static MultiplayerManager instance;
    private CharacterSelection characterSelection;
    private CombatScreen combatScreen;
    private final Controller ctrl;
    private final Game game;
    private JPanel mainPanel;
    private UserObject user;
    private final Dispatcher dispatcher;

    private MultiplayerManager(Controller ctrl, Game game){
        this.ctrl = ctrl;
        this.game = game;
        this.dispatcher = new Dispatcher(ctrl);
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

    public void chooseCharacter(HeroEnum he){
        characterSelection = CharacterSelection.getInstance(null);
        characterSelection.selectCharacter(he);
    }

    public void startGame(){
        characterSelection.startGame();
    }

    public void initCS(){
        if(combatScreen == null)
            combatScreen = CombatScreen.getInstance(null);
    }

    public void continueCombat(){
        combatScreen.changeActionPanel("COMMANDS");
        combatScreen.refresh();
    }

    public void attackAction(int tarjet){
        combatScreen.attackAction(tarjet);
    }

    public void consumTextLog(){
        combatScreen.consumeTextLog();
    }

    public void refreshCombatScreen(){
        combatScreen.refresh();
    }
}
