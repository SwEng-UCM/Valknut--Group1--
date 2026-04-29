// package me.view;

// import java.awt.BorderLayout;
// import javax.swing.ImageIcon;
// import javax.swing.JButton;
// import javax.swing.JPanel;
// import me.control.Controller;
// import me.model.Game;

// public class StoryScreen extends JPanel {
	
// 	private JButton nextButton;
// 	private String text;
// 	private final Controller ctrl;
// 	private final Game game;
	
// 	StoryScreen(String text, Controller ctrl, Game game) {
// 		this.text = text;
// 		this.ctrl = ctrl;
// 		this.game = game;
// 		initGui();
// 	}

// 	private void initGui() {
// 		 setLayout(new BorderLayout());
// 		
// 		 this.nextButton = new JButton();
// 		 this.nextButton.setToolTipText("Load");
// 		 this.nextButton.setIcon(new ImageIcon("resources/icons/open.png"));
// 		 this.nextButton.addActionListener((e) -> handleNext());
// 		 this.add(nextButton, BorderLayout.PAGE_END);
		
// 	}

// 	private void handleNext() {
// 		game.next();
// 	}
// }
