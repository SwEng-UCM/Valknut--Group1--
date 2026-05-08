/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.view;

import javax.swing.*;
import me.control.Controller;

public class AHSetterScreen extends JDialog {
    private Controller ctrl;

    public AHSetterScreen(Controller ctrl) {
        this.ctrl = ctrl;
        initGUI();
    }

    private void initGUI(){
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(ViewUtils.getWindow(this)); 
        this.setLayout(null);

        String[] combatStrategyOptions = { "Follower", "Leader", "Defensive"};
        JComboBox<String> strategy = new JComboBox<>(combatStrategyOptions);
        strategy.setToolTipText("Set the way AH behaves when ATTACK mode");
        strategy.setBounds(100, 50, 200, 30);
        this.add(strategy);

        String[] difficultyOptions = { "Easy", "Normal", "Hard"};
        JComboBox<String> difficulty = new JComboBox<>(difficultyOptions);
        difficulty.setToolTipText("Set the randomness of the AH");
        difficulty.setBounds(100, 100, 200, 30);
        this.add(difficulty);

        JButton btnApply = new JButton("Apply");
        btnApply.setBounds(230, 215, 100, 30);
        btnApply.addActionListener(e -> {
            double mod = (difficulty.getSelectedIndex() == 0 ? 1.0 : (strategy.getSelectedIndex() == 1 ? 0.7 : 0.4));
            int cs = (strategy.getSelectedIndex() == 0 ? 1 : (strategy.getSelectedIndex() == 1 ? 0 : 2));
            if(!ctrl.setDificulty(mod) || !ctrl.setCombatStrategy(cs)){
                JOptionPane.showMessageDialog(
                    this,
                    "No Solo Mode", 
                    "AH",       
                    JOptionPane.ERROR_MESSAGE
                );
            }
            else{
                JOptionPane.showMessageDialog(
                    this,
                    "AH Updated", 
                    "AH",       
                    JOptionPane.INFORMATION_MESSAGE 
                );
            }
        });
        this.add(btnApply);

        JButton exit = new JButton("Exit");
        exit.setBounds(60, 215, 100, 30);
        exit.addActionListener(e -> {
            dispose();
        });
        this.add(exit);
    }
}

