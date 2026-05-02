package me.view;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import me.control.Controller;
import me.model.Game;

public class StoryScreen extends JPanel {
	
	private JButton nextButton;
	private String text;
	private final Controller ctrl;
 	private final Game game;
 	private static StoryScreen instance;
 	private JLabel s;
 	
 	
 	StoryScreen(Controller ctrl, Game game) {
 		this.ctrl = ctrl;
 		this.game = game;
 		initGui();
 	}
 	
 	 public static StoryScreen getInstance(Controller ctrl, Game game){
         if(instance == null)
             instance = new StoryScreen(ctrl, game);
         return instance;
     }
 	

 	private void initGui() {
 		 setLayout(new BorderLayout());
 		
 		 this.nextButton = new JButton();
 		 this.nextButton.setToolTipText("Next");
 		 this.nextButton.setIcon(new ImageIcon("resources/images/buttons/nextButton.png"));
 		 this.nextButton.addActionListener((e) -> handleNext());
 		 this.add(nextButton, BorderLayout.PAGE_END);
		
 	}
 	
 	public void setText(String t) {
 		text = t;
 		
 	}
 	private void handleNext() {
 		game.next();
 	}
}
