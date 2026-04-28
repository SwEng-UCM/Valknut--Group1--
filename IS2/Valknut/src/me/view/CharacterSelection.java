package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;
import me.model.HeroBuilder;
import me.model.HeroEnum;

/**
 * Simple character selection screen with background and character images.
 */
public class CharacterSelection extends JPanel {

    private static CharacterSelection instance;

    private final Controller ctrl;

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

    private CharacterSelection(Controller ctrl) {
        this.ctrl = ctrl;
        initGUI();
        setComponents();
    }

    /**
     * Returns the singleton instance of the character selection screen.
     *
     * @param ctrl controller reference
     * @return character selection instance
     */
    public static CharacterSelection getInstance(Controller ctrl) {
        if (instance == null) {
            instance = new CharacterSelection(ctrl);
        }
        return instance;
    }

    /**
     * Initializes the panel layout and background image.
     */
    private void initGUI() {
        setLayout(new BorderLayout());
        backGround = new ImageIcon(Messages.SELECTIONSCREEN).getImage();
        setOpaque(false);
    }

    /**
     * Paints the background image.
     *
     * @param g the graphics context
     */
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

        freyaBtn.addActionListener(e -> selectCharacter(HeroEnum.GERSEMI, "Gersemi"));
        lokiBtn.addActionListener(e -> selectCharacter(HeroEnum.VALI, "Vali"));
        skadiBtn.addActionListener(e -> selectCharacter(HeroEnum.JORUNN, "Jorunn"));
        vidarBtn.addActionListener(e -> selectCharacter(HeroEnum.VIGGO, "Viggo"));
        mortalBtn.addActionListener(e -> selectCharacter(HeroEnum.MAGNI, "Magni"));
        startBtn.addActionListener(e -> startGame());
    }

    /**
     * Handles character selection.
     *
     * @param index 0 = Freya, 1 = Loki
     * @param name character name for GUI feedback
     */
    private void selectCharacter(HeroEnum h, String name) {
        if (player > 2) {
            return;
        }

        ctrl.selectCharacter(h, player);
        selectionLabel.setText("Player " + player + " selected " + name + ".");

        switch (h) {
        case HeroEnum.GERSEMI:
        	freyaBtn.setEnabled(false);
            freyaBtn.setBackground(Color.GREEN);
        	break;
        case HeroEnum.VALI:
        	lokiBtn.setEnabled(false);
            lokiBtn.setBackground(Color.GREEN);
        	break;
        case HeroEnum.JORUNN:
        	skadiBtn.setEnabled(false);
            skadiBtn.setBackground(Color.GREEN);
        	break;
        case HeroEnum.VIGGO:
        	vidarBtn.setEnabled(false);
            vidarBtn.setBackground(Color.GREEN);
        	break;
        case HeroEnum.MAGNI:
        	mortalBtn.setEnabled(false);
            mortalBtn.setBackground(Color.GREEN);
        	break;
        default:
        	break;
        }

        player++;

        if (player == 2) {
            infoLabel.setText("Player 2: choose a character");
        } else if (player > 2) {
            infoLabel.setText("Both players selected. Press Start Game.");
            startBtn.setEnabled(true);
        }
    }

    /**
     * Starts the game flow after both players are selected.
     */
    private void startGame() {
        if (player <= 2) {
            JOptionPane.showMessageDialog(this, "Select both players first!");
            return;
        }

        selectionLabel.setText("Loading battle...");
        ctrl.startSelectedGame();
    }
}