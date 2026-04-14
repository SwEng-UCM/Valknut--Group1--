package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class MainMenu extends JPanel {

    private static MainMenu instance;
    private AudioManager am;
    private final Controller _ctrl;
    private Image backGround;

    private JButton btnPlay;
    private JButton btnSettings;
    private JButton btnMP;
    private JButton btnExit;
    private JButton btnLoad;
    private JLabel title;

    private MainMenu(Controller ctrl) {
        _ctrl = ctrl;
        am = AudioManager.getInstance();
        initGUI();
        setComponents();
    }

    public static MainMenu getInstance(Controller ctrl) {
        if (instance == null)
            instance = new MainMenu(ctrl);
        return instance;
    }

    private void initGUI() {

        this.backGround = new ImageIcon(
                "resources/images/MainMenu.png"
        ).getImage();

        this.setVisible(true);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void setComponents() {

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcMenu = new GridBagConstraints();
        gbcMenu.gridx = 0;
        gbcMenu.fill = GridBagConstraints.NONE;
        gbcMenu.anchor = GridBagConstraints.NORTH;

        title = new JLabel(
                ViewUtils.rescalate(
                        1200,
                        600,
                        new ImageIcon("resources/images/valknut_logo.png")
                )
        );

        gbcMenu.gridy = 0;
        gbcMenu.insets = new Insets(0, 0, -60, 0);
        this.add(title, gbcMenu);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        gbcMenu.gridy = 1;

        GridBagConstraints gbc = new GridBagConstraints();

        btnPlay = ViewUtils.createButton(
                "resources/images/Buttons/playButton_NS.png",
                "resources/images/Buttons/playButton_S.png"
        );
        btnPlay.addActionListener(e ->{AudioManager.getInstance().sound("resources/sounds/selection_click.wav"); _ctrl.charactersScreen();});

        btnLoad = new JButton("LOAD GAME");
        btnLoad.setPreferredSize(new Dimension(200, 80));
        btnLoad.addActionListener(e ->{AudioManager.getInstance().sound("resources/sounds/selection_click.wav"); _ctrl.loadGame();});

        btnMP = ViewUtils.createButton(
                "resources/images/Buttons/multiButton_NS.png",
                "resources/images/Buttons/multiButton_S.png"
        );

        btnSettings = ViewUtils.createButton(
                "resources/images/Buttons/settingsButton_NS.png",
                "resources/images/Buttons/settingsButton_S.png"
        );
        btnSettings.addActionListener(e ->{AudioManager.getInstance().sound("resources/sounds/selection_click.wav"); _ctrl.settingScreen();});

        btnExit = ViewUtils.createButton(
                "resources/images/Buttons/exitButton_NS.png",
                "resources/images/Buttons/exitButton_S.png"
        );
        btnExit.addActionListener(e ->_ctrl.exit());

        gbc.gridy = 0;
        gbc.insets = new Insets(300, 20, 220, 20);

        gbc.gridx = 0;
        buttonPanel.add(btnMP, gbc);

        gbc.gridx = 1;
        buttonPanel.add(btnPlay, gbc);

        gbc.gridx = 2;
        buttonPanel.add(btnLoad, gbc);

        gbc.gridx = 3;
        buttonPanel.add(btnSettings, gbc);

        gbc.gridx = 4;
        buttonPanel.add(btnExit, gbc);

        this.add(buttonPanel, gbcMenu);
    }
}