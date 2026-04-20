package me.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import me.control.Controller;
import me.model.Enemy;
import me.model.Hero;

public class CombatScreen extends JPanel{
    private static CombatScreen instance;
    private AudioManager am;
    private Controller _ctrl;
    private Image backGround;

    private CombatScreen(Controller ctrl){
         _ctrl = ctrl;
        am = AudioManager.getInstance();
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

    private void setComponents(){
        this.setLayout(new BorderLayout());
        
        JPanel enemyPanel = new JPanel();
        enemyPanel.setSize(new Dimension(500, 500));
        enemyPanel.setOpaque(false); // keep background visible
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));

        List<Enemy> enemies = _ctrl.getEnemies();
        if(enemies != null){
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    JButton enemyButton = new JButton(e.getSprite(enemyPanel.getWidth()/enemies.size(), enemyPanel.getHeight()/enemies.size()));
                    enemyButton.setBorderPainted(false);
                    enemyButton.setContentAreaFilled(false);
        //          enemyButton.addActionListener(ev -> { i dont know exactly how to make it so that clicking on an enemy only works when attacking to select target
        //
        //          });
                    enemyPanel.add(enemyButton);
                }
            }
        }

        
        JPanel heroPanel = new JPanel();
        heroPanel.setSize(new Dimension(500, 500));
        heroPanel.setOpaque(false);
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));

        java.util.List<Hero> heroes = _ctrl.getHeroes();
        if(heroes != null){
            for (Hero h : heroes) {
                if (h.isAlive()) {
                    JLabel heroLabel = new JLabel();
                    heroLabel.setIcon(h.getSprite(heroPanel.getWidth()/heroes.size(), heroPanel.getHeight()/heroes.size()));
                    heroPanel.add(heroLabel);
                }
            }
        }

        this.add(heroPanel, BorderLayout.WEST);
        this.add(enemyPanel, BorderLayout.EAST);
    }
    
}
