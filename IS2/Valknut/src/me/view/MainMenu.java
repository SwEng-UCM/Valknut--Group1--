package me.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel title;

    // Base path to resources folder (always correct at runtime)
    private static final String RES_PATH =
            System.getProperty("user.dir") + "/IS2/Valknut/resources/";

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
                RES_PATH + "images/MainMenu.png"
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

        title = new JLabel(rescalate(
                1200,
                600,
                new ImageIcon(RES_PATH + "images/valknut_logo.png")
        ));

        gbcMenu.gridy = 0;
        gbcMenu.insets = new Insets(-20, 0, -60, 0);
        this.add(title, gbcMenu);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        gbcMenu.gridy = 1;

        GridBagConstraints gbc = new GridBagConstraints();

        btnPlay = createButton(
                RES_PATH + "images/playButton_NS.png",
                RES_PATH + "images/playButton_S.png"
        );

        btnPlay.addActionListener(e -> _ctrl.charactersScreen());

        JButton btnLoad = new JButton("LOAD GAME");
        btnLoad.setPreferredSize(new Dimension(200, 80));
        btnLoad.addActionListener(e -> _ctrl.loadGame());

        btnMP = createButton(
                RES_PATH + "images/multiButton_NS.png",
                RES_PATH + "images/multiButton_S.png"
        );

        btnSettings = createButton(
                RES_PATH + "images/settingsButton_NS.png",
                RES_PATH + "images/settingsButton_S.png"
        );

        gbc.gridy = 0;
        gbc.insets = new Insets(250, 20, 0, 20);

        gbc.gridx = 0;
        buttonPanel.add(btnMP, gbc);

        gbc.gridx = 1;
        buttonPanel.add(btnPlay, gbc);

        gbc.gridx = 2;
        buttonPanel.add(btnLoad, gbc);

        gbc.gridx = 3;
        buttonPanel.add(btnSettings, gbc);

        this.add(buttonPanel, gbcMenu);
    }

    private JButton createButton(String path, String over) {

        JButton jb = new JButton();

        jb.setIcon(rescalate(300, 100, new ImageIcon(path)));
        jb.setRolloverIcon(rescalate(300, 100, new ImageIcon(over)));

        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setFocusPainted(false);
        jb.setOpaque(false);
        jb.setRolloverEnabled(true);

        jb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                am.sound("resources/sounds/selection.wav");
            }
        });

        return jb;
    }

    private ImageIcon rescalate(int width, int height, ImageIcon icon) {
        Image im_icon = icon.getImage();
        Image scalated_im = im_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scalated_im);
    }
}