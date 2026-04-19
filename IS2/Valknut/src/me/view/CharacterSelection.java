package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

/**
 * Simple character selection screen with background and character images.
 */
public class CharacterSelection extends JPanel {

    private static CharacterSelection instance;

    private final Controller ctrl;

    private Image backGround;

    private JButton freyaBtn;
    private JButton lokiBtn;
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

        JLabel freyaImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/gersemi.png")
                        .getImage()
                        .getScaledInstance(300, 450, Image.SCALE_SMOOTH)
        ));
        freyaImage.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lokiImage = new JLabel(new ImageIcon(
                new ImageIcon("resources/images/Characters/vali.png")
                        .getImage()
                        .getScaledInstance(300, 450, Image.SCALE_SMOOTH)
        ));
        lokiImage.setHorizontalAlignment(SwingConstants.CENTER);

        freyaBtn = new JButton("Select Freya");
        lokiBtn = new JButton("Select Loki");
        startBtn = new JButton("Start Game");

        freyaPanel.add(freyaImage, BorderLayout.CENTER);
        freyaPanel.add(freyaBtn, BorderLayout.SOUTH);

        lokiPanel.add(lokiImage, BorderLayout.CENTER);
        lokiPanel.add(lokiBtn, BorderLayout.SOUTH);

        characterPanel.add(freyaPanel);
        characterPanel.add(lokiPanel);

        JPanel middleWrapper = new JPanel(new BorderLayout());
        middleWrapper.setOpaque(false);
        middleWrapper.add(infoPanel, BorderLayout.NORTH);
        middleWrapper.add(characterPanel, BorderLayout.CENTER);

        add(middleWrapper, BorderLayout.CENTER);

        startBtn.setEnabled(false);
        add(startBtn, BorderLayout.SOUTH);

        freyaBtn.addActionListener(e -> selectCharacter(0, "Freya"));
        lokiBtn.addActionListener(e -> selectCharacter(1, "Loki"));
        startBtn.addActionListener(e -> startGame());
    }

    /**
     * Handles character selection.
     *
     * @param index 0 = Freya, 1 = Loki
     * @param name character name for GUI feedback
     */
    private void selectCharacter(int index, String name) {
        if (player > 2) {
            return;
        }

        ctrl.selectCharacter(index, player);
        selectionLabel.setText("Player " + player + " selected " + name + ".");

        if (index == 0) {
            freyaBtn.setEnabled(false);
            freyaBtn.setBackground(Color.GREEN);
        } else {
            lokiBtn.setEnabled(false);
            lokiBtn.setBackground(Color.GREEN);
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