package me.view;

import java.awt.*;
import javax.swing.*;
import me.control.Controller;
import me.model.CharacterSelectionObserver;
import me.model.Game;

/**
 * 
 * @author Helio Vega Fernández
 * @author Gadea Domínguez (for the story-related functions)
 * 
 */

public class CtrlPanel extends JFrame implements CharacterSelectionObserver{
	private final CardLayout cardLayout;
	private final JPanel mainPanel;
	private final SettingsPanel settingsPanel;
	private final MainMenu mainMenu;
	private CharacterSelection characterSelection;
	private CombatScreen combatScreen;
	private final MultiPlayerScreen multiPlayerScreen;
	private StoryScreen storyScreen;
	private final Controller _ctrl;
	private EndScreen endscreen;
	private GameOverScreen game_over_screen;
		
	public CtrlPanel(Controller ctrl) {
		_ctrl = ctrl;

		cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
		mainMenu = MainMenu.getInstance(ctrl);
		settingsPanel = SettingsPanel.getInstance(ctrl);
		multiPlayerScreen = MultiPlayerScreen.getInstance(ctrl);
		storyScreen = new StoryScreen(ctrl);

		mainPanel.add(mainMenu, "MENU");
		mainPanel.add(settingsPanel, "SETTINGS");
		mainPanel.add(multiPlayerScreen, "MULTIPLAYER");

		this.add(mainPanel);
		this.setSize(1408, 768); 
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// SAVE with Ctrl+S
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("control S"), "saveGame");

		mainPanel.getActionMap().put("saveGame", new AbstractAction() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.saveGame();
				JOptionPane.showMessageDialog(null, "Game saved!");
			}
		});

		// LOAD with Ctrl+L
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke("control L"), "loadGame");

		mainPanel.getActionMap().put("loadGame", new AbstractAction() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.loadGame();
				JOptionPane.showMessageDialog(null, "Game loaded!");
			}
		});

		this.setVisible(true);
	}
	
	@Override
	public void onGameStart() {
		showMainMenu();
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
	public void onCombat(Game game){
    	combatGUI(game);
	}
    
    public void onStory(Game game, String story) {
    	storyGUI(game, story);
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
		characterSelection = CharacterSelection.getInstance(_ctrl);
		mainPanel.add(characterSelection, "CHARACTER SELECTION");
		cardLayout.show(mainPanel, "CHARACTER SELECTION");
	}
	
	private void combatGUI(Game game) {
		combatScreen = CombatScreen.getInstance(_ctrl);
		combatScreen.setComponents();
		mainPanel.add(combatScreen, "COMBAT SCREEN");
		cardLayout.show(mainPanel, "COMBAT SCREEN");
	}
	
	private void storyGUI(Game game, String story) {
		storyScreen = StoryScreen.getInstance(_ctrl, game);
		storyScreen.setText(story);
		mainPanel.add(storyScreen, "STORY SCREEN");
		cardLayout.show(mainPanel, "STORY SCREEN");
	}

	public void onEnd() {
		endscreen = EndScreen.getInstance();
		mainPanel.add(endscreen, "END SCREEN");
		cardLayout.show(mainPanel, "END SCREEN");
	}
	
	public void onGameOver() {
		game_over_screen = GameOverScreen.getInstance();
		mainPanel.add(game_over_screen, "GAME OVER SCREEN");
		cardLayout.show(mainPanel, "GAME OVER SCREEN");
	}
	
}
