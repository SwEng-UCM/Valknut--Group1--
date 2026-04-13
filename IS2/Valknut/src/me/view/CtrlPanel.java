package me.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import me.control.Controller;
import me.model.CharacterSelectionObserver;

public class CtrlPanel extends JFrame implements CharacterSelectionObserver{
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private MainMenu mainMenu;
	private int selectionNumber;
	private Controller _ctrl;
	private AudioManager am;
		
	public CtrlPanel(Controller ctrl) {
		cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
		this.add(mainPanel);
		_ctrl = ctrl;
		selectionNumber = 1;
		this.setSize(1408, 768); 
		this.setLocationRelativeTo(null); 
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_ctrl.startStory();
		am = AudioManager.getInstance();
	}
	
    @Override
	public void onSelection() {
		createCharacterSelector();
	}
	
    @Override
	public void onError(String msg) {
		
	}
	
    @Override
	public void onQuit() {
		
	}
	
    @Override
	public void onGameStart() {
		showMainMenu();
		// createCharacterSelector();
	}

	private void showMainMenu(){
		am.sound("resources/sounds/titleMusic.wav");
		mainMenu = MainMenu.getInstance(_ctrl);
		mainPanel.add(mainMenu, "MENU");
		cardLayout.show(mainPanel, "MENU");
		this.revalidate();
        this.repaint();
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
		gersemiButton.setLocation(250, 246);
		gersemiButton.setSize(280, 371);
		ImageIcon scaled_g_icon = rescalate(280, 371, new ImageIcon("resources/images/gersemi.png"));
		gersemiButton.setIcon(scaled_g_icon);
		gersemiButton.addActionListener( (e) -> {
			_ctrl.combatPrint("Player " + selectionNumber++ + " selects...");
			_ctrl.combatPrint(_ctrl.selectCharacter(0, selectionNumber));
			if(selectionNumber == 3){
				onSelection();
				_ctrl.tellFirstLinesChapterOne();
				selectionNumber = 1;
			}
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
			_ctrl.combatPrint("Player " + selectionNumber++ + " selects...");
			_ctrl.combatPrint(_ctrl.selectCharacter(1, selectionNumber));
			if(selectionNumber == 3){
				onSelection();
				_ctrl.tellFirstLinesChapterOne();
				selectionNumber = 1;
			}
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
		this.setSize(1200, 750);
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
		gersemiLabel.setIcon( new ImageIcon("resources/images/gersemi_icon.png"));
		gersemiLabel.setLocation(200, 0);
		gersemiLabel.setSize(300, 300);
		mainPanel.add(gersemiLabel);
		
		JLabel valiLabel = new JLabel();
		valiLabel.setIcon( new ImageIcon("resources/images/vali_icon.png"));
		valiLabel.setLocation(200, 200);
		valiLabel.setSize(300, 300);
		mainPanel.add(valiLabel);
		
		JPanel orangePanel = new JPanel();
		orangePanel.setBackground(Color.orange);
		orangePanel.setLocation(0, 600);
		orangePanel.setSize(1200, 850);
		
		attackButton = new JButton(Messages.ATTACK);
		attackButton.setLocation(140, 700);
		attackButton.setSize(120, 30);
		attackButton.addActionListener( (e) -> {
			attackGUI();
		});
		
		mainPanel.add(attackButton);
		
		defendButton = new JButton(Messages.DEFEND_ACTION);
		defendButton.setLocation(340, 700);
		defendButton.setSize(120, 30);
		defendButton.addActionListener( (e) -> {
			_ctrl.action(2, 1);
		});
		mainPanel.add(defendButton);
		
		useItemButton = new JButton(Messages.USE_ITEM);
		useItemButton.setLocation(540, 700);
		useItemButton.setSize(120, 30);
		useItemButton.addActionListener((e) -> {
			_ctrl.action(3, 1);
		});
		mainPanel.add(useItemButton);
		
		runButton = new JButton(Messages.RUN);
		runButton.setLocation(740, 700);
		runButton.setSize(120, 30);
		runButton.addActionListener((e) -> {
			_ctrl.action(4, 1);
		});
		mainPanel.add(runButton);
		
		runButton = new JButton(Messages.STATS);
		runButton.setLocation(940, 700);
		runButton.setSize(120, 30);
		runButton.addActionListener((e) -> {
			_ctrl.action(5, 1);
		});
		mainPanel.add(runButton);
		
		mainPanel.add(orangePanel);
		
		mainPanel.add(backgroundLabel);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 850);
		this.setVisible(true);
	}
	
	private void attackGUI() {
		JButton enemyButton;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon( new ImageIcon("resources/images/jotunheimr.png"));
		backgroundLabel.setLocation(0, -100);
		backgroundLabel.setSize(1200, 849);
		
		JLabel gersemiLabel = new JLabel();
		gersemiLabel.setIcon( new ImageIcon("resources/images/gersemi_icon.png"));
		gersemiLabel.setLocation(200, 0);
		gersemiLabel.setSize(300, 300);
		mainPanel.add(gersemiLabel);
		
		JLabel valiLabel = new JLabel();
		valiLabel.setIcon( new ImageIcon("resources/images/vali_icon.png"));
		valiLabel.setLocation(200, 200);
		valiLabel.setSize(300, 300);
		mainPanel.add(valiLabel);
		
		JPanel orangePanel = new JPanel();
		orangePanel.setBackground(Color.orange);
		orangePanel.setLocation(0, 600);
		orangePanel.setSize(1200, 850);
		
		
		
//		enemy2Button = new JButton("Giant 2");
//		enemy2Button.setLocation(740, 700);
//		enemy2Button.setSize(120, 30);
//		enemy2Button.addActionListener( (e) -> {
//				 _ctrl.action(1, 2);
//		});
//		mainPanel.add(enemy2Button);
		
		for (int i = 0; i < _ctrl.getNumEnemies(); i++) {
			int giant_num = i + 1;
			enemyButton = new JButton("GIANT " + giant_num);
			enemyButton.setLocation(i*300, 700);
			enemyButton.setSize(120, 30);
			enemyButton.addActionListener( (e) -> {
					 _ctrl.action(1, giant_num);
			});
			
			mainPanel.add(enemyButton);
		}
		
		mainPanel.add(orangePanel);
		
		mainPanel.add(backgroundLabel);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 850);
		this.setVisible(true);
	}

	private ImageIcon rescalate(int width, int height, ImageIcon icon){
		ImageIcon scalated_icon;
		Image im_icon = icon.getImage();
		Image scalated_im = im_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		scalated_icon = new ImageIcon(scalated_im);
		return scalated_icon;
	}
}
