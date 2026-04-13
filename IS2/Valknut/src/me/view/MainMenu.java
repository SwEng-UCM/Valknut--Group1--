package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;

public class MainMenu extends JPanel{

    private static MainMenu instance;
    private Controller _ctrl;
    private Image backGround;

    //Components
    private JButton btnPlay;
    private JButton btnSettings;
    private JButton btnMP;
    private JLabel title;

    private MainMenu(Controller ctrl){
        _ctrl = ctrl;
        
        initGUI();
        setComponents();
    }

    public static MainMenu getInstance(Controller ctrl){
        if(instance == null)
            instance = new MainMenu(ctrl);
        return instance;
    }

    private void initGUI() {
        try {
            this.backGround = new ImageIcon("resources/images/MainMenu.png").getImage();
            this.setLayout(new BorderLayout());
            this.setVisible(true);
            this.setOpaque(false);
        } catch (Exception e) {
            System.err.println("Error: No se pudo cargar el fondo del menú.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void setComponents(){
        title = new JLabel(rescalate(900, 500, new ImageIcon("resources\\images\\valknut_logo.png")));
        this.add(title, BorderLayout.PAGE_START);
    }

    private ImageIcon rescalate(int width, int height, ImageIcon icon){
		ImageIcon scalated_icon;
		Image im_icon = icon.getImage();
		Image scalated_im = im_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		scalated_icon = new ImageIcon(scalated_im);
		return scalated_icon;
	}
}
