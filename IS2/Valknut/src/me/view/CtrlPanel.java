package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;
import me.model.CharacterSelectionObserver;

public class CtrlPanel extends JFrame implements CharacterSelectionObserver{
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private final SettingsPanel settingsPanel;
	private final MainMenu mainMenu;
	private final CharacterSelection characterSelection;
	private CombatScreen combatScreen;
	private final MultiPlayerScreen multiPlayerScreen;
	private final Controller _ctrl;
		
	public CtrlPanel(Controller ctrl) {
		_ctrl = ctrl;

		cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
		mainMenu = MainMenu.getInstance(ctrl);
		settingsPanel = SettingsPanel.getInstance(ctrl);
		characterSelection = CharacterSelection.getInstance(ctrl);
		multiPlayerScreen = MultiPlayerScreen.getInstance(ctrl);

		mainPanel.add(mainMenu, "MENU");
		mainPanel.add(settingsPanel, "SETTINGS");
		mainPanel.add(characterSelection, "CHARACTER SELECTION");
		mainPanel.add(multiPlayerScreen, "MULTIPLAYER");

		this.add(mainPanel);
		this.setSize(1408, 768); 
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		_ctrl.startStory();

		// SAVE with Ctrl+S
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("control S"), "saveGame");

		mainPanel.getActionMap().put("saveGame", new AbstractAction() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				_ctrl.saveGame();
				JOptionPane.showMessageDialog(null, "Game saved!");
			}
		});

		// LOAD with Ctrl+L
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("control L"), "loadGame");

		mainPanel.getActionMap().put("loadGame", new AbstractAction() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				_ctrl.loadGame();
				JOptionPane.showMessageDialog(null, "Game loaded!");
			}
		});

		this.setVisible(true);
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
    AudioManager.getInstance().stopMusic();
    
    System.exit(0);
}

    @Override
	public void onCombat(){
		combatGUI();
	}
	
    @Override
	public void onGameStart() {
		showMainMenu();
	}

	public void multiplayerScreen(){
		cardLayout.show(mainPanel, "MULTIPLAYER");
	}

	public void setPreviousScreenToSettings(String s, String b){
		settingsPanel.setPreviousScreen(s);
		settingsPanel.setBackground(b);
	}

	public void settingScreen(){
		cardLayout.show(mainPanel, "SETTINGS");
	}

	public void showMainMenu(){
		cardLayout.show(mainPanel, "MENU");
		this.revalidate();
        this.repaint();
	}
	
	private void createCharacterSelector() {
		cardLayout.show(mainPanel, "CHARACTER SELECTION");
	}
	
	private void combatGUI() {
		combatScreen = CombatScreen.getInstance(_ctrl);
		mainPanel.add(combatScreen, "COMBAT SCREEN");
		cardLayout.show(mainPanel, "COMBAT SCREEN");
	}
	
}
