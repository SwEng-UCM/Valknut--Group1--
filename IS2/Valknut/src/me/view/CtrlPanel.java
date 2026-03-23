package me.view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.control.Controller;
import me.model.CharacterSelectionObserver;

public class CtrlPanel extends JFrame implements CharacterSelectionObserver{
	JPanel titlePanel, scorePanel, buttonPanel, mainPanel;
	JLabel redLabel, blueLabel, redScore, blueScore;
	Controller _ctrl;
		
	public CtrlPanel(Controller ctrl) {
		_ctrl = ctrl;
	}
	
	public void onSelection() {
		combatGUI();
	}
	
	public void onError(String msg) {
		
	}
	
	public void onQuit() {
		
	}
	
	public void onGameStart() {
		createCharacterSelector();
	}
	
	private void createCharacterSelector() {
		JButton gersemiButton, valiButton;
		JLabel gersemiLabel, valiLabel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon( new ImageIcon("resources/images/selection_screen.png"));
		backgroundLabel.setLocation(0, 0);
		backgroundLabel.setSize(1200, 849);
		
		
		gersemiButton = new JButton("");
		gersemiButton.setLocation(300, 200);
		gersemiButton.setSize(187, 417);
		gersemiButton.setIcon( new ImageIcon("resources/images/gersemi.png") );
		gersemiButton.addActionListener( (e) -> {
			_ctrl.selectCharacter(0);
		});
		mainPanel.add(gersemiButton);
		
		gersemiLabel = new JLabel();
		gersemiLabel.setLocation(281, 620);
		gersemiLabel.setSize(225, 15);
		gersemiLabel.setForeground(Color.white);
		gersemiLabel.setText("GERSEMI, BELOVED CHILD OF FREYA");
		mainPanel.add(gersemiLabel);
		
		valiButton = new JButton("");
		valiButton.setLocation(600, 246);
		valiButton.setSize(280, 371);
		valiButton.setIcon( new ImageIcon("resources/images/vali.png") );
		valiButton.addActionListener( (e) -> {
			_ctrl.selectCharacter(1);
		});
		mainPanel.add(valiButton);
		
		valiLabel = new JLabel();
		valiLabel.setLocation(639, 620);
		valiLabel.setSize(202, 15);
		valiLabel.setForeground(Color.white);
		valiLabel.setText("VÁLI, FORGOTTEN CHILD OF LOKI");
		mainPanel.add(valiLabel);
		
		mainPanel.add(backgroundLabel);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 849);
		this.setVisible(true);
		
		
	}
	
	private void combatGUI() {
		JButton attackButton, defendButton, useItemButton, runButton;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon( new ImageIcon("resources/images/jotunheimr.png"));
		backgroundLabel.setLocation(0, -100);
		backgroundLabel.setSize(1200, 849);
		
		JLabel gersemiLabel = new JLabel();
		gersemiLabel.setIcon( new ImageIcon("resources/images/gersemi_big.png"));
		gersemiLabel.setLocation(200, 0);
		gersemiLabel.setSize(300, 300);
		mainPanel.add(gersemiLabel);
		
		JLabel valiLabel = new JLabel();
		valiLabel.setIcon( new ImageIcon("resources/images/vali_big.png"));
		valiLabel.setLocation(200, 200);
		valiLabel.setSize(300, 300);
		mainPanel.add(valiLabel);
		
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);
		redPanel.setLocation(0, 600);
		redPanel.setSize(1200, 850);
		
		attackButton = new JButton("Attack");
		attackButton.setLocation(40, 700);
		attackButton.setSize(120, 30);
		attackButton.setIcon( new ImageIcon("resources/icons/gersemi_small.png") );
		attackButton.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.red);
		});
		
		mainPanel.add(attackButton);
		
		defendButton = new JButton("Defend");
		defendButton.setLocation(340, 700);
		defendButton.setSize(120, 30);
		defendButton.setIcon( new ImageIcon("resources/icons/gersemi_small.png") );
		defendButton.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.green);
		});
		mainPanel.add(defendButton);
		
		useItemButton = new JButton("Use Item");
		useItemButton.setLocation(640, 700);
		useItemButton.setSize(120, 30);
		useItemButton.setIcon( new ImageIcon("resources/icons/vanir_small.png") );
		useItemButton.addActionListener((e) -> {
				 mainPanel.setBackground(Color.blue);
		});
		mainPanel.add(useItemButton);
		
		runButton = new JButton("Run");
		runButton.setLocation(940, 700);
		runButton.setSize(120, 30);
		runButton.setIcon( new ImageIcon("resources/icons/vanir_small.png") );
		runButton.addActionListener((e) -> {
				 mainPanel.setBackground(Color.yellow);
		});
		mainPanel.add(runButton);
		
		mainPanel.add(redPanel);
		
		mainPanel.add(backgroundLabel);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 850);
		this.setVisible(true);
	}
	
	private void attackGUI() {
		JButton enemy1Button, enemy2Button;
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);
		redPanel.setLocation(0, 600);
		redPanel.setSize(1200, 250);
		
		enemy1Button = new JButton("Attack");
		enemy1Button.setLocation(40, 700);
		enemy1Button.setSize(120, 30);
		enemy1Button.setIcon( new ImageIcon("resources/icons/gersemi_small.png") );
		enemy1Button.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.red);
		});
		
		mainPanel.add(enemy1Button);
		
		enemy1Button = new JButton("Defend");
		enemy1Button.setLocation(340, 700);
		enemy1Button.setSize(120, 30);
		enemy1Button.setIcon( new ImageIcon("resources/icons/gersemi_small.png") );
		enemy1Button.addActionListener( (e) -> {
				 mainPanel.setBackground(Color.green);
		});
		mainPanel.add(enemy1Button);
		
		mainPanel.add(redPanel);
	}
}
