package me.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.type.NullType;
import javax.swing.*;
import me.control.Controller;
import me.model.Game;
import me.model.HeroEnum;
import me.socket.MultiplayerManager;
import me.socket.Request;

/**
 * Simple character selection screen with background and character images.
 */
public class CharacterSelection extends JPanel {

    private static CharacterSelection instance;

    private final Controller ctrl;
    private final Game game;
    private MultiplayerManager mpm = null;
    private Map<HeroEnum, NullType> selectedC;

    private Image backGround;

    private JButton freyaBtn;
    private JButton lokiBtn;
    private JButton skadiBtn;
    private JButton vidarBtn;
    private JButton mortalBtn;
    private JButton startBtn;

    private JLabel infoLabel;
    private JLabel selectionLabel;

    private int player = 1;

    private CharacterSelection(Controller ctrl, Game game) {
        this.ctrl = ctrl;
        this.game = game;
        selectedC = new HashMap<>();
        if(game.isMultiplayer()){
            this.mpm = MultiplayerManager.getInstacne(ctrl,game);
        }
        initGUI();
        setComponents();
    }

    public static CharacterSelection getInstance(Controller ctrl, Game game) {
        if (instance == null) {
            instance = new CharacterSelection(ctrl, game);
        }
        return instance;
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        backGround = new ImageIcon(Messages.SELECTIONSCREEN).getImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Creates and places components on the screen.
     */
    private void setComponents() {
        JLabel title = new JLabel("Select Characters", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);
        title.setOpaque(true);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        add(title, BorderLayout.NORTH);

        infoLabel = new JLabel("Player 1: choose a character", SwingConstants.CENTER);
        selectionLabel = new JLabel("No characters selected yet.", SwingConstants.CENTER);

        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        selectionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setForeground(Color.WHITE);
        selectionLabel.setForeground(Color.WHITE);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(infoLabel);
        infoPanel.add(selectionLabel);

        JPanel characterPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        characterPanel.setOpaque(false);

        JPanel freyaPanel = new JPanel(new BorderLayout());
        freyaPanel.setOpaque(false);

        JPanel lokiPanel = new JPanel(new BorderLayout());
        lokiPanel.setOpaque(false);
        
        JPanel skadiPanel = new JPanel(new BorderLayout());
        skadiPanel.setOpaque(false);
        
        JPanel vidarPanel = new JPanel(new BorderLayout());
        vidarPanel.setOpaque(false);
        
        JPanel mortalPanel = new JPanel(new BorderLayout());
        mortalPanel.setOpaque(false);

        JLabel freyaImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/newgersemi.png")
                        .getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH)
        ));
        freyaImage.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lokiImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/newvali.png")
                        .getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH)
        ));
        lokiImage.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel skadiImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/jorunn.png")
                        .getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH)
        ));
        skadiImage.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel vidarImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/viggo.png")
                        .getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH)
        ));
        vidarImage.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel mortalImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/magni.png")
                        .getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH)
        ));
        mortalImage.setHorizontalAlignment(SwingConstants.CENTER);

        freyaBtn = new JButton("Select Gersemi");
        lokiBtn = new JButton("Select Vali");
        skadiBtn = new JButton("Select Jorunn");
        vidarBtn = new JButton("Select Viggo");
        mortalBtn = new JButton("Select Magni");
        startBtn = new JButton("Start Game");

        freyaPanel.add(freyaImage, BorderLayout.CENTER);
        freyaPanel.add(freyaBtn, BorderLayout.SOUTH);

        lokiPanel.add(lokiImage, BorderLayout.CENTER);
        lokiPanel.add(lokiBtn, BorderLayout.SOUTH);
        
        skadiPanel.add(skadiImage, BorderLayout.CENTER);
        skadiPanel.add(skadiBtn, BorderLayout.SOUTH);
        
        vidarPanel.add(vidarImage, BorderLayout.CENTER);
        vidarPanel.add(vidarBtn, BorderLayout.SOUTH);
        
        mortalPanel.add(mortalImage, BorderLayout.CENTER);
        mortalPanel.add(mortalBtn, BorderLayout.SOUTH);

        characterPanel.add(freyaPanel);
        characterPanel.add(lokiPanel);
        characterPanel.add(skadiPanel);
        characterPanel.add(vidarPanel);
        characterPanel.add(mortalPanel);

        JPanel middleWrapper = new JPanel(new BorderLayout());
        middleWrapper.setOpaque(false);
        middleWrapper.add(infoPanel, BorderLayout.NORTH);
        middleWrapper.add(characterPanel, BorderLayout.CENTER);

        add(middleWrapper, BorderLayout.CENTER);

        startBtn.setEnabled(false);
        add(startBtn, BorderLayout.SOUTH);

        freyaBtn.addActionListener(e ->{
            if(mpm != null){
                if(mpm.getUser().getId() == player){
                    Request rq = new Request(Request.RequestType.CHARACTERSELECT, mpm.getUser().getId());
                    rq.addParameter(HeroEnum.GERSEMI);
                    mpm.send(rq);
                    if(player == 1)
                        selectCharacter(HeroEnum.GERSEMI);
                }
            }
            else
                selectCharacter(HeroEnum.GERSEMI);
            selectedC.put(HeroEnum.GERSEMI, null);
        });
        lokiBtn.addActionListener(e ->{ 
            if(mpm != null){
                if(mpm.getUser().getId() == player){
                    Request rq = new Request(Request.RequestType.CHARACTERSELECT, mpm.getUser().getId());
                    rq.addParameter(HeroEnum.VALI);
                    mpm.send(rq);
                    if(player == 1)
                        selectCharacter(HeroEnum.VALI);
                }
            }
            else
                selectCharacter(HeroEnum.VALI);
            selectedC.put(HeroEnum.VALI, null);
        });
        skadiBtn.addActionListener(e -> {
            if(mpm != null){
                if(mpm.getUser().getId() == player){
                    Request rq = new Request(Request.RequestType.CHARACTERSELECT, mpm.getUser().getId());
                    rq.addParameter(HeroEnum.JORUNN);
                    mpm.send(rq);
                    if(player == 1)
                        selectCharacter(HeroEnum.JORUNN);
                }
            }
            else
                selectCharacter(HeroEnum.JORUNN);
            selectedC.put(HeroEnum.JORUNN, null);
        });
        vidarBtn.addActionListener(e -> {
            if(mpm != null){
                System.err.println("I SELECTED VIGGO");
                if(mpm.getUser().getId() == player){
                    Request rq = new Request(Request.RequestType.CHARACTERSELECT, mpm.getUser().getId());
                    rq.addParameter(HeroEnum.VIGGO);
                    mpm.send(rq);
                    if(player == 1)
                        selectCharacter(HeroEnum.VIGGO);
                }
            }
            else
                selectCharacter(HeroEnum.VIGGO);
            selectedC.put(HeroEnum.VIGGO, null);
        });
        mortalBtn.addActionListener(e -> {
            if(mpm != null){
                if(mpm.getUser().getId() == player){
                    Request rq = new Request(Request.RequestType.CHARACTERSELECT, mpm.getUser().getId());
                    rq.addParameter(HeroEnum.MAGNI);
                    mpm.send(rq);
                    if(player == 1)
                        selectCharacter(HeroEnum.MAGNI);
                }
            }
            else
                selectCharacter(HeroEnum.MAGNI);
            selectedC.put(HeroEnum.MAGNI, null);
        });
        startBtn.addActionListener(e -> startGame());
    }

    public void selectCharacter(HeroEnum h) {
        if(player > 4 || (mpm != null && player > 2))
            return;

        game.selectCharacter(h, player);
        selectionLabel.setText("Player " + player + " selected " + h.toString().toUpperCase() + ".");

        switch (h) {
        case HeroEnum.GERSEMI -> {
            freyaBtn.setEnabled(false);
            freyaBtn.setBackground(Color.GREEN);
            }
        case HeroEnum.VALI -> {
            lokiBtn.setEnabled(false);
            lokiBtn.setBackground(Color.GREEN);
            }
        case HeroEnum.JORUNN -> {
            skadiBtn.setEnabled(false);
            skadiBtn.setBackground(Color.GREEN);
            }
        case HeroEnum.VIGGO -> {
            vidarBtn.setEnabled(false);
            vidarBtn.setBackground(Color.GREEN);
            }
        case HeroEnum.MAGNI -> {
            mortalBtn.setEnabled(false);
            mortalBtn.setBackground(Color.GREEN);
            }
        default -> {
            }
        }

        player++;

        if (player > 1) {
            infoLabel.setText("Press Start Game.");
            startBtn.setEnabled(true);
        }
    }

    public void startGame() {
        if(mpm != null){
            if(mpm.getUser().getId() == 2){
                ViewUtils.showErrorMsg("Wait for Player 1 to Start");
                return;
            }
            if(player < 3){
                ViewUtils.showErrorMsg("Player 2 Selects!!");
                return;
            }
            Request rq = new Request(Request.RequestType.CHARACTERSELECT, 1);
            mpm.send(rq);
        }

        if(player == 2){
            game.setMode(Game.GameMode.SOLO);
            HeroEnum h = HeroEnum.randomEnum();
            while(selectedC.containsKey(h))
                h = HeroEnum.randomEnum();
            selectCharacter(h); 
        }
        game.setStoryHeroes();
        selectionLabel.setText("Loading battle...");
        ctrl.startSelectedGame();
    }
}