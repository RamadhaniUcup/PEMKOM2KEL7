package Sipenta.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class gradient extends JPanel {

    public gradient() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) {
            g2.dispose();
            return;
        }

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float[] dist = {0.0f, 0.5f, 1.0f};

        Color[] colors = {
            new Color(21, 19, 71),
            new Color(35, 33, 90),
            new Color(21, 19, 71)
        };

        LinearGradientPaint paint = new LinearGradientPaint(
                0, 0,
                width, 0,
                dist,
                colors
        );

        g2.setPaint(paint);
        g2.fillRect(0, 0, width, height);

        g2.dispose();

        super.paintComponent(g);
    }
}