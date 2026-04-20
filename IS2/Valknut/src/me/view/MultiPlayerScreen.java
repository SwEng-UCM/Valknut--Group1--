package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class MultiPlayerScreen extends JPanel{
    private static MultiPlayerScreen instance;
    private Thread searchPlayer;
    private boolean searching = false;
    private boolean server;
    private AudioManager am;
    private Controller _ctrl;
    private Image backGround;
    private JoinDialog joinDialog;
    private JButton join;
    private JButton host;
    private JButton exit;
    private JButton settings;

    private MultiPlayerScreen(Controller ctrl){
         _ctrl = ctrl;
        am = AudioManager.getInstance();
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

    public static MultiPlayerScreen getInstance(Controller ctrl){
        if(instance == null)
            instance = new MultiPlayerScreen(ctrl);
        return instance;
    }

    private void initGUI(){
        this.backGround = new ImageIcon(Messages.MULTISCREEN).getImage();
        this.setVisible(true);
        this.setOpaque(false);
    }

    private void setComponents(){
        this.setLayout(new GridBagLayout());
        
        join = ViewUtils.createButton("resources/images/Buttons/joinButton_NS.png", "resources/images/Buttons/joinButton_S.png");
        join.addActionListener(e -> {
            joinDialog = new JoinDialog();
            joinDialog.setVisible(true);
            
            String ip = joinDialog.getIP();
            server = false;
            setWaitingComponents();
            searchPlayer = new Thread(() -> {
                _ctrl.initMultiplayerMode(1, ip);
            }); //server mode
            searchPlayer.start();
            
        });
        host = ViewUtils.createButton("resources/images/Buttons/hostButton_NS.png", "resources/images/Buttons/hostButton_S.png");
        host.addActionListener(e -> {
            server = true;
            setWaitingComponents();
            searching = true;
            searchPlayer = new Thread(() -> {
                _ctrl.initMultiplayerMode(0, null);
            }); //server mode
            searchPlayer.start();
        });
        exit = ViewUtils.createButton("resources/images/Buttons/exitButton_NS.png", "resources/images/Buttons/exitButton_S.png");
        exit.addActionListener(e -> {
            AudioManager.getInstance().sound("resources/sounds/selection_click.wav");
            _ctrl.startGame();
        });
        settings = ViewUtils.createButton("resources/images/Buttons/settingsButton_NS.png", "resources/images/Buttons/settingsButton_S.png");
        settings.addActionListener(e -> {
            AudioManager.getInstance().sound("resources/sounds/selection_click.wav");
            _ctrl.setPreviousScreenToSettings("MULTIPLAYER", Messages.MULTISCREEN); 
            _ctrl.settingScreen();
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; 
        this.add(host, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        this.add(join, gbc);

        gbc.gridx = 0;     
        gbc.gridy = 1;      
        gbc.gridwidth = 1;  
        this.add(settings, gbc);

        gbc.gridx = 1;     
        gbc.gridy = 1;      
        gbc.gridwidth = 1;  
        this.add(exit, gbc);

        this.add(exit, gbc);

    }

    private void setWaitingComponents(){
        this.removeAll();
        this.setLayout(new GridBagLayout());

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel titulo = new JLabel("Waiting...");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(titulo, gbc);

        gbc.gridy = 2; 
        exit = ViewUtils.createButton("resources/images/Buttons/exitButton_NS.png", "resources/images/Buttons/exitButton_S.png");;
        exit.addActionListener(e -> {
            AudioManager.getInstance().sound("resources/sounds/selection_click.wav");
            searching = false;
            searchPlayer.interrupt();
            if(server)
                _ctrl.killServer();
            this.removeAll();
            setComponents();
            revalidate();
            repaint();
            _ctrl.startGame();
            
        });
        this.add(exit, gbc);

        revalidate();
        repaint();
    }
}
