package me.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import me.control.Controller;
import me.model.CombatOption;
import me.model.Enemy;
import me.model.Game;
import me.model.Hero;
import me.model.items.Inventory;
import me.model.items.Item;

public class CombatScreen extends JPanel{
    private static CombatScreen instance;
    private Controller _ctrl;
    private Game game;
    private Image backGround;
    private List<JButton> enemy_buttons, command_buttons;
    private JPanel commandsPanel, heroPanel, enemyPanel;

    private CombatScreen(Controller ctrl, Game game){
         _ctrl = ctrl;
         this.game = game;
        initGUI();
        setComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static CombatScreen getInstance(Controller ctrl, Game game){
        if(instance == null)
            instance = new CombatScreen(ctrl, game);
        return instance;
    }
    
    public void setHeroes() {
    	
    }

    private void initGUI(){
        this.backGround = new ImageIcon(Messages.COMBATSCREEN).getImage();
        this.setVisible(true);
        this.setOpaque(false);
    }

    private void setComponents(){
        this.setLayout(new BorderLayout());
        
        enemyPanel = new JPanel();
        enemyPanel.setSize(new Dimension(500, 500));
        enemyPanel.setOpaque(false); // keep background visible
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));

        List<Enemy> enemies = game.getEnemies();
        if(enemies != null){
            enemy_buttons = new ArrayList<>(enemies.size());
        int enemy_num = 1;
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                	e.setEnemyNum(enemy_num);
                    JButton enemyButton = new JButton(e.getSprite(enemyPanel.getWidth()/enemies.size(), enemyPanel.getHeight()/enemies.size()));
                    enemyButton.setBorderPainted(false);
                    enemyButton.setContentAreaFilled(false);
                    enemyButton.setEnabled(false);
                    enemy_buttons.add(enemyButton);
                    enemyButton.addActionListener(ev -> {
                    	attackEnemy(e);
                    });
                    enemyPanel.add(enemyButton);
                    enemy_num++;
                }
            }
        }

        
        heroPanel = new JPanel();
        heroPanel.setSize(new Dimension(500, 500));
        heroPanel.setOpaque(false);
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));

        java.util.List<Hero> heroes = game.getHeroes();
        if(heroes != null){
            for (Hero h : heroes) {
                if (h.isAlive() && !h.escaped()) {
                    JLabel heroLabel = new JLabel();
                    heroLabel.setIcon(h.getSprite(150, 150));
                    heroPanel.add(heroLabel);
                }
            }
        }
        
        commandsPanel = new JPanel();
        commandsPanel.setSize(new Dimension(500, 500));
        commandsPanel.setOpaque(false);
        commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.X_AXIS));
        command_buttons = new ArrayList<>(CombatOption.values().length);
        
        for (CombatOption c: CombatOption.values()) {
        	JButton actionButton = new JButton(c.toString());
        	actionButton.setPreferredSize(new Dimension(500, 100));
        	switch(c) {
                case ATTACK -> actionButton.addActionListener(ev -> {
                        attack();
                    	this.revalidate();
                    	this.repaint();
                    });

                case DEFEND -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                    });

                case USE_ITEM -> actionButton.addActionListener(ev -> {
                        useItem();
                    });

                case RUN -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                        this.remove(heroPanel);
                        setComponents();
                        this.revalidate();
                    	this.repaint();
                    });

                case STATS -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                    });
                    
                case UNDO -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                        this.removeAll();
                        this.revalidate();
                        this.repaint();
                        initGUI();
                        setComponents();
                    });
        	}
        	command_buttons.add(actionButton);
        	commandsPanel.add(actionButton);
        }

        this.add(heroPanel, BorderLayout.WEST);
        this.add(enemyPanel, BorderLayout.EAST);
        this.add(commandsPanel, BorderLayout.PAGE_END);
    }
    
    private void attack() {
    	for (int i = 0; i < enemy_buttons.size(); i++) {
    		enemy_buttons.get(i).setEnabled(true);
    	}
    	
    	for (int i = 0; i < command_buttons.size(); i++) {
    		command_buttons.get(i).setEnabled(false);
    	}
    }
    
    private void attackEnemy(Enemy enemy) {
    	game.action(CombatOption.ATTACK, enemy.getEnemyNum(), null);
    	
    	for (int i = 0; i < enemy_buttons.size(); i++) {
    		enemy_buttons.get(i).setEnabled(false);
    	}
    	
    	for (int i = 0; i < command_buttons.size(); i++) {
    		command_buttons.get(i).setEnabled(true);
    	}
    }
    
    private void useItem() {
    	this.remove(commandsPanel);
    	this.revalidate();
    	this.repaint();
    	JPanel itemsPanel = new JPanel();
    	itemsPanel.setSize(new Dimension(500, 500));
    	itemsPanel.setOpaque(false);
    	itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));
    	Inventory in = game.getHeroItems(); 
    	if (in.getItems().size() == 0) {
    		System.out.println("No items to use");
    	}
    	for(Item i : in.getItems()){
    		JButton itemChoiceButton = new JButton(i.getName());
    		itemChoiceButton.setPreferredSize(new Dimension(1000, 100));
    		itemChoiceButton.addActionListener(ev -> {
    			game.action(CombatOption.USE_ITEM, 1, i);
    			this.remove(itemsPanel);
    			initGUI();
    			setComponents();
    		});
        	itemsPanel.add(itemChoiceButton);
    	}
    	JButton returnButton = new JButton("RETURN");
		returnButton.setPreferredSize(new Dimension(1000, 100));
		returnButton.addActionListener(ev -> {
			game.action(CombatOption.USE_ITEM, 1, null);
			this.remove(itemsPanel);
			initGUI();
			setComponents();
		});
		itemsPanel.add(returnButton);
    	this.add(itemsPanel, BorderLayout.PAGE_END);
    }
    
}
