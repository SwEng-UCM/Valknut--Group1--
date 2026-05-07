package me.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Miguel Ángel Trejo AI assisted: No
 * 
 */

public class GameOverScreen extends JPanel{
	
	private static GameOverScreen instance;
	private JLabel end;
	private JButton nextButton;
	private JPanel endPanel;
	
	public static GameOverScreen getInstance(){
        if(instance == null)
            instance = new GameOverScreen();
        return instance;
    }
	
	public GameOverScreen() {
		initGUI();
	}

	private void initGUI() {
		this.setVisible(true);
		setLayout(new BorderLayout());
		this.setForeground(new Color(255,255, 255)); //letters color
		this.setBackground(new Color(105,0, 0));
		
		this.nextButton = new JButton("Exit");
		 this.nextButton.setToolTipText("Exit");
		 this.nextButton.addActionListener((e) -> handleNext());
		 JPanel controls = new JPanel();
		 controls.setOpaque(false);
		controls.setPreferredSize(new Dimension(5000, 100));
		 controls.add(nextButton);
		 this.add(controls, BorderLayout.PAGE_START);
		 
		 endPanel = new JPanel();
		 endPanel.setPreferredSize(new Dimension(650, 10000));
		 endPanel.setBackground(new Color(105,0, 0));
		 
		 Font f = new Font(null, Font.PLAIN, 30);
		 end = new JLabel("YOUR PARTY DIED BUT WILL NOT REACH VALHALLA, GAME OVER!");
		 end.setFont(f);
		 end.setPreferredSize(new Dimension(1000, 500));
		 end.setForeground(new Color(255,255, 255)); //letters color
		 endPanel.add(end);
		 this.add(endPanel, BorderLayout.CENTER);
	}

	private void handleNext() {
		System.exit(0);
	}
	
}
