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
import me.socket.MultiplayerManager;
import me.socket.Request;

public class CombatScreen extends JPanel{
    private static CombatScreen instance;
    private final Controller ctrl;
    private final Game game;
    private MultiplayerManager mpm;
    private Image backGround;
    private CardLayout actionLayout;
    private List<JButton> enemy_buttons, command_buttons;
    private JPanel commandsPanel, heroPanel, enemyPanel, actionContainer;
    private JTextArea combatText;
    private boolean toggleVariable = false;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Hero> infected = new ArrayList<>();

    private CombatScreen(Controller ctrl, Game game){
        this.ctrl = ctrl;
        this.game = game;
        if(game.isMultiplayer()){
            this.mpm = MultiplayerManager.getInstacne(ctrl,game);
        }
        initGUI();
        //setComponents();
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

    public void setComponents(){
    	this.removeAll();
        this.setLayout(new BorderLayout());
        
        enemyPanel = new JPanel();
        enemyPanel.setSize(new Dimension(500, 500));
        enemyPanel.setOpaque(false); // keep background visible
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        
        java.util.List<Hero> heroes = game.getHeroes();
        
        heroPanel = new JPanel();
        heroPanel.setSize(new Dimension(500, 500));
        heroPanel.setOpaque(false);
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        
        if (!game.getFinalBattle()) {
	        if(heroes != null){
	            for (Hero h : heroes) {
	                if (h.isAlive() && !h.escaped()) {
	                    JLabel heroLabel = new JLabel();
	                    heroLabel.setIcon(h.getSprite(150, 150));
	                    heroPanel.add(heroLabel);
	                }
	            }
	        }
        }
        
        else {
        	if(heroes != null){
        		if (game.getTurn() - 1 < heroes.size()) {
        			for (Hero h : heroes) {
    	                if (h.isAlive() && !h.escaped()) {
    	                    JLabel heroLabel = new JLabel();
    	                    heroLabel.setIcon(h.getSprite(150, 150));
    	                    heroPanel.add(heroLabel);
    	                }
    	            }
        		}
        		
        		else {
		            for (Hero h : heroes) {
		                if (h.isAlive() && !h.escaped()) {
		                    JButton heroButton= new JButton();
		                    heroButton.setIcon(h.getSprite(150, 150));
		                    heroButton.setBorderPainted(false);
		                    heroButton.setContentAreaFilled(false);
		                    heroButton.addActionListener(ev -> {
		                    	attackHero(h);
		                    });
		                    heroPanel.add(heroButton);
		                }
		            }
        		}
	        }
        }
        
        if (!game.getFinalBattle()) {
	        enemies = game.getEnemies();
	        String s = "New enemies (combat screen): " + enemies.size();
	        for(Enemy e: enemies) {
	        	s = s +" "+e.name();
	        }
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
	                    	showText("You attack!");
	                    });
	                    enemyPanel.add(enemyButton);
	                    enemy_num++;
	                }
	            }
	        }
        }
        
        else {
        	infected = game.getInfected();
        	if (game.getTurn() - 1 >= heroes.size()) {
    			for (Hero h : infected) {
	                if (h.isAlive() && !h.escaped()) {
	                    JLabel heroLabel = new JLabel();
	                    heroLabel.setIcon(h.getSprite(150, 150));
	                    enemyPanel.add(heroLabel);
	                }
	            }
    		}
    		
    		else {
	            for (Hero h : infected) {
	                if (h.isAlive() && !h.escaped()) {
	                    JButton heroButton= new JButton();
	                    heroButton.setIcon(h.getSprite(150, 150));
	                    heroButton.setBorderPainted(false);
	                    heroButton.setContentAreaFilled(false);
	                    heroButton.addActionListener(ev -> {
	                    	attackHero(h);
	                    });
	                    enemyPanel.add(heroButton);
	                }
	            }
    		}
        }
		
		actionLayout = new CardLayout();
		actionContainer = new JPanel(actionLayout);
		actionContainer.setOpaque(false);
        
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
                        toggleAttack();
                    });

                case DEFEND -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                    	initGUI();
                    	setComponents();
                    	this.revalidate();
                    	this.repaint();
                    });

                case USE_ITEM -> actionButton.addActionListener(ev -> {
                        useItem();
                    });

                case RUN -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                    	initGUI();
                    	setComponents();
                    	this.revalidate();
                    	this.repaint();
                    });

                case STATS -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                    });
                    
                case UNDO -> actionButton.addActionListener(ev -> {
                        game.action(c, 1, null);
                        this.revalidate();
                        this.repaint();
                    });
        	}
        	
        	if (!game.getFinalBattle() || game.getTurn() - 1 < heroes.size()) {
	        	command_buttons.add(actionButton);
	        	commandsPanel.add(actionButton);
        	}
        	
        	else {
        		if (c == CombatOption.ATTACK) {
        			command_buttons.add(actionButton);
    	        	commandsPanel.add(actionButton);
        		}
        	}
        }
		
		JPanel textPanel = new JPanel(new BorderLayout());
		combatText = new JTextArea(4, 20);
		combatText.setEditable(false);
		textPanel.add(new JScrollPane(combatText), BorderLayout.CENTER);

		JButton continueBtn = new JButton("Continue");
		continueBtn.addActionListener(e -> actionLayout.show(actionContainer, "COMMANDS"));
		textPanel.add(continueBtn, BorderLayout.SOUTH);
		
		actionContainer.add(commandsPanel, "COMMANDS");
		actionContainer.add(textPanel, "TEXT");

        this.add(heroPanel, BorderLayout.WEST);
        this.add(enemyPanel, BorderLayout.EAST);
        this.add(actionContainer, BorderLayout.PAGE_END);
        
        JButton next = new JButton("next");
			next.addActionListener(ev -> {
				this.toggleVariable = false;
				game.next();
		});
		this.add(next, BorderLayout.PAGE_START );
		
		if (enemies.size() == 0 || heroes.size() == 0 && game.getFinalBattle() || infected.size() == 0 && game.getFinalBattle()) {
			game.next();
		}
    }
    
    public void toggleAttack() {
        toggleVariable = !toggleVariable;
    	for (int i = 0; i < enemy_buttons.size(); i++) {
    		enemy_buttons.get(i).setEnabled(toggleVariable);
    	}
    	
    	for (int i = 0; i < command_buttons.size(); i++) {
    		command_buttons.get(i).setEnabled(!toggleVariable);
    	}
    }
    
    private void attackEnemy(Enemy enemy) {
        if(mpm != null){
            int turn = game.getTurn();
            int id = mpm.getUser().getId();
            if(turn == id){
                Request rq = new Request(Request.RequestType.COMBATOPTION, id);
                rq.addParameter(CombatOption.ATTACK);
                System.err.println("ADDED COMBAT OPTION --> " + CombatOption.ATTACK);
                rq.addParameter(enemy.getEnemyNum());
                System.err.println("ADDED ENEMY NUM --> " + enemy.getEnemyNum());
                System.err.println("OBJECTS TO PASS TO DISPATCHER");
                System.err.println(rq.getParameters()[0].toString());
                System.err.println(rq.getParameters()[1].toString());
                mpm.send(rq);
            }
            else 
                return;
        }
    	game.action(CombatOption.ATTACK, enemy.getEnemyNum(), null);
    	
    	toggleAttack();
    	
    	refresh();
    }
    
    private void useItem() {
    	this.remove(actionContainer);
    	this.revalidate();
    	this.repaint();
    	
    	JPanel itemsPanel = new JPanel();
    	itemsPanel.setSize(new Dimension(500, 500));
    	itemsPanel.setOpaque(false);
    	itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));
    	Inventory in = game.getHeroItems(); 
    	if (in.getItems().isEmpty()) {
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
    			this.revalidate();
    			this.repaint();
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
			this.revalidate();
			this.repaint();
		});
		itemsPanel.add(returnButton);
    	this.add(itemsPanel, BorderLayout.PAGE_END);
    }

    public void refresh(){
        this.removeAll();
    	this.revalidate();
    	initGUI();
    	setComponents();
    }
    
    private void showText(String text) {
        combatText.setText(text);
        actionLayout.show(actionContainer, "TEXT");
    }
    
    private void attackHero(Hero h) {
    	if (toggleVariable) {
    		game.attackHero(h);
    		toggleAttack();
    		refresh();
    	}
    }
    
    
}
