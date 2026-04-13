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
	private CharacterSelection characterSelection;
	private CombatScreen combatScreen;
	private final Controller _ctrl;
	private final AudioManager am;
		
	public CtrlPanel(Controller ctrl) {
		cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
		this.add(mainPanel);
		_ctrl = ctrl;
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

	@Overrride
	public void onCombat(){
		combatGUI();
	}
	
    @Override
	public void onGameStart() {
		showMainMenu();
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
		characterSelection = CharacterSelection.getInstace(_ctrl);
		mainPanel.add(characterSelection, "CHARACTER SELECTION");
		cardLayout.show(mainMenu, "CHARACTER SELECTION");
		this.revalidate();
        this.repaint();
	}
	
	private void combatGUI() {
		combatScreen = CombatScreen.getInstace(_ctrl);
		mainPanel.add(characterSelection, "CHARACTER SELECTION");
		cardLayout.show(mainMenu, "CHARACTER SELECTION");
		this.revalidate();
        this.repaint();
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
