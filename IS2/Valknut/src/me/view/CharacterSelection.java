package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

/**
 * Simple character selection screen.
 * This is a minimal implementation used to enable game flow.
 */
public class CharacterSelection extends JPanel {

    private static CharacterSelection instance;

    private final Controller ctrl;

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
     * Initializes the panel layout.
     */
    private void initGUI() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
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

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 20, 20));
        centerPanel.setBackground(new Color(220, 225, 232));

        freyaBtn = new JButton("Select Freya");
        lokiBtn = new JButton("Select Loki");
        startBtn = new JButton("Start Game");

        infoLabel = new JLabel("Player 1: choose a character", SwingConstants.CENTER);
        selectionLabel = new JLabel("No characters selected yet.", SwingConstants.CENTER);

        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        selectionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(infoLabel);
        infoPanel.add(selectionLabel);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonsPanel.setBackground(new Color(220, 225, 232));
        buttonsPanel.add(freyaBtn);
        buttonsPanel.add(lokiBtn);

        JPanel middleWrapper = new JPanel(new BorderLayout());
        middleWrapper.setBackground(new Color(220, 225, 232));
        middleWrapper.add(infoPanel, BorderLayout.NORTH);
        middleWrapper.add(buttonsPanel, BorderLayout.CENTER);

        add(middleWrapper, BorderLayout.CENTER);

        startBtn.setEnabled(false);
        add(startBtn, BorderLayout.SOUTH);

        // Button actions
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

        ctrl.startStory();
    }
}