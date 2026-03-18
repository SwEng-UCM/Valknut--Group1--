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
		backgroundLabel.setIcon( new ImageIcon("resources/images/ba.png"));
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
		JButton attackButton, defendButton, useItemButton, runButton, blueButton, resetButton;

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
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
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);
		this.setVisible(true);
	}
}
