/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.swing;

import java.awt.*;
import javax.swing.JPanel;


/**
 *
 * @author ASUS
 */
public class Rounpanel extends JPanel {
    
    private int cornerRadius = 25;

    public Rounpanel() {
        setOpaque(false);
    }
    
      
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, 25, 25);
        
        g2.dispose();
    }
}
    


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ASUS
 */
    
