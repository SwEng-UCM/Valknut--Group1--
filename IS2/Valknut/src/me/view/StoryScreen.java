package me.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import me.control.Controller;
import me.model.Game;

public class StoryScreen extends JPanel {
	
	private JButton nextButton;
	private String text = "I am the starting text";
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
 		
 		System.out.println("I am StoryScreen  initiating GUI ");
 		 setLayout(new BorderLayout());
 		JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    this.add(mainPanel);
	    
 		 
 		JLabel story = new JLabel(text);
	    mainPanel.add(story);
 		
 		 this.nextButton = new JButton();
 		 this.nextButton.setToolTipText("Next");
 		 this.nextButton.setIcon(new ImageIcon("resources/images/buttons/menuButton.png"));
 		 this.nextButton.addActionListener((e) -> handleNext());
 		 mainPanel.add(nextButton, BorderLayout.PAGE_END);
		
 	}
 	
 	public void setText(String t) {
 		text = t;
 		//repaint();
 		
 	}
 	private void handleNext() {
 		game.next();
 	}
}
