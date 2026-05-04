package me.view;

import java.awt.*;
import java.awt.event.ActionEvent;
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
    private MultiplayerManager mpm;
    private Image backGround;
    private CardLayout actionLayout;
    private List<JButton> enemy_buttons, command_buttons;
    private JPanel commandsPanel, heroPanel, enemyPanel, actionContainer;
    private JTextArea combatText;
    private String textLog;
    private boolean toggleVariable = false;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Hero> infected = new ArrayList<>();

    private CombatScreen(Controller ctrl){
        this.ctrl = ctrl;
        if(ctrl.isMultiplayer()){
            this.mpm = MultiplayerManager.getInstacne(ctrl,ctrl.getGame());
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

    public static CombatScreen getInstance(Controller ctrl){
        if(instance == null)
            instance = new CombatScreen(ctrl);
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
        
        java.util.List<Hero> heroes = ctrl.getHeroes();
        
        heroPanel = new JPanel();
        heroPanel.setSize(new Dimension(500, 500));
        heroPanel.setOpaque(false);
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        
        if (!ctrl.getFinalBattle()) {
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
        		if (ctrl.getTurn() - 1 < heroes.size()) {
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
        
        if (!ctrl.getFinalBattle()) {
	        enemies = ctrl.getEnemies();
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
	                    });
	                    enemyPanel.add(enemyButton);
	                    enemy_num++;
	                }
	            }
	        }
        }
        else {
        	infected = ctrl.getInfected();
        	if (ctrl.getTurn() - 1 >= heroes.size()) {
    			for (Hero h : infected) {
	                if (h.isAlive() && !h.escaped()) {
	                    JLabel heroLabel = new JLabel();
	                    heroLabel.setIcon(h.getInfectedSprite(150, 150));
	                    enemyPanel.add(heroLabel);
	                }
	            }
    		}
    		else {
	            for (Hero h : infected) {
	                if (h.isAlive() && !h.escaped()) {
	                    JButton heroButton= new JButton();
	                    heroButton.setIcon(h.getInfectedSprite(150, 150));
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
        commandsPanel.setSize(new Dimension(5000, 500));
        commandsPanel.setOpaque(false);
        commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.X_AXIS));
        command_buttons = new ArrayList<>(CombatOption.values().length);
        
        for (CombatOption c: CombatOption.values()) {
        	JButton actionButton = ViewUtils.createButton(
                    "resources/images/Buttons/" + c.toString() + "Button_NS.png",
                    "resources/images/Buttons/" + c.toString() + "Button_S.png"
            );
        	actionButton.setPreferredSize(new Dimension(233, 100));
        	switch(c) {
                case ATTACK -> actionButton.addActionListener(ev -> {
                        toggleAttack();
                    });

                case DEFEND -> actionButton.addActionListener(ev -> {
                		ctrl.action(c, 1, null);
                        textLog = ctrl.consumeCombatLog();
                        refresh();
                    	this.revalidate();
                    	this.repaint();
                    });

                case USE_ITEM -> actionButton.addActionListener(ev -> {
                        useItem();
                    });

                case RUN -> actionButton.addActionListener(ev -> {
                		ctrl.action(c, 1, null);
                        textLog = ctrl.consumeCombatLog();
                        refresh();
                    	this.revalidate();
                    	this.repaint();
                    });

                case STATS -> actionButton.addActionListener(ev -> {
                		showText(ctrl.showStats());
                        //textLog = game.consumeCombatLog();
                        //refresh();
                    });
                    
                case UNDO -> actionButton.addActionListener(ev -> {
                		ctrl.action(c, 1, null);
                        refresh();
                        this.revalidate();
                        this.repaint();
                    });
        	}
        	
        	if (!ctrl.getFinalBattle() || (ctrl.getTurn() - 1 < heroes.size() && c != CombatOption.RUN)) {
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
		combatText = new JTextArea(5, 20);
		combatText.setEditable(false);
		textPanel.add(new JScrollPane(combatText), BorderLayout.CENTER);

		JButton continueBtn = new JButton("Continue");
		continueBtn.addActionListener(e -> {
			if(mpm != null){
			int id = mpm.getUser().getId();
			if(id == 2){
				ViewUtils.showErrorMsg("Wait for Player 1");
				return;
			}
			else{
				Request rq = new Request(Request.RequestType.COMBATOPTION, id);
				mpm.send(rq);
			}
		}
			changeActionPanel("COMMANDS");
			refresh();
		});
		textPanel.add(continueBtn, BorderLayout.SOUTH);
		
		actionContainer.add(commandsPanel, "COMMANDS");
		actionContainer.add(textPanel, "TEXT");

        this.add(heroPanel, BorderLayout.WEST);
        this.add(enemyPanel, BorderLayout.EAST);
        this.add(actionContainer, BorderLayout.PAGE_END);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);

        JButton next = new JButton("Next");
		next.addActionListener((ActionEvent ev) -> {
			this.toggleVariable = false;
			if(mpm != null){
				int id = mpm.getUser().getId();
				if(id == 2){
					ViewUtils.showErrorMsg("Wait for Player 1");
					return;
				}
				else{
					Request rq = new Request(Request.RequestType.STORYADVANCED, id);
					mpm.send(rq);
				}
			}
			ctrl.next();
		});
        topPanel.add(next);
        
        if (!ctrl.getFinalBattle()) {
	        JButton save = new JButton("Save");
	        save.addActionListener(ev -> {
	        	ctrl.saveGame();
	            JOptionPane.showMessageDialog(this, "Game saved.");
	        });
	        topPanel.add(save);
        }

        JButton load = new JButton("Load");
        load.addActionListener(ev -> {
        	ctrl.loadGame();
            JOptionPane.showMessageDialog(this, "Game loaded.");
            refresh();
            this.revalidate();
            this.repaint();
        });
        topPanel.add(load);

        JButton exit = new JButton("Exit");
        exit.addActionListener(ev -> ctrl.exit());
        topPanel.add(exit);

		this.add(topPanel, BorderLayout.PAGE_START);
		
		if (enemies.isEmpty() || heroes.isEmpty() && ctrl.getFinalBattle() || infected.isEmpty() && ctrl.getFinalBattle()) {
			ctrl.next();
		}
		
		// if (textLog != null && !textLog.isEmpty()) {
		// 	System.err.println("I arrived at really showing dialog");
		//     showText(textLog);
		//     textLog = "";
		// }
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
            int turn = ctrl.getTurn();
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
            else{
				ViewUtils.showErrorMsg("You can not perform actions right now.");
				return;
			}
                
        }

		toggleAttack();
		
		attackAction(enemy.getEnemyNum());    	
    }

	public void attackAction(int tarjet){
		ctrl.action(CombatOption.ATTACK, tarjet, null);
    	System.err.println("Time for textLog display");
    	textLog = ctrl.consumeCombatLog();
    	System.err.println("So textLog is: " + textLog);
		System.err.println("Should display TextLog");
		showText(textLog);
	}
    
    private void useItem() {
    	this.remove(actionContainer);
    	this.revalidate();
    	this.repaint();
    	
    	JPanel itemsPanel = new JPanel();
    	itemsPanel.setSize(new Dimension(500, 500));
    	itemsPanel.setOpaque(false);
    	itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.X_AXIS));
    	Inventory in = ctrl.getHeroItems(); 
    	if (in.getItems().isEmpty()) {
    		System.out.println("No items to use");
    	}
    	for(Item i : in.getItems()){
    		JButton itemChoiceButton = new JButton(i.getName());
    		itemChoiceButton.setPreferredSize(new Dimension(1000, 100));
    		itemChoiceButton.addActionListener(ev -> {
    			ctrl.action(CombatOption.USE_ITEM, 1, i);
    			textLog = ctrl.consumeCombatLog();
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
			ctrl.action(CombatOption.USE_ITEM, 1, null);
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
    	setComponents();
    }
    
    private void showText(String text) {
        combatText.setText(text);
        changeActionPanel("TEXT");
    }
    
    private void attackHero(Hero h) {
    	if (toggleVariable) {
    		ctrl.attackHero(h);
    		toggleAttack();
    		refresh();
    		this.repaint();
    		this.revalidate();
    	}
    }
    
    public void changeActionPanel(String s){
		actionLayout.show(actionContainer, s);
	}
}
