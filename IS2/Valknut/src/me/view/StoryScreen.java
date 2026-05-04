package me.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import me.control.Controller;
import me.model.Game;

public class StoryScreen extends JPanel {
	
	private JButton nextButton;
	private String text = "";
	private final Controller ctrl;
 	private final Game game;
 	private static StoryScreen instance;
 	private JLabel story;
 	private JPanel storyPanel = new JPanel();
 	private Image backGround;
 	private JPanel mainPanel;
 	
 	
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
 		this.backGround = new ImageIcon(Messages.STORYSCREEN).getImage();
        this.setVisible(true);
        this.setOpaque(false);

 		System.out.println("I am StoryScreen  initiating GUI ");
 		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 		
 		 this.nextButton = new JButton("Next");
 		 this.nextButton.setToolTipText("Next");
 		 //this.nextButton.setIcon(new ImageIcon("resources/images/buttons/menuButton.png"));
 		 this.nextButton.addActionListener((e) -> handleNext());
 		 JPanel controls = new JPanel();
 		 controls.setOpaque(false);
 		controls.setPreferredSize(new Dimension(5000, 100));
 		 controls.add(nextButton);

 		 JButton saveButton = new JButton("Save");
 		 saveButton.addActionListener(e -> {
 			 game.saveGame();
 			 javax.swing.JOptionPane.showMessageDialog(this, "Game saved.");
 		 });
 		 controls.add(saveButton);

 		 JButton loadButton = new JButton("Load");
 		 loadButton.addActionListener(e -> {
 			 game.loadGame();
 			 javax.swing.JOptionPane.showMessageDialog(this, "Game loaded.");
 		 });
 		 controls.add(loadButton);

 		 JButton exitButton = new JButton("Exit");
 		 exitButton.addActionListener(e -> ctrl.exit());
 		 controls.add(exitButton);

 		 this.add(controls, BorderLayout.PAGE_START);
//		
 	}
 	
 	public void setText(String t) {
 		storyPanel.removeAll();
 		text = t;
 		text = Messages.startFormat + text + Messages.endFormat;
 		story = new JLabel(text);
 		story.repaint();
 		storyPanel.setPreferredSize(new Dimension(650, 10000));
 		storyPanel.add(story);
 		this.add(storyPanel, BorderLayout.CENTER);
 		
 	}
 	private void handleNext() {
 		game.next();
 	}
}
