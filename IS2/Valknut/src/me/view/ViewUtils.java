package me.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ViewUtils {
    
    public static JButton createButton(String path, String over){
        JButton jb = new JButton();
        jb.setIcon(rescalate(300, 100, new ImageIcon(path)));
        jb.setContentAreaFilled(false); 
        jb.setBorderPainted(false);     
        jb.setFocusPainted(false);      
        jb.setOpaque(false);
        jb.setRolloverEnabled(true);
        jb.setRolloverIcon(rescalate(300, 100, new ImageIcon(over)));
        jb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                AudioManager.getInstance().sound("resources/sounds/selection.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        return jb;
    }

    public static ImageIcon rescalate(int width, int height, ImageIcon icon){
		ImageIcon scalated_icon;
		Image im_icon = icon.getImage();
		Image scalated_im = im_icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		scalated_icon = new ImageIcon(scalated_im);
		return scalated_icon;
	}

    public static void showErrorMsg(String msg) {
		showErrorMsg(null, msg);
	}

    public static void showErrorMsg(Component c, String msg) {
		JOptionPane.showMessageDialog(getWindow(c), msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

    public static Frame getWindow(Component c) {
		Frame w = null;
		if (c != null) {
			if (c instanceof Frame)
				w = (Frame) c;
			else
				w = (Frame) SwingUtilities.getWindowAncestor(c);
		}
		return w;
	}

}
