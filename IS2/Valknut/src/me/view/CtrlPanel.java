package me.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CtrlPanel extends JFrame{
	JPanel titlePanel, scorePanel, buttonPanel, mainPanel;
	JLabel redLabel, blueLabel, redScore, blueScore;
		
	public CtrlPanel() {
		characterSelectionGUI();
	}
	
	private void characterSelectionGUI() {
		JButton gersemiButton, valiButton;
		JPanel gersemiDescriptionPanel, valiDescriptionPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		gersemiButton = new JButton("");
		gersemiButton.setLocation(200, 200);
		gersemiButton.setSize(187, 417);
		gersemiButton.setIcon( new ImageIcon("resources/images/gersemi.png") );
		gersemiButton.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.red);
		});
		mainPanel.add(gersemiButton);
		
		valiButton = new JButton("");
		valiButton.setLocation(500, 200);
		valiButton.setSize(280, 371);
		valiButton.setIcon( new ImageIcon("resources/images/vali.png") );
		valiButton.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.red);
		});
		mainPanel.add(valiButton);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);
		this.setVisible(true);
		
	}
	
	private void combatGUI() {
		JButton attackButton, defendButton, useItemButton, runButton, blueButton, resetButton;

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
//		buttonPanel = new JPanel();
//		buttonPanel.setLayout(null);
//		buttonPanel.setLocation(10, 40);
//		buttonPanel.setSize(540, 50);
//		mainPanel.add(buttonPanel);
		
		attackButton = new JButton("Attack");
		attackButton.setLocation(0, 0);
		attackButton.setSize(120, 30);
		attackButton.setIcon( new ImageIcon("resources/icons/exit.png") );
		attackButton.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.red);
		});
		
		mainPanel.add(attackButton);
		
		defendButton = new JButton("Defend");
		defendButton.setLocation(140, 0);
		defendButton.setSize(120, 30);
		defendButton.setIcon( new ImageIcon("resources/icons/cloud.png") );
		defendButton.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.green);
		});
		mainPanel.add(defendButton);
		
		useItemButton = new JButton("Use Item");
		useItemButton.setLocation(280, 0);
		useItemButton.setSize(120, 30);
		useItemButton.setIcon( new ImageIcon("resources/icons/sun.png") );
		useItemButton.addActionListener((e) -> {
				 mainPanel.setBackground(Color.blue);
		});
		mainPanel.add(useItemButton);
		
		runButton = new JButton("Run");
		runButton.setLocation(420, 0);
		runButton.setSize(120, 30);
		runButton.setIcon( new ImageIcon("resources/icons/storm.png") );
		runButton.addActionListener((e) -> {
				 mainPanel.setBackground(Color.yellow);
		});
		mainPanel.add(runButton);
		

//		JPanel textPanel = new JPanel();
//		textPanel.setLayout(null);
//		textPanel.setLocation(10, 0);
//		textPanel.setSize(260, 30);
//		mainPanel.add(textPanel);
//		JLabel redLabel = new JLabel("Red");
//		redLabel.setLocation(0, 0);
//		redLabel.setSize(50, 40);
//		redLabel.setHorizontalAlignment(JLabel.CENTER);
//		textPanel.add(redLabel);
//		
//		JPanel panelForPanels = new JPanel();
//		panelForPanels.setLayout(null);
//		panelForPanels.setLocation(10, 40);
//		panelForPanels.setSize(260, 50);
//		mainPanel.add(panelForPanels);
//		JPanel redPanel = new JPanel();
//		redPanel.setBackground(Color.red);
//		redPanel.setLocation(0, 0);
//		redPanel.setSize(50, 50);
//		panelForPanels.add(redPanel);
//		
//		JPanel bluePanel = new JPanel();
//		bluePanel.setBackground(Color.blue);
//		bluePanel.setLocation(200, 0);
//		bluePanel.setSize(50, 50);
//		panelForPanels.add(bluePanel);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 100);
		this.setVisible(true);
	}
}
